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
}
