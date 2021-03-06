/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyeditor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author mgohde
 */
public class StoryFrame extends javax.swing.JFrame {
    Settings settings;
    //SyntaxFilter sf;
    StoryNode lastSelectedStoryNode;
    ArrayList<StoryNode> rawNodeList;
    int lastSelectedStoryNodeIndex;
    Story internalStory;
    DrawPanel p;
    DefaultListModel nodeNameListModel;
    /**
     * Creates new form NewJFrame
     */
    public StoryFrame() 
    {
        DefaultListModel m;
        initComponents();
        
        //Determine if we have a settings file:
        settings=new Settings();
        if(settings.settingsFileExists())
        {
            try
            {
                settings.loadSettings(new File(settings.settingsFilePath));
            } catch(IOException e)
            {
                System.err.println("Could not save default settings file to: "+settings.settingsFilePath);
            }
        }
        
        else
        {
            try
            {
                settings.saveSettings(new File(settings.settingsFilePath));
            } catch(IOException e)
            {
                System.err.println("Could not create new settings file at "+settings.settingsFilePath);
            }
            
        }
        
        this.codeBox.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        /*
        sf=new SyntaxFilter();
        
        if(this.codeBox.getStyledDocument() instanceof AbstractDocument)
        {
            AbstractDocument a=(AbstractDocument) this.codeBox.getStyledDocument();
            a.addDocumentListener(sf);
        }
        
        else
        {
            System.err.println("codeBox does not contain AbstractDocument. Cannot do code highlighting.");
        } */
        
        rawNodeList=new ArrayList<>();
        internalStory=new Story();
        lastSelectedStoryNode=null;
        
        nodeNameList.addListSelectionListener(new ListSelectionListener()
        {

            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {
                dispatchNodeNameEvent(lse);
            }
                    
        });
        
        //codeBox.setTabSize(4);
        
        p=new DrawPanel();
        p.setMinimumSize(new Dimension(100, 100));
        
        this.drawingPane.add(p);
        p.setSize(drawingPane.getWidth(), drawingPane.getHeight());
        BufferedImage b=new BufferedImage(p.getWidth(), p.getHeight(), BufferedImage.TYPE_INT_ARGB);
        p.setImg(b);
        updateFields();
        m=new DefaultListModel();
        this.nodeNameList.setModel(m);
        this.nodeNameListModel=m;
        
        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                p.setSize(drawingPane.getWidth(), drawingPane.getHeight());
                p.setImg(new BufferedImage(p.getWidth(), p.getHeight(), BufferedImage.TYPE_INT_ARGB));
                if(lastSelectedStoryNode!=null)
                {
                    lastSelectedStoryNode.drawNode(p.getImg(), true, internalStory);
                    p.repaint();
                }
            }
        });
        
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownRunnable(settings)));
    }
    
    private void dispatchNodeNameEvent(ListSelectionEvent lse)
    {
        int idx=lse.getLastIndex();
        StoryNode n;
        int realIdx;
        int i;
        ListSelectionModel m;
        
        m=this.nodeNameList.getSelectionModel();
        realIdx=m.getMinSelectionIndex();
        
        for(i=m.getMinSelectionIndex();i<m.getMaxSelectionIndex();i++)
        {
            if(m.isSelectedIndex(i))
            {
                realIdx=i;
            }
        }
        
        idx=realIdx;
        System.out.println("Index: "+idx);
        System.out.println("Nodelist size: "+internalStory.nodeList.size());
        if(idx<this.internalStory.nodeList.size()&&!this.internalStory.nodeList.isEmpty())
        {
            if(idx==-1)
            {
                idx=0;
            }
            
            n=this.internalStory.nodeList.get(idx);
            System.out.println("Selected story node "+n.name);
            codeBox.setText(n.toString());
            /*
            if(codeBox.getStyledDocument() instanceof AbstractDocument)
            {
                AbstractDocument a=(AbstractDocument) codeBox.getStyledDocument();
                try
                {
                    a.remove(0, a.getLength()-1);
                } catch(BadLocationException e)
                {
                    e.printStackTrace();
                    System.err.println("Got a BadLocationException when attempting to clear all data from an abstract document.");
                }
                
                sf=new SyntaxFilter();
                
                a.addDocumentListener(sf);
            }*/
            
            this.lastSelectedStoryNode=n;
            this.lastSelectedStoryNodeIndex=idx;
        }
        
        if(this.lastSelectedStoryNode!=null)
        {
            this.lastSelectedStoryNode.drawNode(p.getImg(), true, this.internalStory);
            p.repaint();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        nodeNameList = new javax.swing.JList();
        updateButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        codeBox = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        deleteButton = new javax.swing.JToggleButton();
        newItemButton = new javax.swing.JButton();
        drawingPane = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        newItem = new javax.swing.JMenu();
        newButton = new javax.swing.JMenuItem();
        openItem = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        importMenuItem = new javax.swing.JMenuItem();
        exportMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        serverImportItem = new javax.swing.JMenuItem();
        serverExportItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        titleItem = new javax.swing.JMenuItem();
        copyItem = new javax.swing.JMenuItem();
        pasteItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        sanityTestItem = new javax.swing.JMenuItem();
        publishItem = new javax.swing.JMenuItem();
        setDefaultsItem = new javax.swing.JMenuItem();
        createAccountItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutItem = new javax.swing.JMenuItem();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane2.setViewportView(nodeNameList);

        jScrollPane1.setViewportView(jScrollPane2);

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(codeBox);

        jLabel2.setText("Node");

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        newItemButton.setText("New");
        newItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newItemButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout drawingPaneLayout = new javax.swing.GroupLayout(drawingPane);
        drawingPane.setLayout(drawingPaneLayout);
        drawingPaneLayout.setHorizontalGroup(
            drawingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        drawingPaneLayout.setVerticalGroup(
            drawingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        newItem.setText("File");

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        newItem.add(newButton);

        openItem.setText("Open...");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        newItem.add(openItem);

        saveItem.setText("Save...");
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveItemActionPerformed(evt);
            }
        });
        newItem.add(saveItem);
        newItem.add(jSeparator1);

        importMenuItem.setText("Import from XML...");
        importMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importMenuItemActionPerformed(evt);
            }
        });
        newItem.add(importMenuItem);

        exportMenuItem.setText("Export as XML...");
        exportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMenuItemActionPerformed(evt);
            }
        });
        newItem.add(exportMenuItem);
        newItem.add(jSeparator2);

        serverImportItem.setText("Import from Server...");
        serverImportItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverImportItemActionPerformed(evt);
            }
        });
        newItem.add(serverImportItem);

        serverExportItem.setText("Export to Server...");
        serverExportItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverExportItemActionPerformed(evt);
            }
        });
        newItem.add(serverExportItem);
        newItem.add(jSeparator3);

        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        newItem.add(jMenuItem1);

        jMenuBar1.add(newItem);

        editMenu.setText("Edit");

        titleItem.setText("Title...");
        titleItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleItemActionPerformed(evt);
            }
        });
        editMenu.add(titleItem);

        copyItem.setText("Copy");
        copyItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyItemActionPerformed(evt);
            }
        });
        editMenu.add(copyItem);

        pasteItem.setText("Paste");
        pasteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteItemActionPerformed(evt);
            }
        });
        editMenu.add(pasteItem);

        jMenuBar1.add(editMenu);

        toolsMenu.setText("Tools");

        sanityTestItem.setText("Sanity test");
        sanityTestItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sanityTestItemActionPerformed(evt);
            }
        });
        toolsMenu.add(sanityTestItem);

        publishItem.setText("Publish...");
        publishItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publishItemActionPerformed(evt);
            }
        });
        toolsMenu.add(publishItem);

        setDefaultsItem.setText("Set defaults...");
        setDefaultsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setDefaultsItemActionPerformed(evt);
            }
        });
        toolsMenu.add(setDefaultsItem);

        createAccountItem.setText("Create account on database server...");
        createAccountItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAccountItemActionPerformed(evt);
            }
        });
        toolsMenu.add(createAccountItem);

        jMenuBar1.add(toolsMenu);

        helpMenu.setText("Help");

        aboutItem.setText("About...");
        aboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 182, Short.MAX_VALUE)
                                .addComponent(newItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(updateButton))
                            .addComponent(jScrollPane4))
                        .addContainerGap())
                    .addComponent(drawingPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(drawingPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(updateButton)
                            .addComponent(newItemButton)
                            .addComponent(deleteButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed
        new AboutFrame().setVisible(true);
    }//GEN-LAST:event_aboutItemActionPerformed

    /* This function will attempt to update everything onscreen whenever some
     * change is made to the storyline.
     */
    private void updateFields()
    {
        //Determine which nodes are not present in the current list
        //and add them as appropriate.
        DefaultListModel m=this.nodeNameListModel;
        
        for(StoryNode n:this.internalStory.nodeList)
        {
            if(!m.contains(n.name))
            {
                m.addElement(n.name);
            }
        }
        
        //TODO: add checks to see if this is viable.
        System.out.println("Index as of field update: "+this.lastSelectedStoryNodeIndex);
        this.nodeNameList.setSelectedIndex(this.lastSelectedStoryNodeIndex);
        System.out.println("Selected index: "+this.nodeNameList.getSelectedIndex());
        
        if(this.lastSelectedStoryNode!=null)
        {
            this.codeBox.setText(this.lastSelectedStoryNode.toString());
        }
        
        this.setTitle("Editing: "+this.internalStory.title);
        //TODO: Add something for selected nodes, etc.
        
        //Something to do with drawingPanel.
        if(this.lastSelectedStoryNode!=null)
        {
            this.lastSelectedStoryNode.drawNode(p.getImg(), true, this.internalStory);
            p.repaint();
        }
    }
    
    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
        JFileChooser jfc=new JFileChooser(settings.loadSaveDir);
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Story files", "story");
        jfc.setFileFilter(filter);
        
        
        int retV=jfc.showOpenDialog(this);
        
        if(retV==JFileChooser.APPROVE_OPTION)
        {
            System.out.println("File selected: "+jfc.getSelectedFile());
            Story newStory=new Story();
            try {
                newStory.loadStory(FileSystems.getDefault().getPath(jfc.getSelectedFile().toString()));
                this.internalStory=newStory;
                updateFields();
            } catch (IOException ex) {
                System.err.println("HOOOOooOOOoOoOooooooOooooOooooOOO! "+jfc.getSelectedFile());
            }
        }
        
        else
        {
            System.out.println("File open op discarded.");
        }
    }//GEN-LAST:event_openItemActionPerformed
    
    private String getExtension(String s)
    {
        String arr[]=s.split(".");
        
        if(arr.length==0)
        {
            return null;
        }
        
        else
        {
            return arr[arr.length-1];
        }
    }
    
    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        JFileChooser jfc=new JFileChooser(settings.loadSaveDir);
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Story files", "story");
        File f;
        jfc.setFileFilter(filter);
        
        int retV=jfc.showSaveDialog(this);
        
        if(retV==JFileChooser.APPROVE_OPTION)
        {
            System.out.println("File selected: "+jfc.getSelectedFile());
            f=jfc.getSelectedFile();
            
            if(!f.getName().contains("."))
            {
                f=new File(f.getAbsolutePath()+".story");
            }
            
            try {
                this.internalStory.saveStory(f);
                updateFields();
            } catch (IOException ex) {
                System.err.println("HOOOOooOOOoOoOooooooOooooOooooOOO! "+f);
            }
        }
        
        else
        {
            System.out.println("File save op discarded.");
        }
    }//GEN-LAST:event_saveItemActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        String textContent=codeBox.getText();
        
        //This is a quick workaround for a bug in the storynode parser.
        //(ie. every node's response list must end with a newline.)
        if(textContent.charAt(textContent.length()-1)!='\n')
        {
            textContent+="\n";
        }
        
        StoryNode newNode;
        String lineList[]=textContent.split("\n");
        ArrayList<String> lines=new ArrayList<>();
        lines.addAll(Arrays.asList(lineList));
        newNode=new StoryNode();
        newNode.parse(lines);
        
        //Determine the currently selected field:
        if(this.lastSelectedStoryNode!=null)
        {
            System.out.println("Text content: "+textContent);
            
            System.out.println("Number of lines: "+lines.size());
            
            this.internalStory.nodeList.set(this.lastSelectedStoryNodeIndex, newNode);
            this.lastSelectedStoryNode=newNode;
        }
        
        //It should be possible for a user to manually add a new story node to the story.
        else
        {
            System.out.println("New node created manually.");
            this.internalStory.nodeList.add(newNode);
            this.lastSelectedStoryNode=newNode;
        }
        
        System.out.println("In update, last selected index: "+this.lastSelectedStoryNodeIndex);
        
        updateFields();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void newItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newItemButtonActionPerformed
        //Creates a new node depending on the contents of a dialog.
        String newNodeName;
        
        newNodeName=(String) JOptionPane.showInputDialog(this, "Enter new node name:");
        
        if(newNodeName!=null)
        {
            StoryNode n=new StoryNode();
            n.name=newNodeName;
            this.internalStory.nodeList.add(n);
            updateFields();
        }
    }//GEN-LAST:event_newItemButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        
        if(!this.internalStory.nodeList.isEmpty())
        {
            this.internalStory.nodeList.remove(this.lastSelectedStoryNode);
            this.lastSelectedStoryNode=null;
            if(this.lastSelectedStoryNodeIndex>0)
            {
                this.lastSelectedStoryNodeIndex--;
            }

            else
            {
                this.lastSelectedStoryNodeIndex=0;
            }

            updateFields();
        }
        
        else
        {
            JOptionPane.showMessageDialog(this, "Cannot delete node from empty storyline.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void sanityTestItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sanityTestItemActionPerformed
        if(!internalStory.nodeList.isEmpty())
        {
            SanityReport r=this.internalStory.sanityTest();

            ErrorFrame eFrame=new ErrorFrame(r.toString());
            eFrame.setVisible(true);
        }
        
        else
        {
            JOptionPane.showMessageDialog(this, "Cannot perform a sanity check on an empty story.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_sanityTestItemActionPerformed

    private void exportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMenuItemActionPerformed
        JFileChooser jfc=new JFileChooser(settings.loadSaveDir);
        FileNameExtensionFilter filter=new FileNameExtensionFilter("XML story description files", "xml");
        jfc.setFileFilter(filter);
        
        int retV=jfc.showSaveDialog(this);
        
        if(retV==JFileChooser.APPROVE_OPTION)
        {
            System.out.println("Export file selected: "+jfc.getSelectedFile());
            try {
                this.internalStory.exportAsXML(jfc.getSelectedFile());
                updateFields();
            } catch (IOException ex) {
                System.err.println("HOOOOooOOOoOoOooooooOooooOooooOOO! "+jfc.getSelectedFile());
            }
        }
        
        else
        {
            System.out.println("File save op discarded.");
        }
    }//GEN-LAST:event_exportMenuItemActionPerformed

    private void importMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importMenuItemActionPerformed
        JFileChooser jfc=new JFileChooser(settings.loadSaveDir);
        FileNameExtensionFilter filter=new FileNameExtensionFilter("XML story description files", "xml");
        jfc.setFileFilter(filter);
        
        int retV=jfc.showOpenDialog(this);
        
        if(retV==JFileChooser.APPROVE_OPTION)
        {
            System.out.println("File selected: "+jfc.getSelectedFile());
            Story newStory=new Story();
            try {
                XMLTranslator t=new XMLTranslator(jfc.getSelectedFile());
                String contents=t.translate();
                newStory.loadStory(contents);
                System.out.println(contents);
                
                this.internalStory=newStory;
                updateFields();
            } catch (IOException ex) {
                System.err.println("HOOOOooOOOoOoOooooooOooooOooooOOO! "+jfc.getSelectedFile());
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(StoryFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(StoryFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else
        {
            System.out.println("File open op discarded.");
        }
    }//GEN-LAST:event_importMenuItemActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        if(!this.internalStory.nodeList.isEmpty())
        {
            //Show a confirmation dialog:
            int retV=JOptionPane.showConfirmDialog(this, "There is a story in progress. Are you sure you want to create a new story?", "New...", JOptionPane.YES_NO_OPTION);
            
            if(retV==JOptionPane.NO_OPTION)
            {
                return;
            }
        }
        
        //Proceed:
        this.internalStory=new Story();
        
        //Ask the user what to name the new story:
        
        this.lastSelectedStoryNode=null;
        this.lastSelectedStoryNodeIndex=0;
        this.nodeNameList.removeAll();
        this.nodeNameListModel.clear();
        this.nodeNameList.updateUI();
        this.codeBox.setText("");
        this.p.setImg(new BufferedImage(p.getWidth(), p.getHeight(), BufferedImage.TYPE_INT_ARGB));
        this.p.updateUI();
        updateFields();
    }//GEN-LAST:event_newButtonActionPerformed

    private ServerImportFrame f=null;
    private void serverImportItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverImportItemActionPerformed
        f=new ServerImportFrame(settings);
        
        f.registerImportCallback(new Runnable()
        {
            public void run()
            {
                if(f!=null)
                {
                    Story s=f.getImportedStory();
                    if(s!=null)
                    {
                        internalStory=s;
                        lastSelectedStoryNode=null;
                        lastSelectedStoryNodeIndex=0;
                        nodeNameList.removeAll();
                        nodeNameListModel.clear();
                        nodeNameList.updateUI();
                        codeBox.setText("");
                        p.setImg(new BufferedImage(p.getWidth(), p.getHeight(), BufferedImage.TYPE_INT_ARGB));
                        p.updateUI();
                        updateFields();
                    }
                    
                    else
                    {
                        System.err.println("Something went horribly wrong in the story callback runnable.");
                    }
                }
            }
        });
    }//GEN-LAST:event_serverImportItemActionPerformed

    private void serverExportItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverExportItemActionPerformed
        ServerExportFrame f=new ServerExportFrame(this.internalStory, settings);
    }//GEN-LAST:event_serverExportItemActionPerformed

    private void setDefaultsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setDefaultsItemActionPerformed
        SettingsFrame f=new SettingsFrame(settings);
        f.registerOkButtonCallback(new Runnable(){
            @Override
            public void run()
            {
                System.out.println("Changed settings:");
                settings.printSettings();
                
                try
                {
                    settings.saveSettings(new File(settings.settingsFilePath));
                } catch(IOException e)
                {
                    System.err.println("Could not save updated settings to path: "+settings.settingsFilePath);
                }
            }
        });
    }//GEN-LAST:event_setDefaultsItemActionPerformed

    private void createAccountItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAccountItemActionPerformed
        RegistrationForm f=new RegistrationForm(settings);
        f.setRegistrationCallback(new Runnable(){
            public void run()
            {
                try
                {
                    settings.saveSettings(settings.getSettingsFile());
                } catch(IOException e)
                {
                    System.err.println("Error: could not save settings to "+settings.settingsFilePath);
                }
            }
        });
    }//GEN-LAST:event_createAccountItemActionPerformed

    private void copyItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyItemActionPerformed
        Clipboard c=Toolkit.getDefaultToolkit().getSystemClipboard();
        String s=codeBox.getSelectedText();
        StringSelection selection=new StringSelection(s);
        
        c.setContents(selection, selection);
    }//GEN-LAST:event_copyItemActionPerformed

    private void pasteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteItemActionPerformed
        Clipboard c=Toolkit.getDefaultToolkit().getSystemClipboard();
        try
        {
            //Hopefully this won't blow up.
            String s=(String) c.getData(DataFlavor.stringFlavor);
            String codeBoxContents=codeBox.getText();
            int idx=codeBox.getCaret().getDot();
            
            String outStr=codeBoxContents.substring(0, idx);
            outStr+=s;
            outStr+=codeBoxContents.substring(idx, codeBoxContents.length());
            
            codeBox.setText(outStr);
        } catch(UnsupportedFlavorException e)
        {
            //No particular care needs to be given.
        } catch(IOException e)
        {
            //Do nothing
        }
    }//GEN-LAST:event_pasteItemActionPerformed

    private void titleItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleItemActionPerformed
        String newTitle=(String) JOptionPane.showInputDialog(this, "Enter new story title");
        
        if(newTitle!=null)
        {
            this.internalStory.title=newTitle;
        }
        
        updateFields();
    }//GEN-LAST:event_titleItemActionPerformed

    private void publishItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publishItemActionPerformed
        PublicationFrame f=new PublicationFrame(this.internalStory, this.settings);
    }//GEN-LAST:event_publishItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StoryFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StoryFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StoryFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StoryFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StoryFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutItem;
    private javax.swing.JTextPane codeBox;
    private javax.swing.JMenuItem copyItem;
    private javax.swing.JMenuItem createAccountItem;
    private javax.swing.JToggleButton deleteButton;
    private javax.swing.JPanel drawingPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exportMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem importMenuItem;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenuItem newButton;
    private javax.swing.JMenu newItem;
    private javax.swing.JButton newItemButton;
    private javax.swing.JList nodeNameList;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JMenuItem pasteItem;
    private javax.swing.JMenuItem publishItem;
    private javax.swing.JMenuItem sanityTestItem;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JMenuItem serverExportItem;
    private javax.swing.JMenuItem serverImportItem;
    private javax.swing.JMenuItem setDefaultsItem;
    private javax.swing.JMenuItem titleItem;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

}
