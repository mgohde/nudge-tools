/*
 * A container class modeled after the BookInfo class presented in Oracle's
 * JTree tutorial.
 */

package storyeditor;

/**
 *
 * @author mgohde
 */
public class ServerNodeInfo 
{
    public static final int GLOBAL_STORY_NODE=1;
    public static final int LOCAL_STORY_NODE=2;
    public String storyName;
    public int storyType;
    
    public ServerNodeInfo(String newStoryName, int newStoryType)
    {
        storyName=newStoryName;
        storyType=newStoryType;
    }
    
    @Override
    public String toString()
    {
        return storyName;
    }
}
