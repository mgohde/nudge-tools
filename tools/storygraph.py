#!/usr/bin/python
import os
import sys
# Because of course Python has XML parsing built in. 
import xml.etree.ElementTree as ET

# dbtool.py -- Quick and dirty tool to insert XML formatted storylines into the database.

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


def readstory(infile):
    contents=infile.read()
    curid=1
    resid=1
    
    root=ET.fromstring(contents)
    storytitle=root.attrib['title']
    storytitle=storytitle.replace(" ", "_")
    
    # Start the DOT file:
    print "graph %s  {" % storytitle
    print '  size="40,40";';
    
    for subnode in root:
        snid=subnode.attrib['id']
        textnode=nodesearch("text", subnode)
        answernode=nodesearch("answers", subnode)
        
        print "  %s [shape=box];" % (snid)
        
        anid=0
        
        for a in answernode:
            optiontextnode=nodesearch("text", a)
            destsearch=manynodesearch("dest", a)
            
            an_name=snid+'_'+'O'+str(anid)
            print "  %s [color=blue];" % an_name
            
            print "  %s -- %s [ color=blue ];" % (snid, an_name)
            
            #print "INSERT INTO answers VALUES ('%s','%s','%s','%s')" % (storytitle, snid, answerchoice, sanitize(optiontextnode.text))
            
            for d in destsearch:
                print '  %s -- %s [ label="%s" ];' % (an_name, d.text, d.attrib['p'])
                resid+=1
            anid=anid+1
            
    print '}'
    return None


def main(args):
    infile=sys.stdin
    
    # Insert arg handling fanciness here
    if len(args)!=0:
        infile=open(args[1], 'r')
    
    story=readstory(infile)


if __name__=="__main__":
    main(sys.argv)
