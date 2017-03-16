/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyeditor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author mgohde
 */
public class ServerExportFrame extends javax.swing.JFrame {

    private Story internalStory;
    private Settings settings;
    
    /**
     * Creates new form ServerExportFrame
     */
    public ServerExportFrame(Story s, Settings st) {
        initComponents();
        
        internalStory=s;
        settings=st;
        
        serverNameBox.setText(st.dbServer);
        databaseNameBox.setText(st.dbName);
        userNameBox.setText(st.dbUsername);
        passwordBox.setText(st.dbPassword);
        contributorName.setText(st.userName);
        
        this.setTitle("Export to server...");
        this.setVisible(true);
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
        cancelButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        serverNameBox = new javax.swing.JTextField();
        databaseNameBox = new javax.swing.JTextField();
        userNameBox = new javax.swing.JTextField();
        passwordBox = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        contributorName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Server Name:");

        jLabel2.setText("User Name:");

        jLabel3.setText("Password:");

        jLabel4.setText("Database Name:");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        exportButton.setText("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        serverNameBox.setText("dowell-nudge.colorado.edu");

        databaseNameBox.setText("nudge");

        userNameBox.setText("username");

        passwordBox.setText("password");

        jLabel5.setText("Contributor ID:");

        contributorName.setText("jLabel6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(cancelButton))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(exportButton)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(contributorName)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(databaseNameBox)
                            .addComponent(userNameBox)
                            .addComponent(serverNameBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(passwordBox))
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelButton)
                            .addComponent(exportButton))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(contributorName))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
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
            //System.out.println(this.userNameBox.getText());
            //System.out.println(this.passwordBox.getText());
            
            c=DriverManager.getConnection("jdbc:mysql://"+this.serverNameBox.getText()+"/"+this.databaseNameBox.getText(), this.userNameBox.getText(), this.passwordBox.getText());
            s=c.createStatement();
            
            //Determine if the given storyline already exists on the server:
            boolean storyExists=false;
            r=s.executeQuery("SELECT storytitle FROM tmpstorytable WHERE storytitle='"+this.internalStory.title+"'");
            
            while(r.next())
            {
                storyExists=true;
            }
            
            if(storyExists)
            {
                s.execute("DELETE FROM tmpstorytable WHERE storytitle='"+this.internalStory.title+"'");
                s.execute("DELETE FROM tmpanswers WHERE storytitle='"+this.internalStory.title+"'");
                s.execute("DELETE FROM tmpresults WHERE storytitle='"+this.internalStory.title+"'");
                s.execute("DELETE FROM tmprewardss WHERE storytitle='"+this.internalStory.title+"'");
            }
            
            //Now generate SQL statements:
            System.out.println("Generating SQL...");
            String statements=internalStory.toSQL("tmpstorytable", "tmpanswers", "tmpresults", "tmprewardss", settings.userName, settings.password);
            System.out.println(statements);
            String strings[]=statements.split("\n");
            
            for(String str:strings)
            {
                System.out.println("Query: "+str);
                s.execute(str);
            }
            
            s.close();
            c.close();
            
            JOptionPane.showMessageDialog(this, "Successfully saved story to database.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
        } catch(ClassNotFoundException e)
        {
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: Could not connect to specified MySQL server instance!", "Could not connect", JOptionPane.ERROR_MESSAGE);
            System.err.println("Got SQLException. Contents: ");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_exportButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel contributorName;
    private javax.swing.JTextField databaseNameBox;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField passwordBox;
    private javax.swing.JTextField serverNameBox;
    private javax.swing.JTextField userNameBox;
    // End of variables declaration//GEN-END:variables
}
