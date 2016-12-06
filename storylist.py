#!/usr/bin/python

# storylist.py -- A program to generate a human readable version of every possible path in a story.
# Assumes that a story has been sanity checked. 

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
                self.destweights.append(int(d.attrib['p']))
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


    def get_dest_weight(self, node):
        for i in range(0, len(self.dests)):
            if self.dests[i]==node.nodename:
                return self.destweights[i]/100.0
        return 1.0
            
            
    def resolve_dests(self, nodelist):
        for d in self.dests:
            dest_found=False
            #if d=="END":
            #    self.children.append(Node("END"))
            for n in nodelist:
                if n.nodename==d:
                    n.parent=self
                    self.children.append(n)
                    dest_found=True
            if not dest_found:
                # Looks like we have an orphaned node or invalid destination.
                n=Node(d)
                n.parent=self
                self.children.append(n)

        
    def __init__(self, newname):
        self.nodename=newname
        self.dests=[]
        self.destweights=[]
        self.children=[]
        self.text=""
        self.temp_mark=False
        self.parent=None
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
        textnode=nodesearch("text", subnode)
        n=Node(nodeid)
        n.text=textnode.text
        n.add_dests(answernode)
        #print(n.dests)
        nodelist.append(n)
        
    # Second pass: Link the nodes.
    # (This is going to be fairly expensive...)
    for n in nodelist:
        n.resolve_dests(nodelist)
        
    return nodelist


def calc_probability(l):
    runningprob=1.0
    listlen=len(l)
    listrange=range(0, listlen)
    listrange.reverse()
    
    for i in listrange:
        if i!=(listlen-1):
            runningprob=runningprob*l[i].get_dest_weight(l[i+1])
    
    return runningprob


# This function removes all but a node's direct ancestors from a list.
# Ie. it removes all siblings, etc.
def rcsv_filter_heritage(n, curlist):
    curlist.append(n)
    if n.parent!=None:
        rcsv_filter_heritage(n.parent, curlist)



def print_list(l):
    listprob=calc_probability(l)
    print "Storyline weight probability: %s%%" % str(listprob*100)
    
    listlen=len(l)
    listrange=range(0, listlen)
    for i in listrange:
        e=l[i]
        print "\tNode ID: %s" % e.nodename
        print "\tText: %s" % e.text
        if (i+1)<listlen:
            nexte=l[i+1]
            print "\tDestination: %s" % nexte.nodename
            print "\tDestination weight: %s%%" % str(e.get_dest_weight(nexte)*100)
        else:
            print "\tDestination: END"
        print ""


def rcsv_story_write(node, pathlist):
    if node.nodename.upper()=="END":
        newlist=[]
        rcsv_filter_heritage(node.parent, newlist)
        newlist.reverse()
        print_list(newlist)
        pathlist=[]
        
    pathlist.append(node)
    for c in node.children:
        c.parent=node
        rcsv_story_write(c, pathlist)


def main(args):
    infile=sys.stdin
    
    # Insert arg handling fanciness here
    if len(args)!=0:
        infile=open(args[1], 'r')
    

    nodelist=readstory(infile)
    pathlist=[]
    nodelist[0].parent=None
    rcsv_story_write(nodelist[0], pathlist)

if __name__=="__main__":
    main(sys.argv)
