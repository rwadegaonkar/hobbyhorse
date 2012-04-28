/*

ARMiner - Association Rules Miner
Copyright (C) 2000  UMass/Boston - Computer Science Department


This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
USA


The ARMiner Server was written by Dana Cristofor and Laurentiu
Cristofor.

The ARMiner Client was written by Abdelmajid Karatihy, Xiaoyong Kuang,
and Lung-Tsung Li.


The ARMiner package is currently maintained by Laurentiu Cristofor
(laur@cs.umb.edu).

*/

/*

   This file is a part of the ARMiner project.

   (P)1999-2000 by ARMiner Client Team:

   Abdelmajid Karatihy
   Xiaoyong Kuang
   Lung-Tsung Li

*/

import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

/*

  Maintenance log started on November 20th, 2000 by Laurentiu Cristofor

  Nov. 20th, 2000   - made some style changes

  Nov. 21st, 2000   - commented label font and color setting
                    - resized and repositioned widgets
                    - added button shortcuts

*/ 

/**

  DatabaseAddDialog.java<P>

  DatabaseAddDialog is a used to add a new database table to the system.<P>

*/

public class DatabaseAddDialog extends CenteredDialog
{
  public DatabaseAddDialog(javax.swing.JFrame parent, boolean modal) 
    throws ClientException
  {
    super (parent, "Add Databases", modal);
    this.parent = parent;
		    
    Vector feedBack = Client.getDBConfig();
    String response = feedBack.elementAt(0).toString();
    if(response.equals("ERROR"))
      {
	throw new ClientException((String)feedBack.elementAt(1));
      }
    else
      {
	parentD = this;
	// This line prevents the "Swing: checked access to system event queue" message seen in some browsers.
	getRootPane().putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);

	getContentPane().setLayout(null);
	setSize(448,250);
	setLocation(getDialogCenteredLoc(parent)); 

	JLabel2.setText("Database Name: ");
	getContentPane().add(JLabel2);
	//JLabel2.setForeground(java.awt.Color.black);
	//JLabel2.setFont(new Font("MonoSpaced", Font.BOLD, 14));
	JLabel2.setBounds(48,14,120,28);

	JLabel3.setText("Group Access: ");
	getContentPane().add(JLabel3);
	//JLabel3.setForeground(java.awt.Color.black);
	//JLabel3.setFont(new Font("MonoSpaced", Font.BOLD, 14));
	JLabel3.setBounds(255,14,120,28);

	JLabel4.setText("Source file location: ");
	getContentPane().add(JLabel4);
	//JLabel4.setForeground(java.awt.Color.black);
	//JLabel4.setFont(new Font("MonoSpaced", Font.BOLD, 14));
	JLabel4.setBounds(48,86,180,28);

	getContentPane().add(txtDBName);
	txtDBName.setBounds(48,38,202,24);

	DBConfig DB = (DBConfig)feedBack.elementAt(1);
	try
	  {
	    this.group = DB.listGroups(Client.userName);
	  }
	catch(DBConfigException e)
	  {
	    throw new ClientException(e.toString());
	  }
	selGroup = new javax.swing.JComboBox(group);
	getContentPane().add(selGroup);
	selGroup.setBounds(255,38,120,25);

	btnOpen.setText("Browse...");
	btnOpen.setMnemonic(KeyEvent.VK_B);
	getContentPane().add(btnOpen);
	btnOpen.setBounds(255,115,120,25);

	getContentPane().add(txtFileName);
	txtFileName.setBounds(48,115,202,25);

	btnAdd.setText("Add");
	btnAdd.setMnemonic(KeyEvent.VK_A);
	getContentPane().add(btnAdd);
	btnAdd.setBounds(90,162,75,25);

	btnRefresh.setText("Refresh");
	btnRefresh.setMnemonic(KeyEvent.VK_R);
	getContentPane().add(btnRefresh);
	btnRefresh.setBounds(165,162,85,25);

	btnCancel.setText("Close");
	btnCancel.setMnemonic(KeyEvent.VK_C);
	getContentPane().add(btnCancel);
	btnCancel.setBounds(250,162,75,25);

	btnOpen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent ae){
	      chooser = new JFileChooser();
	      int option = chooser.showOpenDialog(parentD);
	      if (option == JFileChooser.APPROVE_OPTION) {
		File dir = chooser.getCurrentDirectory();
		String file = chooser.getSelectedFile().getName();
		sourceFile = new File(dir, file);
		txtFileName.setText(sourceFile.getAbsolutePath().toString());
	      }
	    }
	  });

	SymAction lSymAction = new SymAction();
	btnAdd.addActionListener(lSymAction);
	btnCancel.addActionListener(lSymAction);
	btnRefresh.addActionListener(lSymAction);
      }
  }

  private File sourceFile;
  private javax.swing.JFrame parent;
  private JDialog parentD;
  private Vector group = new Vector();
  private DBConfig config;
  JFileChooser chooser;
  javax.swing.JLabel JLabel1 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel2 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel3 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel4 = new javax.swing.JLabel();
  javax.swing.JTextField txtDBName = new javax.swing.JTextField();
  javax.swing.JComboBox selGroup;
  javax.swing.JButton btnOpen = new javax.swing.JButton();
  javax.swing.JTextField txtFileName = new javax.swing.JTextField();
  javax.swing.JButton btnAdd = new javax.swing.JButton();
  javax.swing.JButton btnRefresh = new javax.swing.JButton();
  javax.swing.JButton btnCancel = new javax.swing.JButton();
   
  class SymAction implements java.awt.event.ActionListener
  {
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
      Object object = event.getSource();
      if (object == btnAdd)
	btnAdd_actionPerformed(event);
      else if (object == btnCancel)
	btnCancel_actionPerformed(event);
      else if (object == btnRefresh)
	btnRefresh_actionPerformed(event);
    }
  }
    
  public void setDBConfig()
  {
    Vector feedBack = Client.getDBConfig();
    String response = feedBack.elementAt(0).toString();
    if(response.equals("ERROR"))
      {
	JOptionPane.showMessageDialog(this, feedBack.elementAt(1));
      }
    else
      {
	config = (DBConfig)feedBack.elementAt(1);
      }
  }
    
  public void btnAdd_actionPerformed(java.awt.event.ActionEvent event)
  {			
    try
      {
	String fileSource = txtFileName.getText().toString();
	DBReader inFile = new DBReader(fileSource);
	String DBName = txtDBName.getText().toString(); 
	String group = selGroup.getSelectedItem().toString();
	Vector feedBack = Client.addDB(inFile, DBName, group);
	//System.out.println("here" + feedBack);
	if(feedBack.size()>0)
	  {
	    String response = feedBack.elementAt(0).toString();
	    if (!response.equals("OK"))
	      {
		JOptionPane.showMessageDialog(this, feedBack.elementAt(1).toString());
	      }
	    else
	      {
		//System.out.println("here");
		JOptionPane.showMessageDialog(this, "The database has been added successfully");
	      }
	  }
      }
    catch(Exception e)
      {
	JOptionPane.showMessageDialog(this, e.toString());
      }
  }
  
  public void btnRefresh_actionPerformed(java.awt.event.ActionEvent event)
  {		
    setDBConfig();
	
    try
      {
	group = config.listGroups(Client.userName);
	selGroup.removeAllItems();
	for(int i=0; i<group.size(); i++)
	  {
	    //System.out.println(group.elementAt(i));
	    selGroup.addItem(group.elementAt(i));
	  }
	//copy(config.listGroups(Client.userName),group);
	String firstGroup = (String)group.elementAt(0);
	selGroup.setSelectedItem(firstGroup);
	txtFileName.setText("");
	txtDBName.setText("");
      }
    catch(Exception e)
      {
	JOptionPane.showMessageDialog(this, e.toString());
      }	
  }
	
  public void btnCancel_actionPerformed(java.awt.event.ActionEvent event)
  {
    this.hide();
    this.dispose();
    parent.repaint();				 
  }	    	
}
