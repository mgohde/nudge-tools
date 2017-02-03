/*
 * This represents a reward that can be given to a user on various end conditions, etc.
 *
 * This class was implemented to encapsulate "with" statement parsing capabilities
 * StoryEditor. 
 */

package storyeditor;

/**
 *
 * @author mgohde
 */
public class Badge 
{
    String title;
    String description;
    int numPoints;
    
    public Badge()
    {
        title=null;
        description=null;
        numPoints=0;
    }
    
    /**
     * Attempts to parse a reward declaration string.
     * Example: (title; description; 10)
     * @param str
     * @return 
     */
    public boolean parseBadgeString(String str)
    {
        String strToks[]=str.split(";");
        
        if(strToks.length!=3)
        {
            return false;
        }
        
        this.title=strToks[0].replace("(", "").trim();
        this.description=strToks[1].trim();
        this.numPoints=Integer.parseInt(strToks[2].replace(")", "").trim());
        
        return true;
    }
    
    @Override
    public String toString()
    {
        String s="";
        
        s="with ("+title+"; "+description+"; "+numPoints+")";
        
        return s;
    }
}
