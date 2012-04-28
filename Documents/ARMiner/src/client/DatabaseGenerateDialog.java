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
                    - changed text for some labels

  Nov. 21st, 2000   - added button shortcuts

*/ 

/**
 * DatabaseGenerateDialog.java<P>
 * 
 * Create a dialog to allow the user to generate a new synthetic database.
 * 
 */

public class DatabaseGenerateDialog extends CenteredDialog
{
  private Vector vector; 
  Vector group = new Vector();
  JTextField dbname; 
  JTextField num_trans;    
  JTextField avg_trans_size;  
  JTextField num_max_large_itemsets;  
  JTextField avg_large_itemset_size;
  JTextField num_items; 
  JTextField correlation_mean;   
  JTextField  corruption_mean;  
  JComboBox selGroup;
  JFrame parent;         

  public DatabaseGenerateDialog(JFrame parent)    throws ClientException
  {
    Vector feedBack = Client.getDBConfig(); 		
    String response = feedBack.elementAt(0).toString(); 
    addWindowListener (new java.awt.event.WindowAdapter () {
	public void windowClosing (java.awt.event.WindowEvent evt) {
	  closeDialog (evt);
	}
      });

    if(response.equals("ERROR"))
      {
	throw new ClientException("Error in getting DBConfig"); 	
      } 		 		
    else
      {
	setSize(400,500); 
	this.parent = parent;
	setLocation(getDialogCenteredLoc(parent));
	getRootPane().putClientProperty("defeatSystemEventQueueCheck",
					Boolean.TRUE); 		
	DBConfig DB = (DBConfig)feedBack.elementAt(1); 			
	try
	  { 				
	    this.group = DB.listGroups(Client.userName);
	  }
	catch(DBConfigException e)
	  {
	    throw new ClientException("Error: " + e);
	  }
	selGroup = new javax.swing.JComboBox(group);
	getContentPane().setLayout(new GridLayout(10,2,30,20));
	//getContentPane().setBackground(new java.awt.Color(204,204,204));
	//getContentPane().setForeground(java.awt.Color.darkGray);
	//getContentPane().setFont(new Font("Dialog", Font.PLAIN, 14));
	setTitle("Generate Synthetic Database");
	JButton jbstart = new JButton("Generate");
	jbstart.setMnemonic(KeyEvent.VK_G);
	JButton jbcancel = new JButton("Close");
	jbcancel.setMnemonic(KeyEvent.VK_C);
	JLabel  dbgroup = new JLabel("Group access:");
	JLabel  ldbname = new JLabel("Database name:");
	dbname = new JTextField();
	JLabel  lnumtrans = new JLabel("Number of transactions:");
	num_trans = new JTextField();
	JLabel  avtrans = new JLabel("Average transaction size:");
	avg_trans_size = new JTextField();
	JLabel  nummaxlargeitemsets = new JLabel("Number of patterns:");
	num_max_large_itemsets = new JTextField();
	JLabel  numitems = new JLabel("Number of items:");
	num_items = new JTextField();
	JLabel  corelation = new JLabel("Correlation mean:");
	correlation_mean = new JTextField("0.5");
	JLabel  correption = new JLabel("Corruption mean:");
	corruption_mean = new JTextField("0.5");
	JLabel avglargeitemsetsize = new JLabel("Average pattern size:");
	avg_large_itemset_size = new JTextField();
    
	getContentPane().add(dbgroup);
	getContentPane().add(selGroup);
	getContentPane().add(ldbname);     
	getContentPane().add(dbname); 
	getContentPane().add(lnumtrans); 
	getContentPane().add(num_trans);
	getContentPane().add(avtrans);
	getContentPane().add(avg_trans_size); 
	getContentPane().add(nummaxlargeitemsets); 
	getContentPane().add(num_max_large_itemsets);
    
	getContentPane().add(avglargeitemsetsize); 
	getContentPane().add(avg_large_itemset_size);
  
	getContentPane().add(numitems); 
	getContentPane().add(num_items); 
	getContentPane().add(corelation); 
	getContentPane().add(correlation_mean); 
	getContentPane().add(correption); 
	getContentPane().add(corruption_mean);
	getContentPane().add(jbstart);
	getContentPane().add(jbcancel); 
    
	jbstart.addActionListener(new ActionListener()
	  {
	    public void actionPerformed(ActionEvent e) 
	    {
	      boolean flag = true;
     
	      if(dbname.getText().equals(""))             
		{     
		  JOptionPane.showMessageDialog(null, "Please enter the database name!", "Error" ,JOptionPane.ERROR_MESSAGE ); 	
		  dbname.requestFocus();  
		  flag = false;
		}     
	      if(flag)
		if((dbname.getText().equals(""))
		   || (num_trans.getText().equals("")) 
		   || (avg_trans_size.getText().equals("")) 
		   || (num_max_large_itemsets.getText().equals("")) 
		   || (avg_large_itemset_size.getText().equals("")) 
		   || (num_items.getText().equals("")))             
		  {     
		    JOptionPane.showMessageDialog(null ,"Please complete all fields!", "Error" ,JOptionPane.ERROR_MESSAGE ); 	
		    dbname.requestFocus();  
		    flag = false;
		  }     

	      if( (correlation_mean.getText().equals(""))
		  || (Double.parseDouble(correlation_mean.getText()) < 0)
		  || (Double.parseDouble(correlation_mean.getText()) > 1))
		{     
		  JOptionPane.showMessageDialog(null ,   "Enter a correlation value between 0 and 1" , "Error" ,JOptionPane.ERROR_MESSAGE ); 	
		  correlation_mean.requestFocus();  
		  flag = false;
		}     

	      if( (corruption_mean.getText().trim().length()==0)
		  || (Double.parseDouble(corruption_mean.getText()) < 0)
		  || (Double.parseDouble(corruption_mean.getText()) > 1))  
		{
		  JOptionPane.showMessageDialog(null ,"Enter a corruption value between 0 and 1" , "Error" ,JOptionPane.ERROR_MESSAGE ); 	
		  corruption_mean.requestFocus();       
		  flag = false;
		}
              
	      if (flag == true) 	  
		StartSynth_actionPerformed(); 
	    }
	  });

	jbcancel.addActionListener(new ActionListener()
	  {
	    public void actionPerformed(ActionEvent e) 
	    {  
	      jbtnCloseActionPerformed ();
	    }
	  });
     
	setModal(true);
      }
  }

  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) 
  {
    setVisible (false);
    dispose ();
    parent.repaint();
  }

  /** Closes the dialog */
  private void jbtnCloseActionPerformed() 
  {
    setVisible (false);
    dispose ();
    parent.repaint();
  }
 
  void  StartSynth_actionPerformed() 
  {
    Vector vsynthetic = new Vector();
    
    try 
      {
	String group = selGroup.getSelectedItem().toString();
	vsynthetic.add(dbname.getText());
	vsynthetic.add(group);
	vsynthetic.add(new Long(num_trans.getText().trim()));
	vsynthetic.add(new Integer( avg_trans_size.getText().trim()));
	vsynthetic.add(new Integer(num_max_large_itemsets.getText().trim()));
	vsynthetic.add(new Integer(avg_large_itemset_size.getText().trim())); 
	vsynthetic.add(new Integer(num_items.getText().trim())); 
	vsynthetic.add(new Double(correlation_mean.getText())); 
	vsynthetic.add(new Double(corruption_mean.getText()));
        
	Vector  feedBack = Client.fsynthetic(vsynthetic);
	if (feedBack.elementAt(0).equals("OK"))
	  JOptionPane.showMessageDialog(null ,"The synthetic database was generated successfully!" , "" ,JOptionPane.INFORMATION_MESSAGE ); 
	else if (feedBack.elementAt(0).equals("ERROR"))
	  {
	    String st = (String)(((Vector)feedBack.elementAt(1)).elementAt(0));
	    JOptionPane.showMessageDialog(null ,"The synthetic database was not generated: " + st , "Error", JOptionPane.ERROR_MESSAGE );
	  }
      }
    catch(ClientErrorException f)
      {
	JOptionPane.showMessageDialog(null, "An error occurred: " + f , "Error", JOptionPane.ERROR_MESSAGE );
	//System.out.println(f);
	//System.out.println("ERROR " + f);
      }
    catch (NumberFormatException e){}          
    //System.out.println(vsynthetic);
  } 
} 
