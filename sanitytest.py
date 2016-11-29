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
    nodename=""
    dests=[]
    children=[]


    def add_dests(self, answernode):
        for a in answernode:
            dnodes=manynodesearch("dest", a)
            for d in dnodes:
                self.dests.append(d.text)
                
                # Do preliminary checks to ensure that this program's results are valid.
                if d.text==self.nodename:
                    print "Error: No node should be able to have itself as a destination."
                    print "Node ID: %s" % self.nodename
                    raise Exception('')
                if int(d.attrib['p'])<1:
                    print "Error: All destinations should have a probability greater than 0."
                    print "Node ID: %s" % self.nodename
                    raise Exception('')


    def resolve_dests(self, nodelist):
        for d in self.dests:
            for n in nodelist:
                if n.nodename==d:
                    self.children.append(n)
                    
        # Now check for and remove duplicates:
        """
        duplist=[]
        jrange=range(0, len(self.children))
        jrange.reverse()
        for i in range(0, len(self.children)):
            for j in jrange:
                if i!=j and self.children[i] is self.children[j]:
                    duplist.append(self.children[j])
                    
        duplist.sort()
        duplist.reverse()
        
        for d in duplist:
            print d
            self.children.remove(d)
        """

        
    def __init__(self, newname):
        self.nodename=newname
        
        
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
        answernode=nodesearch("answers", subnode)
        
        n=Node(nodeid)
        n.add_dests(answernode)
        nodelist.append(n)
    
    # Second pass: Link the nodes.
    # (This is going to be fairly expensive...)
    for n in nodelist:
        n.resolve_dests(nodelist)
    
    return nodelist


def rcsv_sanity_check(node):
    if node.nodename.upper()=="END":
        return True
    else:
        curval=True
        for c in node.children:
            print c
            curval=(curval and rcsv_sanity_check(c))
            if not curval:
                print "Error: Node '%s' has unresolved destinations" % c.nodename
        return curval
    

def main(args):
    infile=sys.stdin
    
    # Insert arg handling fanciness here
    if len(args)!=0:
        infile=open(args[1], 'r')
    
    nodelist=readstory(infile)
    rcsv_sanity_check(nodelist[0])
    try:
        nodelist=readstory(infile)
        if len(nodelist)!=0:
            rcsv_sanity_check(nodelist[0])
    except:
        print "Error found. Terminating..."


if __name__=="__main__":
    main(sys.argv)
