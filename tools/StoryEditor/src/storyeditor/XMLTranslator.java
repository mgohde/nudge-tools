/*
 * This class exists to act as a translator between XML story formats and the 
 * story format used by story2xml and this program.
 */

package storyeditor;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author mgohde
 */
public class XMLTranslator 
{
    private String contents;
    
    public XMLTranslator(File f) throws IOException
    {
        contents=new String(Files.readAllBytes(f.toPath()));
    }
    
    public XMLTranslator(String s)
    {
        contents=s;
    }
    
    public String translate() throws ParserConfigurationException, SAXException, IOException
    {
        String title;
        DocumentBuilderFactory f=DocumentBuilderFactory.newInstance();
        DocumentBuilder b=f.newDocumentBuilder();
        Document d=b.parse(new InputSource(new StringReader(this.contents)));
        NodeList n;
        Node rootNode;
        String s="";
        
        n=d.getChildNodes();
        if(n.getLength()==0)
        {
            return null;
        }
        
        rootNode=n.item(0);
        title=rootNode.getAttributes().getNamedItem("title").getTextContent();
        
        n=rootNode.getChildNodes();
        
        s+="title: "+title+"\n";
        
        //This nasty mess of for-loops is to avoid having to recurse 
        //through this tree structure.
        for(int i=0;i<n.getLength();i++)
        {
            Node curNode=n.item(i);
            if(curNode.getNodeName().equals("node"))
            {
                //See if we can get a node id:
                s+=curNode.getAttributes().getNamedItem("id").getTextContent()+":\n";
                
                NodeList textAnswers=curNode.getChildNodes();
                
                for(int j=0;j<textAnswers.getLength();j++)
                {
                    Node textAnswer=textAnswers.item(j);
                    
                    if(textAnswer.getNodeName().equals("text"))
                    {
                        s+="\t"+textAnswer.getTextContent()+"\n";
                    }
                    
                    else if(textAnswer.getNodeName().equals("answers"))
                    {
                        s+="\tResponses:\n";
                        NodeList optionNodes=textAnswer.getChildNodes();
                        
                        for(int k=0;k<optionNodes.getLength();k++)
                        {
                            Node optionText=optionNodes.item(k);
                            
                            //This loop depth is getting absurd.
                            if(optionText.getNodeName().equals("option"))
                            {
                                NodeList optionTextContentsList=optionText.getChildNodes();
                                ArrayList<Node> filteredContentsList=new ArrayList<>();
                                
                                //Filter through all destinations as needed:
                                for(int l=0;l<optionTextContentsList.getLength();l++)
                                {
                                    Node optionContent=optionTextContentsList.item(l);
                                    
                                    if(optionContent.getNodeName().equals("text")||optionContent.getNodeName().equals("dest"))
                                    {
                                        filteredContentsList.add(optionContent);
                                    }
                                }
                                
                                //Iterate over all of the filtered nodes:
                                for(int l=0;l<filteredContentsList.size();l++)
                                {
                                    Node optionContent=filteredContentsList.get(l);
                                    if(optionContent.getNodeName().equals("text"))
                                    {
                                        s+="\t\t"+optionContent.getTextContent()+" -> ";
                                    }

                                    else if(optionContent.getNodeName().equals("dest"))
                                    {
                                        String prob=optionContent.getAttributes().getNamedItem("p").getTextContent();
                                        if(l!=(filteredContentsList.size()-1))
                                        {
                                            //TODO: implement support for WITH statements.
                                            s+=prob+"% to "+optionContent.getTextContent()+", ";
                                        }

                                        else
                                        {
                                            s+=prob+"% to "+optionContent.getTextContent()+"\n";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                s+="\n";
            }
        }
        
        return s;
    }
}
