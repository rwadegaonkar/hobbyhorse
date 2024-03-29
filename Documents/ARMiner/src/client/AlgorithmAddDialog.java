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

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.net.*;
import java.util.*;

/*

  Maintenance log started on November 20th, 2000 by Laurentiu Cristofor

  Nov. 20th, 2000   - made some style changes

  Nov. 21st, 2000   - resized and repositioned widgets

*/ 

/**
 * AlgorithmAddDialog.java<P>
 * 
 * Create a dialog to allow the user to add a new algorithm.
 * 
 */

public class AlgorithmAddDialog extends CenteredDialog 
{
  /** Initializes the Form */
  public AlgorithmAddDialog (javax.swing.JFrame parent, 
			    boolean modal, Vector groups) 
  {
    super (parent, "Add Algorithms", modal);
    this.parent = parent;
    setSize(360,290);
    this.groups = groups;

    initComponents ();
    setLocation(getDialogCenteredLoc(parent));
    setResizable(false);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () 
  {//GEN-BEGIN:initComponents
    //setBackground (new java.awt.Color (204, 204, 204));
    addWindowListener (new java.awt.event.WindowAdapter () {
        public void windowClosing (java.awt.event.WindowEvent evt) {
          closeDialog (evt);
        }
      });
    getContentPane ().setLayout (new com.netbeans.developer.awt.AbsoluteLayout ());
    getContentPane ().add (new javax.swing.JLabel("Algorithm Name:"), new com.netbeans.developer.awt.AbsoluteConstraints (10, 20, 110, 20));

    jtxfAlgName = new javax.swing.JTextField ();
    getContentPane ().add (jtxfAlgName, new com.netbeans.developer.awt.AbsoluteConstraints (10, 50, 195, 25));

    getContentPane ().add (new javax.swing.JLabel("Group Access:"), new com.netbeans.developer.awt.AbsoluteConstraints (210, 20, 100, 20));
    
    jcbGroupAccess = new javax.swing.JComboBox (groups);
    getContentPane ().add (jcbGroupAccess, new com.netbeans.developer.awt.AbsoluteConstraints (210, 50, 120, 25));
    jcbGroupAccess.setMaximumRowCount(6);
    getContentPane ().add(new javax.swing.JLabel ("Source File Location (*.jar):"), new com.netbeans.developer.awt.AbsoluteConstraints (10, 100, 200, 20));

    jtxfAlgFileName = new javax.swing.JTextField ();
    getContentPane ().add (jtxfAlgFileName, new com.netbeans.developer.awt.AbsoluteConstraints (10, 130, 195, 25));
    
    jbtnBrowse = new javax.swing.JButton("Browse...");
    jbtnBrowse.setMnemonic(KeyEvent.VK_B);
    ActionListener startFileChooser = new ActionListener(){
	public void actionPerformed(ActionEvent e){
	  JFileChooser chooser = new JFileChooser();
	  ExampleFileFilter filter = 
	    new ExampleFileFilter(new String[] {"jar"}, "jar Files");
	  
	  chooser.addChoosableFileFilter(filter);
	  chooser.setFileFilter(filter);
	  
	  int retval = chooser.showOpenDialog(parent);
	  File theFile;
	  if(retval == 0) 
	    {
	      theFile = chooser.getSelectedFile();
	      if (!theFile.exists())
		{
		  JOptionPane.showMessageDialog(parent, "Can not find " +
						chooser.getSelectedFile().getAbsolutePath());
		  return;
		}
	      else 
		{
		  chooser.setCurrentDirectory(theFile);
		  chooser.setSelectedFile(theFile);
		  jtxfAlgFileName.setText(chooser.getSelectedFile().getAbsolutePath());
		  return;
		}
	    } 
	  JOptionPane.showMessageDialog(parent, "No file chosen");
	}
      };
    jbtnBrowse.addActionListener(startFileChooser);
    
    getContentPane ().add (jbtnBrowse, new com.netbeans.developer.awt.AbsoluteConstraints (210, 130, 120, 25));
    
    jbtnAdd = new javax.swing.JButton ("Add");
    jbtnAdd.setMnemonic(KeyEvent.VK_A);
    jbtnAdd.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jcbAddAlgActionPerformed (evt);
        }
      });
    getContentPane ().add (jbtnAdd, new com.netbeans.developer.awt.AbsoluteConstraints (35, 190, 85, 25));
    jbtnRefresh = new javax.swing.JButton ("Refresh");
    jbtnRefresh.setMnemonic(KeyEvent.VK_R);
    jbtnRefresh.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jcbRefreshActionPerformed (evt);
        }
      });
    getContentPane ().add (jbtnRefresh, new com.netbeans.developer.awt.AbsoluteConstraints (133, 190, 85, 25));
    
    jbtnClose = new javax.swing.JButton ("Close");
    jbtnClose.setMnemonic(KeyEvent.VK_C);
    jbtnClose.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jbtnCloseActionPerformed (evt);
        }
      });
    getContentPane ().add (jbtnClose, new com.netbeans.developer.awt.AbsoluteConstraints (231, 190, 85, 25));
    
  }//GEN-END:initComponents
  
  //performed when the user click Close button
  private void jbtnCloseActionPerformed (java.awt.event.ActionEvent evt) 
  {//GEN-FIRST:event_jbtnCloseActionPerformed
    // Add your handling code here:
    setVisible (false);
    dispose ();
    parent.repaint();
  }//GEN-LAST:event_jbtnCloseActionPerformed
  
  
  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) 
  {//GEN-FIRST:event_closeDialog
    setVisible (false);
    dispose ();
    parent.repaint();
  }//GEN-LAST:event_closeDialog
  
  private void refresh()
  {
    try
      {
	Client.getDBConfig1();
      }
    catch(ClientErrorException e)
      {
	JOptionPane.showMessageDialog(parent, "AlgorithmAdd: Client Error in getting DBConfig:\n " + e.toString() + "\nTransaction aborted!");
	return;
      }

    try
      {
	copy(Client.dbConfig.listGroups(Client.userName),groups);
	refGroups = (Vector)groups.clone();
      }
    catch(DBConfigException e)
      {
	JOptionPane.showMessageDialog(this, "AlgorithmAdd: Client Error in getting groups from DBConfig:\n " + e.toString() + "\nTransaction aborted!");
	return;
      }
  }
  
  private void copy(Vector v1, Vector v2)
  {
    v2.clear();
    for(int i = 0; i < v1.size(); i++)
      v2.add(v1.get(i));
    return;
  }

  private void jcbAddAlgActionPerformed (java.awt.event.ActionEvent evt) 
  {//GEN-FIRST:event_AlgorithmAddDialog
    if(jtxfAlgName.getText().trim().equals(""))
      {
	JOptionPane.showMessageDialog(this, "Must specify the algorithm's name");
	jtxfAlgName.requestFocus();
	return;
      }
    if(jtxfAlgFileName.getText().trim().equals(""))
      {
	JOptionPane.showMessageDialog(this, "Must specify an algorithm file name");
	jtxfAlgFileName.requestFocus();
	return;
      }
    
    File algFile = new File(jtxfAlgFileName.getText().trim());
    if(!algFile.exists())
      {
	JOptionPane.showMessageDialog(this, "Can not find file " 
				      + jtxfAlgFileName.getText());
	jtxfAlgFileName.selectAll();
	jtxfAlgFileName.requestFocus();
	return;
      }
    
    if(jcbGroupAccess.getSelectedIndex() < 0)
      {
	jcbGroupAccess.setSelectedIndex(0);
      }
    
    int dim;
    byte[] buf;
    try
      {
	BufferedInputStream in = 
	  new BufferedInputStream(new FileInputStream(algFile));
	dim = in.available();
	//System.out.println("dim = " + dim);
	if(dim > MAX_ALGORITHM_SIZE){
	  JOptionPane.showMessageDialog(this, "The size of this algorithm file is over " + MAX_ALGORITHM_SIZE/1000 + "K. Transaction aborted!");
	
	  in.close();
	  return;
	}

	buf = new byte[dim];
      
	int no_bytes;
	int bytes_read = 0;
	while (bytes_read != dim)	
	  {
	    int r = in.read(buf, bytes_read, dim - bytes_read);
	    if (r < 0)  
	      break;
	    bytes_read += r;
	  }
	if (bytes_read != dim)
	  throw new IOException("Cannot read entry");     		  

	//System.out.println("bytes read: " + bytes_read);
	in.close();
      }
    catch (IOException e)
      {
	JOptionPane.showMessageDialog(this, "AlgorithmAdd: IO Error in reading file:\n " + e.toString() + "\nTransaction aborted!");
	return;
      }

    Vector input = new Vector();
    input.add(jtxfAlgName.getText().trim());
    input.add(jcbGroupAccess.getSelectedItem());
    input.add(new Integer(dim));

    try
      {
	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	Client.addAlgorithms(input,buf);
	this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	JOptionPane.showMessageDialog(this, "The algorithm: " 
				      + jtxfAlgName.getText() 
				      + " has been successfully added.");
	jtxfAlgFileName.setText("");
	jtxfAlgName.setText("");
      }
    catch(ClientErrorException e)
      {
	JOptionPane.showMessageDialog(this, "Client Error in reading file: " 
				      + e.toString() 
				      + " Transaction aborted!");
	return;
      }
  }//GEN-LAST:event_AlgorithmAddDialog
          
  private void jcbRefreshActionPerformed (java.awt.event.ActionEvent evt) 
  {//GEN-FIRST:event_RefreshAcitonPerformed
    refresh();
    jcbGroupAccess.removeAllItems();

    for(int i = 0; i < refGroups.size(); i++)
      jcbGroupAccess.addItem(refGroups.get(i));

  }//GEN-LAST:event_RefreshActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField jtxfAlgName;
  private javax.swing.JComboBox jcbGroupAccess;
  private javax.swing.JTextField jtxfAlgFileName;
  private javax.swing.JButton jbtnBrowse;
  private javax.swing.JButton jbtnAdd;
  private javax.swing.JButton jbtnRefresh;
  private javax.swing.JButton jbtnClose;
  private javax.swing.JFrame parent;
  private String filename;
  private String algname;
  Vector groups = new Vector();
  Vector refGroups = new Vector();
  public static final long MAX_ALGORITHM_SIZE  = 100000;
  // End of variables declaration//GEN-END:variables
}


