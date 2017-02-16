/*
 * Represents an individual answer node as stored in the Nudge database.
 */

package storyeditor;

import java.util.ArrayList;

/**
 *
 * @author mgohde
 */
public class DBAnswer 
{
    String answer;
    String ownerName;
    String text;
    
    ArrayList<Integer> probList;
    ArrayList<String> destNodeNames;
    
    public DBAnswer()
    {
        probList=new ArrayList<>();
        destNodeNames=new ArrayList<>();
    }
    
    public void calcProb(int low, int high)
    {
        if(low==0)
        {
            this.probList.add(high);
        }
        
        else
        {
            this.probList.add(high-low+1);
        }
    }
}
