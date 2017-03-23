/*
 * This class encapuslates the SyntaxUpdateFilterRunnable's state so that it can be maintained
 * between invocations. 
 */

package storyeditor;

import java.util.ArrayList;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;

/**
 *
 * @author mgohde
 */
public class SyntaxUpdateState 
{
    public boolean firstIteration;
    public String contents;
    
    ArrayList<Element> documentElements;
    ArrayList<AttributeSet> attributeSets;
    
    public SyntaxUpdateState()
    {
        firstIteration=true;
        contents=null;
        
        documentElements=new ArrayList<>();
        attributeSets=new ArrayList<>();
    }
    
    
}
