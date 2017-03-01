/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storyeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
    
    boolean hasBeenVisited;
    
    public StoryNode()
    {
        name="";
        text="";
        destList=new ArrayList<>();
        weightList=new ArrayList<>();
        respList=new ArrayList<>();
        hasBeenVisited=false;
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
        System.out.println("lines.get(1): "+lines.get(1));
        System.out.println("textIndentationlevel: "+textIndentationLevel);
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
            System.out.println("Line: "+line);
            System.out.println("Indentation level: "+Story.getIndentationLevel(line));
            if((Story.getIndentationLevel(line)==textIndentationLevel))//&&(!subBlockStarted))
            {
                if(toks.length!=0)
                {
                    upperCaseTok=toks[0].toUpperCase();
                    System.out.println("upperCaseTok: "+upperCaseTok);
                    
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
        s=s+"\t"+text.trim()+"\n";
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
    
    public void drawNode(BufferedImage img, boolean clearImg, Story s)
    {
        Graphics g;
        int heightPerDest;
        int destColIdx;
        int destWeightColIdx;
        int numDests=0;
        g=img.getGraphics();
        
        if(clearImg)
        {
            g.setColor(Color.white);
            g.fillRect(0, 0, img.getWidth(), img.getHeight());
        }
        
        g.setColor(Color.black);
        
        //Attempt to draw contents:
        g.drawString(this.name, img.getWidth()/2, img.getHeight()/2);
        
        //Now draw lines to all of the subnodes:
        if(!this.respList.isEmpty())
        {
            //Get all responses:
            for(Response r:respList)
            {
                for(String str:r.destNames)
                {
                    numDests++;
                }
            }
            
            heightPerDest=img.getHeight()/numDests;
            destColIdx=img.getWidth()*3/4;
            destWeightColIdx=img.getWidth()*5/8;
            
            int ctr=0;
            int x, y;
            x=img.getWidth()/2+(img.getWidth()-destColIdx)/2;

            for (Response r : respList) {
                for(int i=0;i<r.destNames.size();i++)
                {
                    String destName=r.destNames.get(i);
                    int destWeight=r.destWeights.get(i);
                    //Determine if the selected child node exists:
                    if(destName.equals("END"))
                    {
                        g.setColor(Color.blue);
                    }
                    
                    else if(s.findNode(destName)==null)
                    {
                        g.setColor(Color.red);
                    }
                    
                    g.drawLine(img.getWidth()/2, img.getHeight()/2, destColIdx, (heightPerDest*ctr+15));
                    
                    g.drawString(destName, destColIdx, heightPerDest*ctr+15);
                    
                    //TODO: Find a better way to compute the constant offset here.
                    y=heightPerDest/2*(ctr)+80;
                    g.drawString(""+destWeight, x, y);
                    ctr++;
                    
                    g.setColor(Color.black);
                }
            }
        }
        
        //Attempt to draw parents:
        ArrayList<StoryNode> parents=s.findParents(this.name);
        if(!parents.isEmpty())
        {
            heightPerDest=img.getHeight()/parents.size();
            destColIdx=15;
            
            for(int i=0;i<parents.size();i++)
            {
                StoryNode p=parents.get(i);
                g.drawLine(img.getWidth()/2, img.getHeight()/2, destColIdx, (heightPerDest*i+15));
                g.drawString(p.name, destColIdx, (heightPerDest*i+15));
                
                int destWeight=0;
                
                //Get the weight that this node had in its parent:
                for(Response r:p.respList)
                {
                    for(int j=0;j<r.destNames.size();j++)
                    {
                        if(r.destNames.get(j).equals(this.name))
                        {
                            destWeight=r.destWeights.get(j);
                        }
                    }
                }
                
                //Render the weight of the child from the parent.
                int x=img.getWidth()/3;
                int y=heightPerDest/2*(i)+80;
                g.drawString(""+destWeight, x, y);
            }
        }
    }
    
    public String generateXML()
    {
        String s="";
        
        s+="\t<node id=\""+this.name+"\">\n";
        s+="\t\t<text>"+this.text.trim()+"</text>\n";
        s+="\t\t<answers>\n";
        
        for(Response r:this.respList)
        {
            s+=r.generateXML();
        }
        
        s+="\t\t</answers>\n";
        s+="\t</node>\n";
        return s;
    }
    
    public boolean checkNodeWeights(ArrayList<String> errReport)
    {
        boolean weightsOk=true;
        
        for(Response r:this.respList)
        {
            weightsOk=weightsOk&&r.checkNodeWeights(errReport);
        }
        
        return weightsOk;
    }
    
    //Returns true if there are no responses for which this node references itself.
    public boolean checkSelfReferentialNodes(ArrayList<String> errReport)
    {
        boolean noSelfReferences=true;
        
        for(Response r:this.respList)
        {
            noSelfReferences=noSelfReferences&&(!r.pointsToNode(this.name));
        }
        
        if(!noSelfReferences)
        {
            errReport.add("Node "+this.name+" has itself as a destination.");
        }
        
        return noSelfReferences;
    }
    
    public ArrayList<String> getCompleteDestList()
    {
        ArrayList<String> completeDestList=new ArrayList<>();
        
        for(Response r:this.respList)
        {
            completeDestList.addAll(r.destNames);
        }
        
        return completeDestList;
    }
    
    public Response getResponse(String prompt)
    {   
        for(Response r:this.respList)
        {
            if(r.prompt.equals(prompt))
            {
                return r;
            }
        }
        
        return null;
    }
}
