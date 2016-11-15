#!/usr/bin/python

import os
import sys

def get_title(linelist):
    for l in linelist:
        toks=l.split()
        if len(toks)!=0:
            if toks[0].upper()=="TITLE:":
                return l[len("TITLE: ")]
    return ""


def remove_blanks(linelist):
    retlist=[]
    
    for l in linelist:
        if len(l.strip())!=0:
            retlist.append(l)
    
    return retlist


def get_indentation_level(line):
    return len(line)-len(line.strip())


def parse(infile):
    nodelist=[]
    contents=infile.read()
    lines=contents.splitlines()
    lines=remove_blanks(lines)
    prevlevel=0
    
    print '<story title="%s">' % get_title(lines)
    
    for l in lines:
        curlevel=get_indentation_level()
        
        if curlevel==0:
            print '<node id="%s">' % l.strip(':')
        elif curlevel==prevlevel:
            if l[len(l)-1]==':':
                print '\t<answers>'
                print '\t\t<text>%s</text>' % l.strip().strip(':')
                
            else:
                sys.stdout.write(l.strip())
        elif curlevel>prevlevel:
            pass
        else:
            pass


def main(args):
    infile=sys.stdin
    
    if len(args)!=0:
        infile=open(args[1], 'r')
        if len(args)>=2:
            sys.stdout=open(args[2], 'w')
        
    nodelist=parse(infile)

if __name__=="__main__":
    main(sys.argv)