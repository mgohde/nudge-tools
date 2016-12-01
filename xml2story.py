#!/usr/bin/python

# xml2story.py -- A somewhat useless utility that can convert an XML file back into a valid input file for story2xml.py.
# This script mostly exists to ensure that stories are easy to read and modify, regardless of format.
# In effect, this source is effectively just a modified and slightly restructured version of 
# dbtool.

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
            
def gen_text(text, indent):
    out=sys.stdout
    words=text.split()
    indentlen=len(indent)
    charcount=indentlen
    
    out.write(indent)
    
    for w in words:
        curlen=len(w)
        if (charcount+curlen+1)>80:
            out.write("\n%s" % indent)
            charcount=indentlen
        out.write("%s " % w)
        charcount=charcount+curlen+1
        
    out.write("\n\n")
    

def gen_response_block(node, indent):
    out=sys.stdout
    print "%sResponses:" % indent
    
    for child in node:
        textnode=nodesearch("text", child)
        dests=manynodesearch("dest", child)
        out.write("%s%s%s -> " % (indent, indent, textnode.text))
        for d in dests:
            if d is dests[0]:
                out.write("%s%% to %s" % (d.attrib['p'], d.text))
            else:
                out.write(", %s%% to %s" % (d.attrib['p'], d.text))
        out.write("\n")


def convert(infile):
    contents=infile.read()
    
    root=ET.fromstring(contents)
    storytitle=root.attrib['title']
    
    print "Title: %s\n" % storytitle
    
    for subnode in root:
        answerchoice='A'
        
        snid=subnode.attrib['id']
        textnode=nodesearch("text", subnode)
        answernode=nodesearch("answers", subnode)
        
        print "%s:" % snid
        gen_text(textnode.text, "    ")
        gen_response_block(answernode, "    ")
        print ""


def main(args):
    infile=sys.stdin
    
    # Insert arg handling fanciness here
    if len(args)!=0:
        infile=open(args[1], 'r')
    
    convert(infile)


if __name__=="__main__":
    main(sys.argv)
