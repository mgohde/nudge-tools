/*
 * This class represents a filter for the main editor window that does syntax highlighting
 * and such.
 */

package storyeditor;

import java.awt.Color;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author mgohde
 */
public class EditorFilter extends DocumentFilter
{
    private StyledDocument d;
    private StyleContext cxt;
    private AttributeSet highlightedText;
    private AttributeSet normalText;
    private Pattern pattern;
    
    public EditorFilter(StyledDocument d)
    {
        this.d=d;
        this.cxt=StyleContext.getDefaultStyleContext();
        
        highlightedText=cxt.addAttribute(cxt.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        normalText=cxt.addAttribute(cxt.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        
        this.pattern=new Pattern(); //This will match all highlighted syntax nuggets.
    }
}
