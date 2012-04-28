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

  Nov. 21st, 2000   - replaced a bunch of code by a call to 
                      MiningResult.fillCells
                    - commented label font and color setting
                    - added button shortcuts

*/ 

/**

  MiningDialog.java<P>

  Interface for mining input.<P>

*/

public class MiningDialog extends CenteredDialog implements FocusListener
{
  public MiningDialog(javax.swing.JFrame parent, boolean modal) 
    throws ClientException
  {
    super (parent, "Find Association Rules", modal);
    this.parent = parent;
    addWindowListener (new java.awt.event.WindowAdapter () {
	public void windowClosing (java.awt.event.WindowEvent evt) {
	  closeDialog(evt);
        }
      });

    Vector feedBack = Client.getDBConfig();
    String response = feedBack.elementAt(0).toString();
    //System.err.println(response);
    if(response.equals("ERROR"))
      {
	throw new ClientException((String)feedBack.elementAt(1));
      }
    else
      {
	config = (DBConfig)feedBack.elementAt(1);
	try
	  {
	    this.DB = config.listDatabases(Client.userName);
	    this.AG = config.listAlgorithms(Client.userName);
	  }
	catch(DBConfigException e)
	  {
	    //System.err.println(e);
	    throw new ClientException("DBException happened");
	  }
	isAdvanced = false;
	selDatabase = new javax.swing.JComboBox(DB);
	selAlgorithm = new javax.swing.JComboBox(AG);

	// This line prevents the "Swing: checked access to system event queue" message seen in some browsers.
	getRootPane().putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
	getContentPane().setLayout(null);
	//getContentPane().setBackground(new java.awt.Color(204,204,204));
	//getContentPane().setForeground(java.awt.Color.darkGray);
	getContentPane().setFont(new Font("Dialog", Font.PLAIN, 14));
	setSize(600,600);
	//setLocation(250, 50);
	setLocation(getDialogCenteredLoc(parent));

	//setTitle("Data Mining");
	btnDefault.setText("Default");
	getContentPane().add(btnDefault);
	btnDefault.setBounds(5,0,80,30);
	btnAdvanced.setText("Advanced");
	getContentPane().add(btnAdvanced);
	btnAdvanced.setBounds(95, 0, 93, 30);

	lblWarning.setText("Can't get requested data from server");
	getContentPane().add(lblWarning);
	lblWarning.setForeground(java.awt.Color.red);
	lblWarning.setFont(new Font("DialogInput", Font.BOLD, 14));
	lblWarning.setBounds(195, 0,300 ,30);
	lblWarning.setVisible(false);

	JLabel2.setText("Database:");
	getContentPane().add(JLabel2);
	//JLabel2.setForeground(java.awt.Color.darkGray);
	//JLabel2.setFont(new Font("DialogInput", Font.BOLD, 12));
	JLabel2.setBounds(320, 85,80 ,30);

	JLabel3.setText("Algorithm:");
	getContentPane().add(JLabel3);
	//JLabel3.setForeground(java.awt.Color.darkGray);
	//JLabel3.setFont(new Font("DialogInput", Font.BOLD, 12));
	JLabel3.setBounds(320,55,80,25);

	getContentPane().add(selDatabase);
	selDatabase.setBounds(410,85,130,28);
	getContentPane().add(selAlgorithm);
	selAlgorithm.setBounds(410,55,130,28);

	JLabel4.setText("Minimum Confidence:");
	getContentPane().add(JLabel4);
	//JLabel4.setForeground(java.awt.Color.darkGray);
	//JLabel4.setFont(new Font("DialogInput", Font.BOLD, 12));
	JLabel4.setBounds(10,85,144,30);

	JLabel5.setText("Minimum Support: ");
	getContentPane().add(JLabel5);
	//JLabel5.setForeground(java.awt.Color.darkGray);
	//JLabel5.setFont(new Font("DialogInput", Font.BOLD, 12));
	JLabel5.setBounds(10,55,125,25);

	txtSupport.setText("");
	getContentPane().add(txtSupport);
	txtSupport.setBounds(160,55,129,25);
	txtSupport.setNextFocusableComponent(txtConfidence);
    
	txtConfidence.setText("");
	getContentPane().add(txtConfidence);
	txtConfidence.setBounds(160,85,129,25);

	getContentPane().add(palAdvanced);
	palAdvanced.setBounds(10,115,500, 400);
	palAdvanced.setVisible(false);
	btnMining.setText("Mine");
	btnMining.setMnemonic(KeyEvent.VK_M);
	getContentPane().add(btnMining);
	btnMining.setBounds(150,530,93,25);

	btnRefresh.setText("Refresh");
	btnRefresh.setMnemonic(KeyEvent.VK_R);
	getContentPane().add(btnRefresh);
	btnRefresh.setBounds(243,530,93,25);

	btnCancel.setText("Close");
	btnCancel.setMnemonic(KeyEvent.VK_C);
	getContentPane().add(btnCancel);
	btnCancel.setBounds(336,530,93,25);

	SymAction lSymAction = new SymAction();
	    
	btnMining.addActionListener(lSymAction);
	btnCancel.addActionListener(lSymAction);
	btnDefault.addActionListener(lSymAction);
	btnAdvanced.addActionListener(lSymAction);
	btnRefresh.addActionListener(lSymAction);
	selDatabase.addActionListener(lSymAction);
      }
  }

  private Vector DB = new Vector();
  private Vector AG = new Vector();
  private boolean isAdvanced;
  private javax.swing.JFrame parent;
  private DBConfig config;

  javax.swing.JLabel lblWarning = new javax.swing.JLabel();
  javax.swing.JLabel JLabel1 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel2 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel3 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel4 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel5 = new javax.swing.JLabel();
  javax.swing.JComboBox selDatabase;
  javax.swing.JComboBox selAlgorithm ;
  MiningAdvanced palAdvanced = new MiningAdvanced(this);

  javax.swing.JTextField txtConfidence = new javax.swing.JTextField();
  javax.swing.JTextField txtSupport = new javax.swing.JTextField();
  javax.swing.JButton btnMining = new javax.swing.JButton();
  javax.swing.JButton btnCancel = new javax.swing.JButton();
  javax.swing.JButton btnRefresh = new javax.swing.JButton();
  javax.swing.JButton btnDefault = new javax.swing.JButton();
  javax.swing.JButton btnAdvanced = new javax.swing.JButton();

  class SymAction implements java.awt.event.ActionListener
  {
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
      Object object = event.getSource();
      if (object == btnMining)
	btnMining_actionPerformed(event);
      else if (object == btnCancel)
	btnCancel_actionPerformed(event);
      else if (object == btnDefault)
	btnDefault_actionPerformed(event);
      else if (object == btnAdvanced)
	btnAdvanced_actionPerformed(event);
      else if (object == selDatabase)
	selDatabase_actionPerformed(event);
      else if (object == btnRefresh)
	btnRefresh_actionPerformed(event);
    }
  }

  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) 
  {//GEN-FIRST:event_closeDialog
    setVisible (false);
    dispose ();
    parent.repaint();
  }
    
  //Function need to be rewritten from focuslistener interface
  public void focusGained(FocusEvent e) 
  {
  }

  public void focusLost(FocusEvent e) 
  {
  }
   
  public void setDBConfig()
  {
    Vector feedBack = Client.getDBConfig();
    String response = feedBack.elementAt(0).toString();
    if(response.equals("ERROR"))
      {
	JOptionPane.showMessageDialog(this, "Error in re_set DBConfig");
      }
    else
      {
	config = (DBConfig)feedBack.elementAt(1);
      }
  }
    
  void btnDefault_actionPerformed(java.awt.event.ActionEvent event)
  {
    palAdvanced.setVisible(false);
    isAdvanced = false;
  }

  void selDatabase_actionPerformed(java.awt.event.ActionEvent event)
  {
    if(isAdvanced == true)
      btnAdvanced_actionPerformed(event);
  }

  private void copy(Vector v1, Vector v2)
  {
    v2.clear();
    for(int i = 0; i < v1.size(); i++)
      v2.add(v1.get(i));
    return;
  }	
	
    
  void btnRefresh_actionPerformed(java.awt.event.ActionEvent event)
  {
    setDBConfig();
    try
      {
	//copy(config.listDatabases(Client.userName),DB);
	//copy(config.listAlgorithms(Client.userName), AG);
	DB = config.listDatabases(Client.userName);
	AG = config.listAlgorithms(Client.userName);
	selDatabase.removeAllItems();
	for(int i=0; i<DB.size(); i++)
	  selDatabase.addItem(DB.elementAt(i));
	selAlgorithm.removeAllItems();
	for(int i=0; i<AG.size(); i++)
	  selAlgorithm.addItem(AG.elementAt(i));
	isAdvanced = false;
	palAdvanced.setVisible(false);
      }
    catch(Exception e)
      {
	JOptionPane.showMessageDialog(this, "Error in refresh:" + e.toString());
      }
  }

  void btnAdvanced_actionPerformed(java.awt.event.ActionEvent event)
  {
    try
      {
	String DBName = selDatabase.getSelectedItem().toString();
	Vector db = new Vector();
	db.add(DBName);
	Vector feedBack = Client.getColumns(db);
	if(feedBack.size()>0)
	  {
	    String response = feedBack.elementAt(0).toString();
	    if (!response.equals("OK"))
	      {
		JOptionPane.showMessageDialog(this, feedBack.elementAt(0).toString());
	      }
	    else
	      {
		Vector DBColumns = (Vector)feedBack.elementAt(1);
		palAdvanced.setList(DBColumns);
		palAdvanced.setVisible(true);
		isAdvanced = true;
	      }
	  }
      }
    catch(Exception e)
      {
	//System.err.println(e);
	JOptionPane.showMessageDialog(this, "Exception in getting response from server" + e.toString());     
      }
  }

  boolean isEmpty(String value)
  {
    if(value.equals(new String("")))
      {
	return true;
      }
    else
      return false;
  }

  void btnMining_actionPerformed(java.awt.event.ActionEvent event)
  {
    String action;
    String database;
    String algorithm;
    Vector miningInput = new Vector();
    Float minSupp;
    Float minConf;

    database = selDatabase.getSelectedItem().toString();
    algorithm = selAlgorithm.getSelectedItem().toString();

    if(isEmpty(txtSupport.getText().toString()) 
       || isEmpty(txtConfidence.getText().toString()))
      {
	JOptionPane.showMessageDialog(this, "Minimum support and mimum confidence can not be empty");     
      }
    else
      {
	try
	  {
	    minSupp = Float.valueOf(txtSupport.getText());
	    minConf = Float.valueOf(txtConfidence.getText());
	    if(minSupp.floatValue()<0 || minSupp.floatValue()>1 
	       || minConf.floatValue()<0 || minConf.floatValue()>1){
	      if(minSupp.floatValue()<0 || minSupp.floatValue()>1)
		txtSupport.setText("");
	      else
		txtConfidence.setText("");
	      JOptionPane.showMessageDialog(this, "Minimum support and mimum confidence must be values between 0 and 1");   
	      return;
	    }
	    else
	      {
		minSupp = Float.valueOf(txtSupport.getText());
		minConf = Float.valueOf(txtConfidence.getText());
		miningInput.add(database);
		miningInput.add(algorithm);
		miningInput.add(minSupp);
		miningInput.add(minConf);
	      }
	  }
	catch(Exception f)
	  {
	    JOptionPane.showMessageDialog(this, f.toString()); 
	    return;     
	  }
	if(isAdvanced == false)
	  {
	    action = new String("MINE");
	  }
	else
	  {
	    action = new String("MINEADV");
	    miningInput.add(palAdvanced.getAnt());
	    miningInput.add(palAdvanced.getCon());
	    miningInput.add(palAdvanced.getIgn());
	    try
	      {
		miningInput.add(palAdvanced.getMaxAnt());
		miningInput.add(palAdvanced.getMinCon());
	      }
	    catch(ClientException e)
	      {
		miningInput.add(Integer.valueOf("0"));
		miningInput.add(Integer.valueOf("0"));
		JOptionPane.showMessageDialog(this, e.toString());         
	      }
	  }

	//System.out.println(action);
	try
	  {
	    Vector db = new Vector();
	    db.add(database);
	    Vector feedBack = Client.mine(action, miningInput);
	    Vector column = Client.getColumns(db);
        
	    if(feedBack.size()>0 && column.size()>0)
	      {
		String response = feedBack.elementAt(0).toString();
		String response1 = column.elementAt(0).toString();
		if (!(response.equals("OK") 
		      && response1.equals("OK")))
		  {
		    JOptionPane.showMessageDialog(this, feedBack.elementAt(1).toString());            
		  }
		else
		  {
		    Vector result = (Vector)feedBack.elementAt(1);
		    Vector names = (Vector)column.elementAt(1);
		    Vector cells = new Vector();

		    MiningResult.fillCells(result, names, cells);
		    
		    Vector columnNames = new Vector();
		    columnNames.add("Antecedent");
		    columnNames.add("Consequent");
		    columnNames.add("Support");
		    columnNames.add("Confidence");

		    MiningResult miningResult = 
		      new MiningResult(cells, columnNames, names, this);

		    miningResult.show();
		  }
	      }
	  }
	catch(Exception e)
	  {
	    JOptionPane.showMessageDialog(this, e.toString());      
	  }
      }
  }

  void btnCancel_actionPerformed(java.awt.event.ActionEvent event)
  {
    this.hide();
    this.dispose();
    parent.repaint();
  }
}
