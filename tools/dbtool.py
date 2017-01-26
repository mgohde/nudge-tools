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
    position=0;
    resid=1
    
    root=ET.fromstring(contents)
    storytitle=root.attrib['title']
    
    for subnode in root:
        answerchoice='A'
        snid=subnode.attrib['id']
        textnode=nodesearch("text", subnode)
        answernode=nodesearch("answers", subnode)


        print "-- Generated statements for node: %s" % snid

        # Check all destinations to see if there is an END node, and if there is, insert it into the rewards table.
        for a in answernode:
            destsearch=manynodesearch("dest", a)
            for d in destsearch:
                if(d.text=="END"):
                    position=1
                    try:
                        numpoints=int(d.attrib['points'])
                        rewardname=d.attrib['reward']
                        rewardtext=d.attrib['rewardtext']
                        endname="e%d" % curid
                        
                        print "INSERT INTO rewardss (reward, statement, points, end_id, end, storytitle) VALUES ('%s', '%s', %d, %d, '%d', '%d');" % (rewardname, rewardtext, numpoints, curid, endname, storytitle)
                    except:
                         pass
                     


        print "INSERT INTO storytable VALUES (%d,'%s','%s','%s',%d);" % (curid, storytitle, snid, sanitize(textnode.text), position)

        # This ensures that the story will have valid entry and exit points.
        position=2

        curid+=1

        for a in answernode:
            optiontextnode=nodesearch("text", a)
            destsearch=manynodesearch("dest", a)
            minprob=0

            print "INSERT INTO answers VALUES ('%s','%s','%s','%s');" % (storytitle, snid, answerchoice, sanitize(optiontextnode.text))

            for d in destsearch:
                    maxprob=minprob+int(d.attrib['p'])
                    print "INSERT INTO results VALUES (%d,'%s','%s','%s',%d,%d,'%s');" % (resid, storytitle, snid, answerchoice, minprob, maxprob, d.text)
                    minprob=minprob+int(d.attrib['p'])
                    resid+=1

        answerchoice=chr(ord(answerchoice)+1)


def delstory(infile):
    contents=infile.read()
    curid=1
    resid=1
    
    root=ET.fromstring(contents)
    storytitle=root.attrib['title']
    
    print "-- Generated statements for story: %s" % storytitle
    print "DELETE FROM storytable WHERE storytitle='%s';" % storytitle
    print "DELETE FROM answers WHERE storytitle='%s';" % storytitle
    print "DELETE FROM results WHERE storytitle='%s';" % storytitle
    

def printusage(progname):
    print "Usage: %s [-d] [input filename]" % progname
    print "Generate SQL statements to install or delete a storyline from a Nudge SQL database"
    print "Generates statements to install if -d is not specified."
    print ""
    print "Arguments:"
    print "  -d\tGenerate statements to delete a storyline."
    print "[input filename] may be blank. In this case, %s attempts to read a story from standard input." % progname
    
    
def main(args):
    infile=sys.stdin
    delete=False
    
    # Insert arg handling fanciness here
    if len(args)!=1:
        if args[1]=='-d':
            delete=True
            if len(args)>1:
                infile=open(args[2], 'r')
        elif args[1]=='-h' or args[1]=='--h':
            printusage(args[0])
            return
        else:
            infile=open(args[1], 'r')

    if not delete:
        readstory(infile)
    else:
        delstory(infile)
    


if __name__=="__main__":
    main(sys.argv)
