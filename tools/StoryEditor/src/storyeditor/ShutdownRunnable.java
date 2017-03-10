/*
 * Tiny class to implement appropriate shutdown hooks.
 */

package storyeditor;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author mgohde
 */
public class ShutdownRunnable implements Runnable
{
    private Settings s;
    
    public ShutdownRunnable(Settings s)
    {
        this.s=s;
    }
    
    public void run()
    {
        try
        {
            s.saveSettings(new File(s.settingsFilePath));
        } catch(IOException e)
        {
            System.err.println("Could not save settings.");
        }
    }
}
