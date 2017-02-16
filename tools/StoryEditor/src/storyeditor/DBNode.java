/*
 * Represents a single storynode as stored in the Nudge database.
 */

package storyeditor;

import java.util.ArrayList;

/**
 *
 * @author mgohde
 */
public class DBNode 
{
    String name;
    String text;
    
    ArrayList<DBAnswer> answers;
    
    public DBNode()
    {
        answers=new ArrayList<>();
    }
}
