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
    //General database access information:
    public String dbUsername;
    public String dbPassword;
    public String dbServer;
    public String dbName;
    
    //Local state information.
    public String settingsFilePath;
    public String loadSaveDir;
    
    //Database export credentials:
    public String userName;
    public String password;
    
    
    public Settings()
    {
        dbUsername="user";
        dbPassword="password";
        dbServer="dowell-nudge.colorado.edu";
        dbName="nudge";
        settingsFilePath=System.getProperty("user.home")+"/.storyeditor.conf";
        loadSaveDir=System.getProperty("user.home");
        userName="someuser";
        password="password";
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
                    case "username:":
                        userName=lineToks[1];
                        break;
                    case "password:":
                        password=lineToks[1];
                        break;
                    default:
                        System.err.println("Encountered unknown token when parsing configuration: "+line);
                        System.err.println("Token: "+lineToks[0]);
                }
            }
        }
    }
    
    private void genSettingsDump(PrintWriter pw)
    {
        pw.printf("%s: %s\n", "dbusername", dbUsername);
        pw.printf("%s: %s\n", "dbpassword", dbPassword);
        pw.printf("%s: %s\n", "dbserver", dbServer);
        pw.printf("%s: %s\n", "dbname", dbName);
        pw.printf("%s: %s\n", "loadsavedir", loadSaveDir);
        
        pw.printf("%s: %s\n", "username", userName);
        pw.printf("%s: %s\n", "password", password);
        
        pw.flush();
    }
    
    public void saveSettings(File f) throws FileNotFoundException
    {
        PrintWriter pw=new PrintWriter(f);
        
        genSettingsDump(pw);
        pw.close();
    }
    
    //Moderately redundant debugging function
    public void printSettings()
    {
        PrintWriter pw=new PrintWriter(System.out);
        genSettingsDump(pw);
        pw.close();
    }
    
    //This is such a commonly used task that it makes sense to make a method for it.
    public File getSettingsFile()
    {
        return new File(this.settingsFilePath);
    }
}
