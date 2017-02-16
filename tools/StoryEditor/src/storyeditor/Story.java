/*
 * This class manages a list of story nodes.
 */

package storyeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mgohde
 */
public class Story 
{
    public ArrayList<StoryNode> nodeList;
    String title;
    
    public Story()
    {
        nodeList=new ArrayList<>();
    }
    
    public static int getIndentationLevel(String s)
    {
        int strLength;
        int newLength;
        
        strLength=s.length();
        newLength=s.trim().length();
        
        return strLength-newLength;
    }
    
    public void loadStory(String storyContents)
    {
        String storyLines[];
        String tmpArr[];
        int curIndentationLevel;
        int i, j;
        String l;
        StoryNode curNode;
        ArrayList<StoryNode> tempList=new ArrayList<>();
        int newIndentationLevel;
        ArrayList<String> stringList=new ArrayList<>();
        
        storyLines=storyContents.split("\n");
        
        try
        {
            tmpArr=storyLines[0].split(": ");
            this.title=tmpArr[1];
        } catch(IndexOutOfBoundsException e)
        {
            System.err.println("Error: story title declaration invalid.");
            System.err.println("Title line contents: "+storyLines[0]);
        }
        
        for(i=1;i<storyLines.length;i++)
        {
            l=storyLines[i];
            
            if(l.length()!=0)
            {
                newIndentationLevel=getIndentationLevel(l);
                if(l.charAt(l.length()-1)==':' && newIndentationLevel==0)
                {
                    if(!stringList.isEmpty())
                    {
                        curNode=new StoryNode();
                        curNode.parse(stringList);
                        tempList.add(curNode);
                        stringList.clear();
                    }
                }
                
                stringList.add(l);
            }
        }
        
        System.out.println("Does stringList still have data? "+(!stringList.isEmpty()));
        
        if(!stringList.isEmpty())
        {
            System.out.println("Remaining content: ");
            for(String s:stringList)
            {
                System.out.println(s);
            }
            
            curNode=new StoryNode();
            curNode.parse(stringList);
            tempList.add(curNode);
            stringList.clear();
        }
        
        this.nodeList=tempList;
    }
    
    public void loadStory(Path p) throws IOException
    {
        byte contents[];
        String contentString;
        contents=Files.readAllBytes(p);
        contentString=new String(contents);
        
        loadStory(contentString);
    }
    
    public void saveStory(File f) throws FileNotFoundException
    {
        System.out.println("Saving story to "+f);
        PrintWriter pw=new PrintWriter(f);
        
        pw.print(this.toString());
        System.out.println("Contents printed:");
        System.out.println(this.toString());
        
        pw.flush();
        pw.close();
    }
    
    @Override
    public String toString()
    {
        String s="";
        
        s+="Title: "+this.title+"\n";
        for(StoryNode n:this.nodeList)
        {
            s+=n+"\n";
        }
        
        return s;
    }
    
    public StoryNode findNode(String nodeName)
    {
        StoryNode n=null;
        
        for(StoryNode newN:this.nodeList)
        {
            if(newN.name.equals(nodeName))
            {
                n=newN;
            }
        }
        
        return n;
    }
    
    public ArrayList<StoryNode> findParents(String destName)
    {
        ArrayList<StoryNode> retList=new ArrayList<>();
        
        for(StoryNode n:this.nodeList)
        {
            for(Response r:n.respList)
            {
                if(r.pointsToNode(destName))
                {
                    retList.add(n);
                }
            }
        }
        
        return retList;
    }
    
    public String generateXML()
    {
        String s="";
        
        s+="<story title=\""+this.title+"\">\n";
        
        for(StoryNode n:this.nodeList)
        {
            s+=n.generateXML();
        }
        
        s+="</story>\n";
        return s;
    }
    
    public void exportAsXML(File f) throws FileNotFoundException
    {
        PrintWriter pw=new PrintWriter(f);
        
        pw.print(this.generateXML());
        
        pw.flush();
        pw.close();
    }
    
    private void checkNodeWeights(SanityReport r)
    {
        for(StoryNode n:this.nodeList)
        {
            r.allProbabilitiesCorrect=r.allProbabilitiesCorrect&&n.checkNodeWeights(r.probErrMessages);
        }
    }
    
    private void checkSelfReferentialNodes(SanityReport r)
    {
        for(StoryNode n:this.nodeList)
        {
            r.noSelfReferentialNodes=r.noSelfReferentialNodes&&n.checkSelfReferentialNodes(r.selfReferentialNodeNames);
        }
    }
    
    //This is, as said on the tin, an extremely expensive and poorly implemented
    //check on all nodes to ensure that circular references are not possible.
    private void extremelyExpensiveAndPoorlyImplementedReferenceCheck(SanityReport r)
    {
       r.noCircularReferences=rcsvTerribleReferenceCheck(this.nodeList.get(0), r);
    }
    
    private StoryNode getNode(String nodeName)
    {
        StoryNode n=null;
        
        for(StoryNode tmp:this.nodeList)
        {
            if(tmp.name.endsWith(nodeName))
            {
                n=tmp;
                break;
            }
        }
        
        return n;
    }
    
    private boolean rcsvTerribleReferenceCheck(StoryNode n, SanityReport r)
    {
        if(n.hasBeenVisited)
        {
            return false;
        }
        
        else if(n.respList.isEmpty())
        {
            r.circularReferenceCheckMessages.add("Error with node: "+n.name+". Node has no children or endpoints.");
            return false;
        }
        
        else
        {
            n.hasBeenVisited=true;
            
            ArrayList<String> dests=n.getCompleteDestList();
            
            //Recurse through all of this node's children and perform the same circular sanity check.
            for(String s:dests)
            {
                StoryNode tmp=getNode(s);
                
                if(tmp!=null)
                {
                    //So that we can break on a self-referential node:
                    if(!rcsvTerribleReferenceCheck(tmp, r))
                    {
                        return false;
                    }
                }
                
                else if(!s.toUpperCase().equals("END"))
                {
                    r.circularReferenceCheckMessages.add("Error with node: "+n.name+". Reference is made to another node that neither exists nor is an END node.");
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public void checkCanBeCompleted(SanityReport r)
    {
        
    }
    
    public SanityReport sanityTest()
    {
        SanityReport r=new SanityReport();
        
        checkNodeWeights(r);
        checkSelfReferentialNodes(r);
        extremelyExpensiveAndPoorlyImplementedReferenceCheck(r);
        
        return r;
    }
    
    /**
     * Effectively implements dbtool's functionality.
     * @return A string containing SQL statements.
     */
    public String toSQL()
    {
        String s="";
        
        for(StoryNode n:this.nodeList)
        {
            s+=n.toSQL(this.title);
        }
        
        return s;
    }
}
