#!/usr/bin/python

# sanitytest.py -- Reads an XML story description and attempts to determine if 
# the story can be completed through all possible choices.

# In this case, "can be completed" is defined as whether or not all possible choices can result in 
# landing on "END" as a destination at some point.

import os
import sys
import xml.etree.ElementTree as ET
        
        
def nodesearch(nodename, node):
    for n in node:
        if n.tag==nodename:
            return n
    return None


def manynodesearch(nodename, node):
    nlist=[]
    
    for n in node:
        if n.tag==nodename:
            nlist.append(n)
    return nlist


def sanitize(text):
    """Replaces all special characters in text with escaped characters."""
    newstr=''
    for c in text:
        if c=='"' or c=="'" or c=='%' or c=='_':
            newstr=newstr+'\\'
        newstr=newstr+c
        
    return newstr
            

class Node:
    def add_dests(self, answernode):
        for a in answernode:
            dnodes=manynodesearch("dest", a)
            destsum=0
            for d in dnodes:
                destsum=destsum+int(d.attrib['p'])
                #print "[%s] Current destination text: %s" % (self.nodename, d.text) 
                self.dests.append(d.text)
                
                # Do preliminary checks to ensure that this program's results are valid.
                if d.text==self.nodename:
                    print "Error: No node should be able to have itself as a destination."
                    print "Node ID: %s" % self.nodename
                    raise Exception()
                if int(d.attrib['p'])<1:
                    print "Error: All destinations should have a probability greater than 0."
                    print "Node ID: %s" % self.nodename
                    raise Exception()
            if destsum!=100:
                textnode=nodesearch("text", a)
                print "Error: Probability for all destinations does not sum to 100%."
                print "Node ID: %s" % self.nodename
                print "Option text: %s" % textnode.text
                raise Exception()


    def resolve_dests(self, nodelist):
        for d in self.dests:
            dest_found=False
            if d=="END":
                self.children.append(Node("END"))
            for n in nodelist:
                if n.nodename==d:
                    self.children.append(n)
                    dest_found=True
            if not dest_found:
                # Looks like we have an orphaned node or invalid destination.
                self.children.append(Node(d))

        
    def __init__(self, newname):
        self.nodename=newname
        self.dests=[]
        self.children=[]
        self.temp_mark=False
        #self.permMark=False
        
        
def print_node_list(nodelist):
    for n in nodelist:
        print n.nodename+"-> "+str(n.dests)


def readstory(infile):
    contents=infile.read()
    curid=1
    resid=1
    
    nodelist=[]
    
    root=ET.fromstring(contents)
    storytitle=root.attrib['title']
    
    # First pass: Fill the node list with raw data.
    for subnode in root:
        nodeid=subnode.attrib['id']
        #print "Current node id: %s" % nodeid
        answernode=nodesearch("answers", subnode)
        
        n=Node(nodeid)
        n.add_dests(answernode)
        #print(n.dests)
        nodelist.append(n)
        
    # Second pass: Link the nodes.
    # (This is going to be fairly expensive...)
    for n in nodelist:
        n.resolve_dests(nodelist)
        
    return nodelist


def rcsv_sanity_check(node):
    if node.nodename.upper()=="END":
        return True
    elif len(node.children)==0:
        print "Node with no children or endpoints found: %s" % node.nodename
        return False
    else:
        for c in node.children:
            if not rcsv_sanity_check(c):
                print "Story failed sanity check on node %s with destination %s." % (node.nodename, c.nodename)
                return False
        return True
    

def check(node):
    if node.temp_mark:
        return (False, None)
    else:
        node.temp_mark=True
        for c in node.children:
            if not check(c)[0]:
                return (False, c)
        node.temp_mark=False
        return (True, None)


# This implements a search to determine if there are any circular references.
# While it is possible for a story with circular paths to terminate, doing so is likely very bad practice
# that can fairly easily completely break the sanity checker.
def detect_circular_references(nodelist):
    for n in nodelist:
        (res, badnode)=check(n)
        if not res:
            print "Circular reference check failed for node %s referred to by %s" % (n.nodename, badnode.nodename)
            return False
    return True


def main(args):
    infile=sys.stdin
    
    # Insert arg handling fanciness here
    if len(args)!=0:
        infile=open(args[1], 'r')
    
    try:
        nodelist=readstory(infile)
        if detect_circular_references(nodelist):
            if rcsv_sanity_check(nodelist[0]):
                print "Story passed sanity check."

    except:
        pass

if __name__=="__main__":
    main(sys.argv)
