/*
 * Since none of the other readily available approaches are working,
 * we will try occasionally asynchronously scanning the document and
 * applying formatting as necessary. 
 */

package storyeditor;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.StyledDocument;

/**
 *
 * @author mgohde
 */
public class SyntaxUpdateFilterRunnable implements Runnable
{
    private SyntaxUpdateState s;
    private JTextPane p;
    
    public SyntaxUpdateFilterRunnable(SyntaxUpdateState newS, JTextPane newP)
    {
        s=newS;
        p=newP;
    }
    
    @Override
    public void run()
    {
        int i;
        int charCtr;
        String contents=p.getText();
        String toks[]=contents.split(" ");
        int offsetArr[]=new int[toks.length];
        boolean highlightArr[]=new boolean[toks.length];
        
        
        for(i=0;i<highlightArr.length;i++)
        {
            highlightArr[i]=false;
        }
        
        //Parse contents and determine which elements need to be highlighted:
        i=0;
        charCtr=0;
        for(String str:toks)
        {
            if(str.contains(":"))
            {
                highlightArr[i]=true;
            }
            
            i++;
            charCtr+=str.length()+1; //Plus 1 since str was preceeded by whitespace.
        }
        
        
    }
}
