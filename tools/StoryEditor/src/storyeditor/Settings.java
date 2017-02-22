/*
 * Container class for global settings.
 */

package storyeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author mgohde
 */
public class Settings 
{
    public String dbUsername;
    public String dbPassword;
    public String dbServer;
    public String dbName;
    public String settingsFilePath;
    public String loadSaveDir;
    
    
    public Settings()
    {
        dbUsername="user";
        dbPassword="password";
        dbServer="dowell-nudge.colorado.edu";
        dbName="nudge";
        settingsFilePath=System.getProperty("user.home")+"/.storyeditor.conf";
        loadSaveDir=System.getProperty("user.home");
    }
    
    public boolean settingsFileExists()
    {
        File f=new File(settingsFilePath);
        
        return f.exists();
    }
    
    public void loadSettings(File f) throws FileNotFoundException
    {
        Scanner s=new Scanner(f);
        
        while(s.hasNextLine())
        {
            String line=s.nextLine();
            String lineToks[]=line.split(" ");
            
            if(lineToks.length!=0)
            {
                switch(lineToks[0].trim())
                {
                    case "dbusername:":
                        dbUsername=lineToks[1];
                        break;
                    case "dbpassword:":
                        dbPassword=lineToks[1];
                        break;
                    case "dbserver:":
                        dbServer=lineToks[1];
                        break;
                    case "dbname:":
                        dbName=lineToks[1];
                        break;
                    case "loadsavedir:":
                        loadSaveDir=lineToks[1];
                        break;
                    default:
                        System.err.println("Encountered unknown token when parsing configuration: "+line);
                        System.err.println("Token: "+lineToks[0]);
                }
            }
        }
    }
    
    public void saveSettings(File f) throws FileNotFoundException
    {
        PrintWriter pw=new PrintWriter(f);
        
        pw.printf("%s: %s\n", "dbusername", dbUsername);
        pw.printf("%s: %s\n", "dbpassword", dbPassword);
        pw.printf("%s: %s\n", "dbserver", dbServer);
        pw.printf("%s: %s\n", "dbname", dbName);
        pw.printf("%s: %s\n", "loadsavedir", loadSaveDir);
        
        pw.flush();
        pw.close();
    }
    
    //Moderately redundant debugging function
    public void printSettings()
    {
        System.out.printf("%s: %s\n", "dbusername", dbUsername);
        System.out.printf("%s: %s\n", "dbpassword", dbPassword);
        System.out.printf("%s: %s\n", "dbserver", dbServer);
        System.out.printf("%s: %s\n", "dbname", dbName);
        System.out.printf("%s: %s\n", "loadsavedir", loadSaveDir);
    }
}
