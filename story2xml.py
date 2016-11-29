#!/usr/bin/python

import os
import sys


def print_err(errtext):
    sys.stderr.write(errtext+"\n")


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
        if len(l[0].strip())!=0:
            retlist.append(l)
    
    return retlist


def get_indentation_level(line):
    return max(len(line)-len(line.strip()), 0)


def gen_response(respline, indent):
    strippedrespline=respline[0].strip()
    print '%s<option>' % indent

    namedests=strippedrespline.split('->')
    namedests[0]=namedests[0].strip()
    if len(namedests)==2:
        print '%s\t<text>%s</text>' % (indent, namedests[0])
        
        destlines=namedests[1].split(',')
        dests=[]
        for d in destlines:
            desttoks=d.split()
            if len(desttoks)!=0:
                dests.append([desttoks[0].strip('%'), desttoks[len(desttoks)-1].strip()])
                
        # Do some sanity checking:
        destsum=0
        for d in dests:
            destsum=destsum+int(d[0])
        
        if destsum>100:
            print_err("Error: for response '%s' on line %s, destination probabilities exceed 100%%" % (namedests[0], respline[1]))
            raise Exception('Nasty stuff!')
        elif destsum<100:
            print_err("Error: for response '%s' on line %s, destination probabilities sum to a value below 100%%" % (namedests[0], respline[1]))
            raise Exception('Nasty stuff!')
        
        for d in dests:
            print '%s\t<dest p="%s">%s</dest>' % (indent, d[0], d[1])
                
    print '%s</option>' % indent
        

def generate_block(linelist):
    if len(linelist)==0:
        return
    
    linetoks=linelist[0][0].split()
    if len(linetoks)==0: # No valid start line.
        return
    elif linetoks[0].upper()=="TITLE:":
        print '<story title="%s">' % linelist[0][0][len('TITLE: '):]
        return
    
    print '\t<node id="%s">' % linetoks[0].strip().strip(':')
    textcontent=""
    respindent=0
    resplines=[]
    for l in linelist[1:]:
        sline=l[0].strip()
        linetoks=sline.split()
        
        if len(linetoks)!=0:
            # Determine what, exactly to do with this list.
            if linetoks[0][len(linetoks[0])-1]==':':
                if linetoks[0].upper()=="RESPONSES:":
                    respindent=2*get_indentation_level(l[0])
                elif linetoks[0].upper()=="NOTE:" or linetoks[0].upper()=="COMMENT:":
                    pass
                else:
                    print_err("Warning: for line %s, unknown token '%s'" % (l[1], linetoks[0]))
            elif get_indentation_level(l[0])==respindent:
                resplines.append(l)
            else:
                textcontent=textcontent+" "+sline
                
    print '\t\t<text>%s</text>' % textcontent.strip()
    print '\t\t<answers>'
    
    for r in resplines:
        gen_response(r, '\t\t\t')
    
    print '\t\t</answers>'
    print '\t</node>'
                    

def getlines(content):
    lines=content.splitlines()
    retarr=[]
    lnum=1
    
    for l in lines:
        retarr.append([l, lnum])
        lnum=lnum+1
    
    return retarr


def parse(infile):
    nodelist=[]
    contents=infile.read()
    lines=getlines(contents)
    #lines=contents.splitlines()
    lines=remove_blanks(lines)
    prevlevel=0
    
    #print '<story title="%s">' % get_title(lines)
    
    templist=[]
    for l in lines:
        level=get_indentation_level(l[0])
        if level==0:
            if len(templist)!=0:
                generate_block(templist)
            templist=[]
        
        templist.append(l)
    
    if len(templist)!=0:
        generate_block(templist)
    
    print '</story>'


def main(args):
    infile=sys.stdin
    
    if len(args)!=0:
        infile=open(args[1], 'r')
    
    try:
        parse(infile)
    except:
        # Suppress exceptions:
        print_err("Error encountered. Terminating...")

if __name__=="__main__":
    main(sys.argv)