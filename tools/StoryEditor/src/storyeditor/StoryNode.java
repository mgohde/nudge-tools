/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storyeditor;

import java.util.ArrayList;

/**
 *
 * @author mgohde
 */
public class StoryNode 
{
    String name;
    String text;
    
    ArrayList<StoryNode> destList;
    ArrayList<Integer> weightList;
    ArrayList<Response> respList;
    
    public StoryNode()
    {
        name="";
        text="";
        destList=new ArrayList<>();
        weightList=new ArrayList<>();
        respList=new ArrayList<>();
    }
    
    public void parse(ArrayList<String> lines)
    {
        int i;
        String toks[];
        String line;
        int textIndentationLevel;
        boolean subBlockStarted=false;
        ArrayList<String> subLines=new ArrayList<>();
        String subBlockType="";
        String upperCaseTok;
        
        textIndentationLevel=Story.getIndentationLevel(lines.get(1));
        //The first line should give us a node name:
        line=lines.get(0);
        //Remove the colon at the end of the line:
        this.name=line.trim().substring(0, line.length()-1);
        
        for(i=1;i<lines.size();i++)
        {
            line=lines.get(i);
            toks=line.trim().split(" ");
            
            //If we're on the base indentation level for the block, then attempt
            //to check for special keywords, etc.
            if((Story.getIndentationLevel(line)==textIndentationLevel)&&(!subBlockStarted))
            {
                if(toks.length!=0)
                {
                    upperCaseTok=toks[0].toUpperCase();
                    
                    if(upperCaseTok.equals("NOTE:")||upperCaseTok.equals("COMMENT:"))
                    {
                        //Do nothing.
                    }
                    
                    else if(upperCaseTok.equals("RESPONSES:"))
                    {
                        subBlockStarted=true;
                        subBlockType=upperCaseTok;
                    }
                    
                    else
                    {
                        this.text+=(line.trim()+" ");
                    }
                }
            }
            
            //Wrap up storing lines in any sub-block and call any appropriate parsing function.
            /*
            else if(subBlockStarted)
            {
                //So that we go back to the first 'if' in this chain.
                i--;
                subBlockStarted=false;
                
                if(subBlockType.equals("RESPONSES:"))
                {
                    for(String l:subLines)
                    {
                        Response r=new Response();
                        r.readResponseLine(l);
                        this.respList.add(r);
                    }
                }
                
                subLines.clear();
            }*/
            
            else
            {
                Response r=new Response();
                if(r.readResponseLine(line.trim()))
                {
                    this.respList.add(r);
                }
            }
        }
    }
    
    @Override
    public String toString()
    {
        String s=name;
        
        s=s+":\n";
        s=s+"\t"+text+"\n";
        s=s+"\t"+"Responses:\n";
        
        for(Response r:this.respList)
        {
            s+="\t\t"+r+"\n";
        }
        
        return s;
    }
    
    /**
     * Returns whether the current StoryNode has all fields filled in.
     * @return 
     */
    public boolean isComplete()
    {
        boolean completeness=true;
        
        completeness=completeness&&(this.name!=null)&&(this.text!=null);
        completeness=completeness&&(!this.respList.isEmpty());
        
        return completeness;
    }
}
