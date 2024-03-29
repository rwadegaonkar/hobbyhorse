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

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

/*

  Maintenance log started on November 16th, 2000 by Laurentiu Cristofor

  Nov. 16th, 2000   - made some style changes
                    - changed a label from "Algorithms Selected" to
                      "Selected Algorithms"

  Nov. 17th, 2000   - changed the text for more labels/error messages
                    - renamed some methods
                    - introduced dialogs for errors instead of 
                      System.out calls

  Nov. 21st, 2000   - resized and repositioned widgets
                    - added button shortcuts

*/ 

/**
 * BenchmarkDialog.java<P>
 * 
 * Create a dialog to allow the user to benchmark algorithms.
 * 
 */

public class BenchmarkDialog extends CenteredDialog 
{
  /** Initializes the Form */
  public BenchmarkDialog(JFrame parent)throws ClientException
  {
    setSize(500,400);
    this.parent = parent;
    currentlistvalue = new Vector();
    currentlistalg = new Vector();
    dbvector = new Vector(); 
    algvector = new Vector();
    Vector feedBack = Client.getDBConfig();
    String response = feedBack.elementAt(0).toString();
    //System.err.println(response);

    if(response.equals(new String("ERROR")))
      {
	//System.err.println("Error in getting DBConfig");
	throw new ClientException("Error in getting DBConfig"); 
      }
    else
      {
	DBConfig DBC = (DBConfig)feedBack.elementAt(1);
	try
	  {
	    this.DB = DBC.listDatabases(Client.userName); 
	    this.AG = DBC.listAlgorithms(Client.userName);
	  }
	catch(DBConfigException e)
	  {
	    //System.err.println(e);
	    throw new ClientException("DBException happened");
	  }              
      }
	    
    currentlistvalue = DB; 
    currentlistalg = AG;
    setTitle("Benchmarking"); 
    initComponents ();
    setLocation(getDialogCenteredLoc(parent));
  }
  

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () 
  {
    //setBackground (new java.awt.Color (204, 204, 204));
    addWindowListener (new java.awt.event.WindowAdapter () 
      {
	public void windowClosing (java.awt.event.WindowEvent evt) 
	{
	  closeDialog (evt);
	}
      }
		       );
    getContentPane().setLayout(new com.netbeans.developer.awt.AbsoluteLayout ());
    jbtnClose = new javax.swing.JButton ("Close");
    jbtnClose.setMnemonic(KeyEvent.VK_C);
    jbtnClose.addActionListener (new java.awt.event.ActionListener () 
      {
	public void actionPerformed (java.awt.event.ActionEvent evt) 
	{
	  jbtnCloseActionPerformed() ;
	}
      });
    jbtnClose.addKeyListener(new KeyAdapter() 
      {
	public void keyPressed(KeyEvent evt) 
	{  
	  if (evt.getKeyCode() == KeyEvent.VK_ENTER)
	    { 
	      jbtnCloseActionPerformed();
	    }
	}
      });

    jbtnClose.requestFocus();
    getContentPane ().add (jbtnClose, new com.netbeans.developer.awt.AbsoluteConstraints (410, 35, 70, 25));
    jbtnStart = new javax.swing.JButton ("Start");
    jbtnStart.setMnemonic(KeyEvent.VK_S);
    jbtnStart.addActionListener (new java.awt.event.ActionListener () 
      {
	public void actionPerformed (java.awt.event.ActionEvent evt) 
	{  
	  boolean flag = true;
	  if((dbvector.size()==0) ||
	     (algvector.size()==0))
	    {
	      JOptionPane.showMessageDialog(null,
					    "You should select at least one database and one algorithm!",
					    "Error",
					    JOptionPane.ERROR_MESSAGE );
	      flag = false;
	    }
                                 
	  if(flag)
	    if ((getSupport().get(0)).equals("GOODFORMAT") &&
		!jtxrangesupport.getText().equals(""))
	      startBenchmarking_actionPerformed(); 
	    else
	      JOptionPane.showMessageDialog(null,
					    "Please enter support values between 0.0 and 1.0, separated by commas!",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
	}
      });
    
    getContentPane ().add (jbtnStart, new com.netbeans.developer.awt.AbsoluteConstraints (310, 35, 70, 25));  
    getContentPane().add (new javax.swing.JLabel("Support Values:"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 10, 100, 20));
    jtxrangesupport = new javax.swing.JTextField ();
    getContentPane ().add (jtxrangesupport, new com.netbeans.developer.awt.AbsoluteConstraints (20, 35, 170, 25)); 
    getContentPane ().add (new javax.swing.JLabel("Databases:"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 90, 100, 20));
    
    Databases = new javax.swing.JList (DB);
    Databases.setBorder (new javax.swing.border.EtchedBorder ());
    scrollPane1 = new JScrollPane(Databases);
    scrollPane1.setAlignmentX(LEFT_ALIGNMENT);
    scrollPane1.setAlignmentY(TOP_ALIGNMENT);
    getContentPane().add (scrollPane1, new com.netbeans.developer.awt.AbsoluteConstraints (20, 120, 170, 100));
    
    jbadddb = new javax.swing.JButton (last);
    jbadddb.addActionListener (new java.awt.event.ActionListener () 
      {
	public void actionPerformed (java.awt.event.ActionEvent evt) 
	{
	  addDatabase_actionPerformed();
	}
      });
    getContentPane ().add (jbadddb, new com.netbeans.developer.awt.AbsoluteConstraints (220, 130, 43, 30));
    
    jbremovedb = new javax.swing.JButton (first);
    jbremovedb.addActionListener (new java.awt.event.ActionListener () 
      {
	public void actionPerformed (java.awt.event.ActionEvent evt) 
	{
	  removeDatabase_actionPerformed();
	}
      });
    getContentPane ().add (jbremovedb, new com.netbeans.developer.awt.AbsoluteConstraints (220, 170, 43, 30));
   
    getContentPane ().add (new javax.swing.JLabel("Selected Databases:"), new com.netbeans.developer.awt.AbsoluteConstraints (310, 90, 130, 20));
   
    dblist= new javax.swing.JList();
    dblist.setBorder (new javax.swing.border.EtchedBorder ());
    scrollPane2 = new JScrollPane(dblist);
    scrollPane2.setAlignmentX(LEFT_ALIGNMENT);
    scrollPane2.setAlignmentY(TOP_ALIGNMENT);
    getContentPane ().add (scrollPane2, new com.netbeans.developer.awt.AbsoluteConstraints (310, 120, 170, 100));
    
    getContentPane ().add (new javax.swing.JLabel("Algorithms:"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 140, 140, 200));
    getContentPane ().add (new javax.swing.JLabel("Selected Algorithms:"), new com.netbeans.developer.awt.AbsoluteConstraints (310, 140, 160, 200));

    Algorithms = new javax.swing.JList (AG);
    Algorithms.setBorder (new javax.swing.border.EtchedBorder ());
    scrollPane11 = new JScrollPane(Algorithms);
    scrollPane11.setAlignmentX(LEFT_ALIGNMENT);
    scrollPane11.setAlignmentY(TOP_ALIGNMENT);
    
    getContentPane().add (scrollPane11, new com.netbeans.developer.awt.AbsoluteConstraints (20, 260, 170, 100));
    
    alglist = new javax.swing.JList();
    alglist.setBorder (new javax.swing.border.EtchedBorder ());
    scrollPane22 = new JScrollPane(alglist);
    scrollPane22.setAlignmentX(LEFT_ALIGNMENT);
    scrollPane22.setAlignmentY(TOP_ALIGNMENT);
    getContentPane ().add (scrollPane22, new com.netbeans.developer.awt.AbsoluteConstraints (310, 260, 170, 100));
    
    jbaddalg = new javax.swing.JButton (last); 
    jbaddalg.addActionListener (new java.awt.event.ActionListener () 
      {
	public void actionPerformed (java.awt.event.ActionEvent evt) 
	{
	  addAlgorithm_actionPerformed();
	}
      });   
    getContentPane ().add (jbaddalg, new com.netbeans.developer.awt.AbsoluteConstraints (220, 270, 43, 30));

    jbremovealg = new javax.swing.JButton (first);
    jbremovealg.addActionListener (new java.awt.event.ActionListener () 
      {
	public void actionPerformed (java.awt.event.ActionEvent evt) 
	{
	  removeAlgorithm_actionPerformed();
	}
      }
				   );
    getContentPane ().add (jbremovealg, new com.netbeans.developer.awt.AbsoluteConstraints (220, 310, 43, 30));
    setModal(true);   
  }//GEN-END:initComponents
  
  public void setList(Vector newValue)
  {
    currentlistvalue = newValue;
    Vector newData = new Vector();
    this.Databases.setListData(newValue);
  }                    
      
  public Vector getDBvector()
  {
    return dbvector;
  }

  public Vector getALGvector()
  {
    return algvector;
  }

  public Vector getSupport()
  {
    Vector supp;
    Vector statesupport;
    String token;
    float fVal;
    String sValue = jtxrangesupport.getText();
    StringTokenizer st = new StringTokenizer( sValue , ", " );
    boolean AllInRange = true;
    supp = new Vector();
    statesupport = new Vector();
    while (st.hasMoreTokens())
      {
	token = st.nextToken();
	try
	  {
	    fVal = (new Float(token)).floatValue();
	    if (fVal < 0 || fVal > 1.0 )
	      AllInRange = false;
	    supp.add(new Float(token.trim()));    
	  }
	catch(NumberFormatException e)
	  {
	    AllInRange = false;
	  }
      }

    // from over here we know if all in range by the boolean var
    if (AllInRange)
      {
	statesupport.add("GOODFORMAT");
	Collections.sort(supp);
	statesupport.add(supp);
      }
    else
      statesupport.add("BADFORMAT"); 
    return statesupport;
  }

  private void startBenchmarking_actionPerformed()
  {  
    Vector benchInput = new Vector();
    Vector result = new Vector();
    try 
      {
	benchInput.add(this.getDBvector());
	benchInput.add(this.getALGvector());
	benchInput.add((Vector)this.getSupport().get(1));
	Vector feedBack = Client.benchmark(benchInput);
	result.add(feedBack.elementAt(1));
	BenchmarkResult chart = 
	  new BenchmarkResult((Vector)result.elementAt(0));
	chart.show();
      }
    catch(ClientErrorException f)
      {
	JOptionPane.showMessageDialog(null,
				      "An error happened: " + f,
				      "Error",
				      JOptionPane.ERROR_MESSAGE );
	//System.out.println(f);
      }
  } 
  
  void addDatabase_actionPerformed()
  {
    try
      {
	Vector selected = new Vector();
	int [] selectedIndex = Databases.getSelectedIndices(); 
	for(int i=0; i<selectedIndex.length; i++){
	  selected.add((String)Databases.getModel().getElementAt(selectedIndex[i]));
	}     
	currentlistvalue.removeAll(selected);
	Databases.setListData(currentlistvalue);
	dbvector.addAll(selected);
	dblist.setListData(dbvector);
      }
    catch(Exception f)
      {
	JOptionPane.showMessageDialog(null,
				      "An error happened: " + f,
				      "Error",
				      JOptionPane.ERROR_MESSAGE );
	//System.out.println("Error in setting list data: "+ f );
      }

  }        
  public void removeDatabase_actionPerformed()
  {
    Vector selected = new Vector();
    try 
      {
	int [] selectedIndex = dblist.getSelectedIndices();
	for(int i=0; i<selectedIndex.length; i++){
	  selected.add((String)dblist.getModel().getElementAt(
							      selectedIndex[i]));
	}       
	currentlistvalue.addAll(selected);
	dbvector.removeAll(selected);
	Databases.setListData(currentlistvalue);
	dblist.setListData(dbvector);
      }
    catch(Exception f)
      {
	JOptionPane.showMessageDialog(null,
				      "An error happened: " + f,
				      "Error",
				      JOptionPane.ERROR_MESSAGE );
	//System.out.println("Error: "+ e );
      }   
  }                               
     
  public void  addAlgorithm_actionPerformed() 
  {
    
    Vector selected = new Vector();
    int [] selectedIndex = Algorithms.getSelectedIndices(); 
    for(int i=0; i<selectedIndex.length; i++){
      selected.add((String)Algorithms.getModel().getElementAt(selectedIndex[i]));
    }     
    try
      {
	currentlistalg.removeAll(selected);
	Algorithms.setListData(currentlistalg);
	algvector.addAll(selected);
	alglist.setListData(algvector);
      }
    catch(Exception f)
      {
	JOptionPane.showMessageDialog(null,
				      "An error happened: " + f,
				      "Error",
				      JOptionPane.ERROR_MESSAGE );
	//System.out.println("Error : "+ f );
      }
  }  
  
  public void removeAlgorithm_actionPerformed()
  {  
    Vector selected = new Vector();
    try 
      {
	int [] selectedIndex = alglist.getSelectedIndices();
	for(int i=0; i<selectedIndex.length; i++){
	  selected.add((String)alglist.getModel().getElementAt(
							       selectedIndex[i]));
	}       
	currentlistalg.addAll(selected);
	algvector.removeAll(selected);
	Algorithms.setListData(currentlistalg);
	alglist.setListData(algvector);
      }
    catch(Exception f)
      {
	JOptionPane.showMessageDialog(null,
				      "An error happened: " + f,
				      "Error",
				      JOptionPane.ERROR_MESSAGE );
	//System.out.println("Error : "+ ee );
      }
  }
  
  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) 
  {
    setVisible (false);
    dispose ();
    parent.repaint();
  }

  private void jbtnCloseActionPerformed() 
  {
    hide();
    dispose();
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private Vector dbvector;
  private Vector algvector;
  private Vector currentlistvalue;
  private Vector  currentlistalg;
  private Vector DB = new Vector();
  private Vector AG = new Vector();
  private javax.swing.JButton jbtnStart;
  private javax.swing.JButton jbtnClose;
  private javax.swing.JTextField jtxrangesupport;
  private javax.swing.JList Databases;
  private javax.swing.JButton jbadddb;
  private javax.swing.JButton jbremovedb;
  private javax.swing.JButton jbaddalg;
  private javax.swing.JButton jbremovealg;
  private javax.swing.JScrollPane scrollPane1;
  private javax.swing.JScrollPane scrollPane11;
  private javax.swing.JScrollPane scrollPane2;
  private javax.swing.JScrollPane scrollPane22;
  private javax.swing.JList Algorithms;
  private javax.swing.JList alglist;
  private javax.swing.JList dblist;
  private javax.swing.JFrame parent;
  public Icon first = new ImageIcon("first.gif");
  public Icon last = new ImageIcon("last.gif");
  // End of variables declaration//GEN-END:variables
}
