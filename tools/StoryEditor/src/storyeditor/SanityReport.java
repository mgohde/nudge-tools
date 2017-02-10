/*
 * This class encapsulates a number of fields related to 
 * internal story sanity testing.
 */

package storyeditor;

import java.util.ArrayList;

/**
 *
 * @author mgohde
 */
public class SanityReport 
{
    boolean canBeCompleted;
    ArrayList<String> completionStackTrace;
    boolean allProbabilitiesCorrect;
    ArrayList<String> probErrMessages;
    boolean noSelfReferentialNodes;
    ArrayList<String> selfReferentialNodeNames;
    boolean noCircularReferences;
    ArrayList<String> circularReferenceCheckMessages;
    
    
    public SanityReport()
    {
        canBeCompleted=true;
        completionStackTrace=new ArrayList<>();
        allProbabilitiesCorrect=true;
        probErrMessages=new ArrayList<>();
        noSelfReferentialNodes=true;
        selfReferentialNodeNames=new ArrayList<>();
        noCircularReferences=true;
        circularReferenceCheckMessages=new ArrayList<>();
    }
    
    public String toString()
    {
        String s="";
        
        s+="Can be completed? "+canBeCompleted+"\n";
        for(String str:completionStackTrace)
        {
            s+="\t"+str+"\n";
        }
        
        s+="Are all probabilities correct? "+allProbabilitiesCorrect+"\n";
        for(String str:probErrMessages)
        {
            s+="\t"+str+"\n";
        }
        
        s+="Are there no self-referential nodes? "+noSelfReferentialNodes+"\n";
        for(String str:selfReferentialNodeNames)
        {
            s+="\t"+str+"\n";
        }
        
        s+="Are there no circular references? "+noCircularReferences+"\n";
        for(String str:circularReferenceCheckMessages)
        {
            s+="\t"+str+"\n";
        }
        
        return s;
    }
}
