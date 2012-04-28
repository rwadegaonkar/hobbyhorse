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
import java.applet.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

/*

  Maintenance log started on November 20th, 2000 by Laurentiu Cristofor

  Nov. 20th, 2000   - made some style changes

  Nov. 21st, 2000   - commented label font and color setting
                    - resized and repositioned widgets
                    - added button shortcuts

*/ 

/**

  DatabaseModifyDialog.java<P>

  Change the Database user in the system.<P>

*/

public class DatabaseModifyDialog extends CenteredDialog
{
  public DatabaseModifyDialog(javax.swing.JFrame parent, boolean modal) 
    throws ClientException
  {
    super (parent, "Modify/Delete Databases", modal);
    this.parent = parent;
    Vector feedBack = Client.getDBConfig();
    String response = feedBack.elementAt(0).toString();
    if(response.equals("ERROR"))
      {
	throw new ClientException((String)feedBack.elementAt(1));
      }
    else
      {
	getContentPane().setLayout(null);
	setSize(425,300);
	setLocation(getDialogCenteredLoc(parent)); 

	btnDelete.setText("Delete");
	btnDelete.setMnemonic(KeyEvent.VK_D);
	getContentPane().add(btnDelete);
	btnDelete.setBounds(35,25,85,25);

	btnSave.setText("Save");
	btnSave.setMnemonic(KeyEvent.VK_S);
	getContentPane().add(btnSave);
	btnSave.setBounds(120,25,85,25);

	btnRefresh.setText("Refresh");
	btnRefresh.setMnemonic(KeyEvent.VK_R);
	getContentPane().add(btnRefresh);
	btnRefresh.setBounds(205,25,85,25);

	btnClose.setText("Close");
	btnClose.setMnemonic(KeyEvent.VK_C);
	getContentPane().add(btnClose);
	btnClose.setBounds(290,25,85,25);

	JLabel1.setText("Databases Available:");
	getContentPane().add(JLabel1);
	//JLabel1.setForeground(java.awt.Color.black);
	//JLabel1.setFont(new Font("MonoSpaced", Font.BOLD, 14));
	JLabel1.setBounds(35,80,130,25);

	JLabel3.setText("Group Access:");
	getContentPane().add(JLabel3);
	//JLabel3.setForeground(java.awt.Color.black);
	//JLabel3.setFont(new Font("MonoSpaced", Font.BOLD, 14));
	JLabel3.setBounds(235,80,118,25);

	DBConfig DB = (DBConfig)feedBack.elementAt(1);
	config = DB;
	String firstDB = new String();
	String firstGroup = new String();
	try
	  {
	    this.database = DB.listDatabases(Client.userName);
	    firstDB = (String)database.elementAt(0);
	  }
	catch(DBConfigException e)
	  {
	    throw new ClientException("Error getting database list: " + e);
	  }
	selDatabase = new javax.swing.JComboBox(database);
	getContentPane().add(selDatabase);
	selDatabase.setBounds(35,110,139,25);

	try
	  {
	    this.group = DB.listGroups(Client.userName);
	    firstGroup = config.getDatabaseGroup(firstDB);
	  }
	catch(DBConfigException e)
	  {
	    throw new ClientException("Error getting group list: " + e);
	  }
	selGroup = new javax.swing.JComboBox(group);
	getContentPane().add(selGroup);
	selGroup.setSelectedItem(firstGroup);
	selGroup.setBounds(235,110,139,25);

	SymAction lSymAction = new SymAction();
	btnDelete.addActionListener(lSymAction);
	btnSave.addActionListener(lSymAction);
	btnClose.addActionListener(lSymAction);
	btnRefresh.addActionListener(lSymAction);
	selDatabase.addActionListener(lSymAction);
      }
  }

  private javax.swing.JFrame parent;
  private DBConfig config;
  private Vector group = new Vector();
  private Vector database = new Vector();
  javax.swing.JLabel JLabel1 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel2 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel3 = new javax.swing.JLabel();
  javax.swing.JButton btnDelete = new javax.swing.JButton();
  javax.swing.JButton btnRefresh = new javax.swing.JButton();
  javax.swing.JButton btnSave = new javax.swing.JButton();
  javax.swing.JButton btnClose = new javax.swing.JButton();
  javax.swing.JComboBox selDatabase ;
  javax.swing.JComboBox selGroup ;

  class SymAction implements java.awt.event.ActionListener
  {
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
      Object object = event.getSource();
      if (object == btnDelete)
	btnDelete_actionPerformed(event);
      else if (object == btnSave)
	btnSave_actionPerformed(event);
      else if (object == btnClose)
	btnClose_actionPerformed(event);
      else if (object == btnRefresh)
	btnRefresh_actionPerformed(event);
      else if (object == selDatabase)
	selDatabase_actionPerformed(event);
    }
  }

  public void setDBConfig()
  {
    Vector feedBack = Client.getDBConfig();
    String response = feedBack.elementAt(0).toString();
    if(response.equals("ERROR"))
      {
	JOptionPane.showMessageDialog(this, (String)feedBack.elementAt(1));
      }
    else
      {
	DBConfig DB = (DBConfig)feedBack.elementAt(1);
	config = DB;
      }
  }

  void selDatabase_actionPerformed(java.awt.event.ActionEvent event)
  {
    try
      {
	if (selDatabase.getSelectedItem() == null)
	  return;
	//System.out.println("before geting selected DB ");
	String selectedDB = selDatabase.getSelectedItem().toString();
	//System.out.println("selected DB " + selectedDB);
	String respondGroup = config.getDatabaseGroup(selectedDB);
	selGroup.setSelectedItem(respondGroup);
      }
    catch(Exception e)
      {
	JOptionPane.showMessageDialog(this, "Error selecting database: " +e.toString());
      }
  }

  void btnDelete_actionPerformed(java.awt.event.ActionEvent event)
  {
    String dbName= selDatabase.getSelectedItem().toString();
    int  result;
    result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete database " + dbName + "?" );
    if (result == JOptionPane.NO_OPTION)
      return;
    else if (result == JOptionPane.YES_OPTION)
      {
	Vector db = new Vector();
	db.add(selDatabase.getSelectedItem().toString());
	try
	  {
	    Vector feedBack = Client.delDB(db);
	    if(feedBack.size()>0)
	      {
		String response = feedBack.elementAt(0).toString();
		if (!response.equals("OK"))
		  {
		    JOptionPane.showMessageDialog(this, feedBack.elementAt(1).toString());
		  }
		else
		  {
		    JOptionPane.showMessageDialog(this, "The database has been deleted sucessfully");

		    this.hide();
		    this.dispose();
		    try
		      {
			DatabaseModifyDialog mf = new DatabaseModifyDialog(parent, true);
			mf.show();
		      }
		    catch(ClientException e)
		      {
			JOptionPane.showMessageDialog(this, e.toString());
		      }
		  }
	      }
	  }
	catch(Exception e)
	  {
	    JOptionPane.showMessageDialog(this, e.toString());
	  }
      }
  }

  void btnSave_actionPerformed(java.awt.event.ActionEvent event)
  {
    Vector db = new Vector();
    db.add(selDatabase.getSelectedItem().toString());
    db.add(selGroup.getSelectedItem().toString());
    try
      {
	Vector feedBack = Client.modDB(db);
	if(feedBack.size()>0)
	  {
	    String response = feedBack.elementAt(0).toString();
	    if (!response.equals("OK"))
	      {
		JOptionPane.showMessageDialog(this, feedBack.elementAt(1).toString());
	      }
	    else
	      {
		setDBConfig();
		JOptionPane.showMessageDialog(this, "The database has been modified sucessfully");
	      }
	  }
      }
    catch(Exception e)
      {
	JOptionPane.showMessageDialog(this, e.toString());        
      }
  }

  void btnClose_actionPerformed(java.awt.event.ActionEvent event)
  {
    this.hide();
    this.dispose();
    parent.repaint();
  }

  void btnRefresh_actionPerformed(java.awt.event.ActionEvent event)
  {
    String firstDB = new String("");
    String firstGroup = new String("");
    try
      {
	setDBConfig();
	selDatabase.removeAllItems();
	database = config.listDatabases(Client.userName);
	//System.out.println(database.size());
	for(int i=0; i<database.size(); i++)
	  {
	    //System.out.println(database.elementAt(i));
	    selDatabase.addItem(database.elementAt(i));
	  }
	firstDB = (String)database.elementAt(0);
	selDatabase.setSelectedItem(firstDB);
	selGroup.removeAllItems();
	group = config.listGroups(Client.userName);
	for(int i=0; i<group.size(); i++)
	  selGroup.addItem(group.elementAt(i));
	firstGroup = config.getDatabaseGroup(firstDB);
	//System.out.println("firstDB " + firstDB);
	//System.out.println("first group " + firstGroup);
	selGroup.setSelectedItem(firstGroup);
      }
    catch(Exception e)
      {
	JOptionPane.showMessageDialog(this, "Error refreshing data " + e);
      } 
  }
}
