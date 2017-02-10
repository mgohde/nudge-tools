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
    
    public String generateXML()
    {
        String s="";
        
        s+="\t\t\t<option>\n";
        s+="\t\t\t\t<text>"+this.prompt+"</text>\n";
        
        for(int i=0;i<Math.min(this.destNames.size(), this.destWeights.size());i++)
        {
            //TODO: add handling for in-game rewards. Whoops.
            s+="\t\t\t\t<dest p=\""+destWeights.get(i)+"\">"+destNames.get(i)+"</dest>\n";
        }
        
        s+="\t\t\t</option>\n";
        
        return s;
    }
    
    public boolean checkNodeWeights(ArrayList<String> errReport)
    {
        boolean weightsOk;
        int weightSum=0;
        
        for(Integer i:this.destWeights)
        {
            weightSum+=i;
        }
        
        weightsOk=(weightSum==100);
        
        //Only bother to check for specific error conditions if there is an error in weights overall.
        if(!weightsOk)
        {
            if(weightSum>100)
            {
                errReport.add("Error for response with prompt \""+this.prompt+"\". Sum of response weights is greater than 100%");
            }
            
            //The case for which weightSum==100 has been taken care of.
            else
            {
                errReport.add("Error for response with prompt \""+this.prompt+"\". Sum of response weights is less than than 100%");
            }
        }
        
        return weightsOk;
    }
}
