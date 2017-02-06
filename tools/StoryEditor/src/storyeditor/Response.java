/*
 * This represents an individual response (in text) that a user can provide
 * to Nudge.
 */

package storyeditor;

import java.util.ArrayList;

/**
 *
 * @author mgohde
 */
public class Response 
{
    Badge b;
    ArrayList<String> destNames;
    ArrayList<Integer> destWeights;
    String prompt;
    
    public Response()
    {
        b=null;
        destNames=new ArrayList<>();
        destWeights=new ArrayList<>();
        prompt="";
    }
    
    public boolean pointsToNode(String nodeName)
    {
        boolean retV=false;
        
        for(String s:destNames)
        {
            retV=retV||s.equals(nodeName);
        }
        
        return retV;
    }
    
    public boolean readResponseLine(String l)
    {
        String promptDests[]=l.split("->");
        
        try
        {
            this.prompt=promptDests[0].trim();
            
            //TODO: Account for WITH statements at some point.
            String withChunks[]=promptDests[1].split("with");
            String destChunks[]=withChunks[0].split(",");
            
            Badge b=new Badge();
            if(withChunks.length>1)
            {
                if(b.parseBadgeString(withChunks[1]))
                {
                    this.b=b;
                }
            }
            
            for(String s:destChunks)
            {
                String probDest[]=s.split("% to ");
                
                this.destNames.add(probDest[1].trim());
                this.destWeights.add(Integer.parseInt(probDest[0].trim()));
            }
            
            return true;
            
        } catch(ArrayIndexOutOfBoundsException e)
        {
            //Do nothing for now.
        }
        
        return false;
    }
    
    @Override
    public String toString()
    {
        String s=prompt+" -> ";
        int i;
        
        for(i=0;i<Math.min(destNames.size(), destWeights.size());i++)
        {
            s+=destWeights.get(i)+"% to "+destNames.get(i);
            
            if(i!=(Math.min(destNames.size(), destWeights.size())-1))
            {
                s+=", ";
            }
        }
        
        if(b!=null)
        {
            s+=" "+b;
        }
        
        return s;
    }
    
    public ArrayList<StoryNode> resolveDests(ArrayList<StoryNode> storyNodeList)
    {
        return null;
    }
}
