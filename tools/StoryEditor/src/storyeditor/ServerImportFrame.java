/*
 * TODO: modify this so that it can query the server, get a list of stories, and
 * display this list for the user to chooose which story they want.
 */
package storyeditor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.WindowConstants;

/**
 *
 * @author mgohde
 */
public class ServerImportFrame extends javax.swing.JFrame 
{
    private Story callerStory;
    private Story newStory;
    /**
     * Creates new form ServerImportFrame
     */
    public ServerImportFrame(Story s) {
        initComponents();
        callerStory=s;
        newStory=null;
        
        //Make the import button invisible until the user queries the database for stories.
        importButton.setVisible(false);
        storyNameLabel.setVisible(false);
        storyListBox.setVisible(false);
        
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    public Story getImportedStory()
    {
        return newStory;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        serverNameBox = new javax.swing.JTextField();
        databaseNameBox = new javax.swing.JTextField();
        userNameBox = new javax.swing.JTextField();
        passwordBox = new javax.swing.JPasswordField();
        queryButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        storyListBox = new javax.swing.JComboBox();
        storyNameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Server Name:");

        jLabel2.setText("User Name:");

        jLabel3.setText("Password:");

        jLabel4.setText("Database Name:");

        serverNameBox.setText("dowell-nudge.colorado.edu");

        databaseNameBox.setText("nudge");

        userNameBox.setText("someuser");

        passwordBox.setText("somepassword");

        queryButton.setText("Query");
        queryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queryButtonActionPerformed(evt);
            }
        });

        importButton.setText("Import");

        storyListBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        storyListBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storyListBoxActionPerformed(evt);
            }
        });

        storyNameLabel.setText("Story Name:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(queryButton)
                    .addComponent(storyNameLabel))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(importButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordBox)
                            .addComponent(databaseNameBox)
                            .addComponent(userNameBox)
                            .addComponent(serverNameBox, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(storyListBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(serverNameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(databaseNameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(userNameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(storyListBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(storyNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(queryButton)
                    .addComponent(importButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void queryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queryButtonActionPerformed
        Connection c;
        Statement s;
        PreparedStatement p;
        ResultSet r;
        
        ArrayList<String> storyNameList=new ArrayList<>();
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Attempting to query URL:");
            System.out.println("jdbc:mysql://"+this.serverNameBox.getText()+"/"+this.databaseNameBox.getText());
            System.out.println(this.userNameBox.getText());
            System.out.println(this.passwordBox.getText());
            c=DriverManager.getConnection("jdbc:mysql://"+this.serverNameBox.getText()+"/"+this.databaseNameBox.getText(), this.userNameBox.getText(), this.passwordBox.getText());
            s=c.createStatement();
            
            //Get all story names:
            r=s.executeQuery("SELECT DISTINCT storytitle FROM storytable");
            
            //The next() method moves us to the next record.
            while(r.next())
            {
                storyNameList.add(r.getString(1));
            }
            
            r.close();
            s.close();
            c.close();
            
            String storyNames[]=new String[storyNameList.size()];
            storyNames=storyNameList.toArray(storyNames);
            
            storyListBox.setModel(new DefaultComboBoxModel(storyNames));
            storyListBox.setVisible(true);
            storyNameLabel.setVisible(true);
            importButton.setVisible(true);
        } catch(ClassNotFoundException e)
        {
            
        } catch (SQLException ex) {
            System.err.println("Got SQLException. Contents: ");
            ex.printStackTrace();
        }
        
    }//GEN-LAST:event_queryButtonActionPerformed

    private void storyListBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storyListBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_storyListBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField databaseNameBox;
    private javax.swing.JButton importButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField passwordBox;
    private javax.swing.JButton queryButton;
    private javax.swing.JTextField serverNameBox;
    private javax.swing.JComboBox storyListBox;
    private javax.swing.JLabel storyNameLabel;
    private javax.swing.JTextField userNameBox;
    // End of variables declaration//GEN-END:variables
}