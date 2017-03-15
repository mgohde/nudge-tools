/*
 * This class represents a filter for the main editor window that does syntax highlighting
 * and such.
 */

package storyeditor;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
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
    private JTextPane pane;
    
    public EditorFilter(StyledDocument d, JTextPane p)
    {
        this.d=d;
        this.cxt=StyleContext.getDefaultStyleContext();
        
        highlightedText=cxt.addAttribute(cxt.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        normalText=cxt.addAttribute(cxt.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        
        //this.pattern=Pattern.compile("^[ \\t]*\\w*:"); //This will match all highlighted syntax nuggets.
        this.pattern=Pattern.compile("\\w*:");
        
        this.pane=p;
    }
    
    private void queueStyleUpdate()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    updateStyle();
                } catch(BadLocationException e)
                {
                    System.err.println("Attempted to match invalid string in document.");
                    e.printStackTrace();
                }
            }
        });
    }
    
    @Override
    public void insertString(FilterBypass f, int offset, String text, AttributeSet a) throws BadLocationException
    {
        super.insertString(f, offset, text, a);
        queueStyleUpdate();
    }
    
    @Override
    public void remove(FilterBypass f, int offset, int length) throws BadLocationException
    {
        super.remove(f, length, length);
        queueStyleUpdate();
    }
    
    @Override
    public void replace(FilterBypass f, int offset, int length, String text, AttributeSet a) throws BadLocationException
    {
        super.replace(f, length, length, text, a);
        queueStyleUpdate();
    }
    
    private void updateStyle() throws BadLocationException
    {
        Matcher m;
        //Clear all attributes. 
        d.setCharacterAttributes(0, d.getLength(), normalText, true);
        
        m=pattern.matcher(d.getText(0, d.getLength()));
        
        while(m.find())
        {
            d.setCharacterAttributes(m.start(), m.end(), highlightedText, true);
        }
    }
}
