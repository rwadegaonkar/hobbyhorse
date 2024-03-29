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
import java.util.*;
import java.awt.event.*;

/*

  Maintenance log started on November 20th, 2000 by Laurentiu Cristofor

  Nov. 20th, 2000   - made some style changes
                    - changed text for some labels

  Nov. 21st, 2000   - resized and repositioned widgets

  Nov. 22nd, 2000   - eliminated the password confirmation check
                      on lost focus, it didn't work correctly
                    - eliminated an error that made it impossible to 
                      change the password

*/ 

/**
 * UserManagerDialog.java<P>
 * 
 * Create a dialog to allow the user to be able to add a new user, change an
 * existing user's name, password, previledges and his associated group. 
 * 
 */

public class UserManagerDialog extends CenteredDialog 
  implements FocusListener
{
  /** Initializes the Form */
  public UserManagerDialog(javax.swing.JFrame parent, boolean modal, 
			   Vector users, Vector groups) 
  {
    super (parent, "User Management", modal);    
    this.parent = parent;
    setSize(480,380);
    this.users = users;
    users.insertElementAt("New User", 0);
    usergroup.add("all");
    defGroup.add("all");
    this.groups = groups;
    setNonUserGroups();
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
    jbtnClose = new javax.swing.JButton ("Close");
    jbtnClose.setMnemonic(KeyEvent.VK_C);
    jbtnClose.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jbtnCloseActionPerformed ();
        }
      });
    jbtnClose.addKeyListener(new KeyAdapter() {
	public void keyPressed(KeyEvent evt) {  
	  if (evt.getKeyCode() == KeyEvent.VK_ENTER){ 
	    jbtnCloseActionPerformed();
	  }
	}
      });
    getContentPane ().add (jbtnClose, new com.netbeans.developer.awt.AbsoluteConstraints (390, 30, 75, 25));
    jbtnClose.requestFocus();
    getContentPane ().add (new javax.swing.JLabel("User List:"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 10, 90, 20));
    jcbUserList = new javax.swing.JComboBox (users);
    jcbUserList.setMaximumRowCount(6);
    jcbUserList.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jcbUserListActionPerformed (evt);
        }
      });
    getContentPane ().add (jcbUserList, new com.netbeans.developer.awt.AbsoluteConstraints (20, 30, 120, 25));
    
    getContentPane ().add (new javax.swing.JLabel("User Name:"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 70, 90, 20));

    jtxfUserName = new javax.swing.JTextField ();
    getContentPane ().add (jtxfUserName, new com.netbeans.developer.awt.AbsoluteConstraints (100, 70, 90, 25));
    
    getContentPane ().add (new javax.swing.JLabel("Password:"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 110, 70, 20));
    jpfPassword = new javax.swing.JPasswordField ();
    getContentPane ().add (jpfPassword, new com.netbeans.developer.awt.AbsoluteConstraints (100, 110, 90, 25));
    getContentPane ().add (new javax.swing.JLabel("Confirm"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 148, 80, 20));
    getContentPane ().add (new javax.swing.JLabel("Password:"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 165, 80, 20));
    jpfPasswordConfrm = new javax.swing.JPasswordField ();
    jpfPasswordConfrm.addFocusListener(this);
    getContentPane ().add (jpfPasswordConfrm, new com.netbeans.developer.awt.AbsoluteConstraints (100, 150, 90, 25));
    
    getContentPane ().add (new javax.swing.JLabel ("User Permissions:"), new com.netbeans.developer.awt.AbsoluteConstraints (205, 65, 120, 20));

    jchbAddAlg = new javax.swing.JCheckBox ("Add Algorithms");
    jchbAddAlg.setMnemonic(KeyEvent.VK_L);
    jchbAddAlg.addItemListener(new java.awt.event.ItemListener () {
	public void itemStateChanged (java.awt.event.ItemEvent evt) {
	  jchbAddAlgActionPerformed (evt);
        }
      });
    getContentPane ().add (jchbAddAlg, new com.netbeans.developer.awt.AbsoluteConstraints (330, 65, 120, 20));
    
    jchbAddDB = new javax.swing.JCheckBox ("Add Databases");
    jchbAddDB.setMnemonic(KeyEvent.VK_B);
    jchbAddDB.addItemListener(new java.awt.event.ItemListener () {
	public void itemStateChanged (java.awt.event.ItemEvent evt) {
	  jchbAddDBActionPerformed (evt);
        }
      });
    getContentPane ().add (jchbAddDB, new com.netbeans.developer.awt.AbsoluteConstraints (330, 92, 110, 20));
    
    jchbAddGroup = new javax.swing.JCheckBox ("Add Groups");
    jchbAddGroup.setMnemonic(KeyEvent.VK_G);
    jchbAddGroup.addItemListener(new java.awt.event.ItemListener () {
    	public void itemStateChanged (java.awt.event.ItemEvent evt) {
	  jchbAddGroupActionPerformed (evt);
	}
      });
    
    getContentPane ().add (jchbAddGroup, new com.netbeans.developer.awt.AbsoluteConstraints (330, 119, 110, 20));
    
    getContentPane ().add (new javax.swing.JLabel("Not User Groups:"), new com.netbeans.developer.awt.AbsoluteConstraints (20, 200, 140, 20));
    jlstGroupList = new javax.swing.JList (nonUsergroups);
    jlstGroupList.setBorder (new javax.swing.border.EtchedBorder ());
    scrollPane1 = new JScrollPane(jlstGroupList);
    scrollPane1.setAlignmentX(LEFT_ALIGNMENT);
    scrollPane1.setAlignmentY(TOP_ALIGNMENT);
    getContentPane ().add (scrollPane1, new com.netbeans.developer.awt.AbsoluteConstraints (20, 220, 160, 120));
    
    jbtnForward = new javax.swing.JButton (last);
    jbtnForward.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jbtnForwardActionPerformed (evt);
	}
      });
    
    getContentPane ().add (jbtnForward, new com.netbeans.developer.awt.AbsoluteConstraints (210, 240, 43, 30));
    jbtnBackward = new javax.swing.JButton (first);
    jbtnBackward.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jbtnBackwardActionPerformed (evt);
        }
      });
    
    getContentPane ().add (jbtnBackward, new com.netbeans.developer.awt.AbsoluteConstraints (210, 290, 43, 30));
    
    getContentPane().add (new javax.swing.JLabel("User Groups:"), new com.netbeans.developer.awt.AbsoluteConstraints (290, 200, 90, 20));
    jlstUserGroup = new javax.swing.JList (usergroup);
    jlstUserGroup.setBorder (new javax.swing.border.EtchedBorder ());
    scrollPane2 = new JScrollPane(jlstUserGroup);
    scrollPane2.setAlignmentX(LEFT_ALIGNMENT);
    scrollPane2.setAlignmentY(TOP_ALIGNMENT);
    getContentPane ().add (scrollPane2, new com.netbeans.developer.awt.AbsoluteConstraints (290, 220, 160, 120));
    
    jbtnMakeDefault = new javax.swing.JButton ("Make Default");
    jbtnMakeDefault.setMnemonic(KeyEvent.VK_M);
    jbtnMakeDefault.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
	  jbtnMakeDefaultActionPerformed (evt);
        }
      });
    getContentPane ().add (jbtnMakeDefault, new com.netbeans.developer.awt.AbsoluteConstraints (330, 150, 130, 25));
    
    jbtnAdd = new javax.swing.JButton ("Add");
    jbtnAdd.setMnemonic(KeyEvent.VK_A);
    jbtnAdd.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jbtnAddActionPerformed (evt);
        }
      });
    
    getContentPane ().add (jbtnAdd, new com.netbeans.developer.awt.AbsoluteConstraints (150, 30, 75, 25));
    
    jbtnDelete = new javax.swing.JButton ("Delete");
    jbtnDelete.setMnemonic(KeyEvent.VK_D);
    jbtnDelete.addActionListener (new java.awt.event.ActionListener () {
	public void actionPerformed (java.awt.event.ActionEvent evt) {
	  jbtnDeleteActionPerformed (evt);
        }
      });
    
    getContentPane ().add (jbtnDelete, new com.netbeans.developer.awt.AbsoluteConstraints (230, 30, 75, 25));
    
    jbtnSave = new javax.swing.JButton ("Save");
    jbtnSave.setMnemonic(KeyEvent.VK_S);
    jbtnSave.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          jbtnSaveActionPerformed (evt);
        }
      });
    
    getContentPane ().add (jbtnSave, new com.netbeans.developer.awt.AbsoluteConstraints (310, 30, 75, 25));
    jbtnSave.setEnabled(false);
    
    jbtnRefresh = new javax.swing.JButton ("Refresh");
    jbtnRefresh.setMnemonic(KeyEvent.VK_R);
    jbtnRefresh.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
	  jbtnRefreshActionPerformed (evt);
        }
      });
    getContentPane ().add (jbtnRefresh, new com.netbeans.developer.awt.AbsoluteConstraints (230, 150, 90, 25));

    jbtnClose.setNextFocusableComponent(jcbUserList);
    jcbUserList.setNextFocusableComponent(jtxfUserName);
    jtxfUserName.setNextFocusableComponent(jpfPassword);
    jpfPassword.setNextFocusableComponent(jpfPasswordConfrm);
    jpfPasswordConfrm.setNextFocusableComponent(jchbAddAlg);
    jchbAddAlg.setNextFocusableComponent(jchbAddDB);
    jchbAddDB.setNextFocusableComponent(jchbAddGroup);
    jchbAddGroup.setNextFocusableComponent(jlstGroupList);
    jchbAddGroup.setNextFocusableComponent(jbtnMakeDefault);
    jbtnMakeDefault.setNextFocusableComponent(jlstGroupList);
    jlstGroupList.setNextFocusableComponent(jbtnForward);
    jbtnForward.setNextFocusableComponent(jbtnBackward);
    jbtnBackward.setNextFocusableComponent(jlstUserGroup);
    jlstUserGroup.setNextFocusableComponent(jbtnAdd);
    jbtnAdd.setNextFocusableComponent(jbtnDelete);
    jbtnDelete.setNextFocusableComponent(jbtnSave);
    jbtnSave.setNextFocusableComponent(jbtnRefresh);
    jbtnRefresh.setNextFocusableComponent(jbtnClose);
    
  }//GEN-END:initComponents
  
  private void jbtnCloseActionPerformed () 
  {//GEN-FIRST:event_jbtnCloseActionPerformed
    // Add your handling code here:
    setVisible (false);
    dispose ();
    parent.repaint();
  }//GEN-LAST:event_jbtnCloseActionPerformed
  
  private void jcbUserListActionPerformed (java.awt.event.ActionEvent evt) 
  {//GEN-FIRST:event_jcbUserListActionPerformed
    // Add your handling code here:
    if (isReload == true)
      return;
    reinitialize();
    if (jcbUserList.getSelectedIndex() == 0)
      {
	setupForNewUser();
	return; 
      }
    refresh();
    repaint();
    jbtnAdd.setEnabled(false);
    jbtnSave.setEnabled(true);
    
    selectedUser = (String)jcbUserList.getSelectedItem();
    if (!users.contains(selectedUser))
      {
	JOptionPane.showMessageDialog(this, "The selected user: " + selectedUser + " is no longer in the system.");
	reinitialize();
	jcbUserList.setSelectedIndex(0);
	setupForNewUser();
	return;
      }

    resetUserInfo(selectedUser);
    jtxfUserName.setText(userInfo.userName);
    setUserPermissions(userInfo.permission);
    copy(userInfo.userGroups, usergroup);
    jlstUserGroup.setListData(usergroup);
    setNonUserGroups();
    jlstGroupList.setListData(nonUsergroups);
    isNewUser = false;
  }//GEN-LAST:event_jcbUserListActionPerformed
  
  private void setupForNewUser()
  {
    isNewUser = true;
    selectedUser = null;
    jbtnAdd.setEnabled(true);
    jbtnSave.setEnabled(false);
    jtxfUserName.requestFocus();
  }
  
  private void resetUserInfo(String user)
  {
    if (user == null)
      return;
    userInfo = new UserInfo(user);
    //System.out.println("User Info from dbConfig: " + userInfo.toString());
  }
  
  private void setUserPermissions(long permission)
  {    
    if ((DBConfig.ADD_NEW_GROUPS & permission) == DBConfig.ADD_NEW_GROUPS)
      jchbAddGroup.setSelected(true);
    else
      jchbAddGroup.setSelected(false);
    if ((DBConfig.ADD_NEW_ALGORITHMS & permission) == DBConfig.ADD_NEW_ALGORITHMS)
      jchbAddAlg.setSelected(true);
    else
      jchbAddAlg.setSelected(false);
    if ((DBConfig.ADD_NEW_DATABASES & permission) == DBConfig.ADD_NEW_DATABASES)
      jchbAddDB.setSelected(true);
    else
      jchbAddDB.setSelected(false);
  }
  
  private void jbtnAddActionPerformed (java.awt.event.ActionEvent evt)
  {
    if (isNewUser == false)
      return;
    Vector info = new Vector();
    String pass = new String();
    String user = jtxfUserName.getText().trim();
    
    if (user.equals(""))
      {
	JOptionPane.showMessageDialog(this, "Must specify the user's name");
	jtxfUserName.requestFocus();
	return;
      }
    info.add(user);

    pass = new String(jpfPassword.getPassword()).trim();
    if (pass.equals(""))
      {
	JOptionPane.showMessageDialog(this, "Must specify the user's password");
	jpfPassword.requestFocus();
	return;
      }
    if (!checkPasswordConfirmation())
      {
	JOptionPane.showMessageDialog(this, "Passwords do not match.");
	jpfPassword.setText("");
	jpfPasswordConfrm.setText("");
	jpfPassword.requestFocus();
	return;
      }
    info.add(pass);

    info.add(new Long(getPermission()));

    if (usergroup.isEmpty())
      {
	JOptionPane.showMessageDialog(this, "Must select at least one group for the user.");
	jbtnForward.requestFocus();
	return;
      }
    info.add(usergroup);

    try
      {
	Client.addUser(info);
	JOptionPane.showMessageDialog(this, "The new user: " + user + " has been successfully added.");
	jcbUserList.setSelectedIndex(0);

	jcbUserList.addItem(user);
	//System.out.println("users now = " + users.toString());
	reinitialize();
      }
    catch(ClientErrorException e)
      {
	JOptionPane.showMessageDialog(this, "UserManagerDialog: Client Error in adding an user:\n " + e.toString() + "\nTransaction aborted!");
	reinitialize();
	return;
      }
    catch(ClientWarningException e)
      {
	JOptionPane.showMessageDialog(this, "UserManagerDialog: Client Warning in adding a User:\n " + e.toString());

      }
    //isConfirmed = false;
    return;
  }

  private void reinitialize()
  {
    jlstUserGroup.clearSelection();
    jchbAddAlg.setSelected(defAddAlg);
    jchbAddDB.setSelected(defAddDB);
    jchbAddGroup.setSelected(defAddGrp);
    jtxfUserName.setText("");
    jpfPassword.setText("");
    jpfPasswordConfrm.setText("");
    usergroup.clear();
    jlstUserGroup.setListData(defGroup);
    usergroup = (Vector)defGroup.clone();
    setNonUserGroups();
    jlstGroupList.setListData(nonUsergroups);
    return;
  }
  
  private void jbtnBackwardActionPerformed (java.awt.event.ActionEvent evt)
  {
    if (jlstUserGroup.isSelectionEmpty())
      return;
    Object[] values = jlstUserGroup.getSelectedValues();
    for (int i = 0; i < values.length; i++)
      {
	if (usergroup.contains(values[i]))
	  if (((String)values[i]).equals("all"))
	    {
	      JOptionPane.showMessageDialog(this, "Can not remove group all as default group for each user.");
	      return;
	    }
	usergroup.remove(values[i]);
      }
    jlstUserGroup.setListData(usergroup);

    for (int i = 0; i < values.length; i++)
      {
	if (!nonUsergroups.contains(values[i]))
	  nonUsergroups.add(values[i]);
      }
    jlstGroupList.setListData(nonUsergroups);

    return;
  }
  
  public void focusGained(FocusEvent e) 
  {
  }

  public void focusLost(FocusEvent e) 
  {
    /*
    String pass = new String(jpfPassword.getPassword()).trim();
    String conf = new String(jpfPasswordConfrm.getPassword()).trim();

    if (pass.equals(""))
      return;
    	
    if (conf.equals(""))
      {
	JOptionPane.showMessageDialog(this, "Must confirm the user's password");
	return;
      }
    else if (!conf.equals(pass)) 
      {
	JOptionPane.showMessageDialog(this, "Passwords do not match.");
	jpfPassword.setText("");
	jpfPasswordConfrm.setText("");
	jpfPassword.requestFocus();
	return;
      }
    else
      isConfirmed = true;
    */
  }	

  private boolean checkPasswordConfirmation()
  {
    String pass = new String(jpfPassword.getPassword()).trim();
    String conf = new String(jpfPasswordConfrm.getPassword()).trim();

    if (pass.equals(""))
      return false;
    	
    if (!conf.equals(pass)) 
      return false;

    return true;
  }
  
  private void jbtnSaveActionPerformed (java.awt.event.ActionEvent evt)
  {
    Vector info = new Vector();
    boolean isChanged = false;

    String user = jtxfUserName.getText().trim();
    if (!user.equals(userInfo.userName))
      {
	info.add(userInfo.userName);
	info.add(user);
	try
	  {
	    Client.modifyUserName(info);
	    JOptionPane.showMessageDialog(this, "The user " + userInfo.userName + " has been succesfully changed to " + user);
	    refresh();
	    selectedUser = user;
	    jcbUserList.setSelectedItem(selectedUser);
	    isChanged = true;
	  }
	catch(ClientErrorException e)
	  {
	    JOptionPane.showMessageDialog(this, "UserManagerDialog: Client Error in modifying user's name:\n " + e.toString() + "\nTransaction aborted!");
	    jtxfUserName.selectAll();
	    jtxfUserName.requestFocus();
	    return;
	  }
      }

    long perm = getPermission();
    //System.out.println("Permission = " + perm);
    //System.out.println("Old permission = " + userInfo.permission);
    info.clear();    
    if (perm != userInfo.permission)
      {
	info.add(user);
	info.add(new Long(perm));
	try
	  {
	    Client.modifyUserPrivlg(info);
	    JOptionPane.showMessageDialog(this, "The user permissions have been successfully modified.");	  
	    //repaint();
	    isChanged = true;
	    refresh();
	    resetUserInfo(selectedUser);
	  }
	catch(ClientErrorException e)
	  {
	    JOptionPane.showMessageDialog(this, "UserManagerDialog: Client Error in modifying user's permissions:\n " + e.toString() + "\nTransaction aborted!");
	    return;
	  }
      }
    
    String pass = new String(jpfPassword.getPassword()).trim();
    info.clear();    
    if (!pass.equals("") && !pass.equals(userInfo.password))
      {
	if (!checkPasswordConfirmation())
	  {
	    JOptionPane.showMessageDialog(this, "Passwords do not match.");
	    jpfPassword.setText("");
	    jpfPasswordConfrm.setText("");
	    jpfPassword.requestFocus();
	    return;
	  }

	info.add(user);
	info.add(pass);
	try
	  {
	    Client.modifyUserPassword(info);
	    JOptionPane.showMessageDialog(this, "The user password has been successfully modified.");	  
	    //repaint();
	    isChanged = true;
	    refresh();
	    //repaint();
	  }
	catch(ClientErrorException e)
	  {
	    JOptionPane.showMessageDialog(this, "UserManagerDialog: Client Error in modifying user's password:\n " + e.toString() + "\nTransaction aborted!");
	    return;
	  }		
      }

    info.clear();    
    if (!usergroup.equals(userInfo.userGroups))
      {
	info.add(user);
	info.add(usergroup);
	try
	  {
	    Client.setGroupsForUser(info);
	    JOptionPane.showMessageDialog(this, "The user's groups have been successfully modified.");	  //repaint();
	    isChanged = true;
	    refresh();
	    resetUserInfo(selectedUser);
	  }
	catch(ClientErrorException e)
	  {
	    JOptionPane.showMessageDialog(this, "UserManagerDialog: Client Error in resetting user's groups:\n " + e.toString() + "\nTransaction aborted!");
	    return;
	  }
	catch(ClientWarningException e)
	  {
	    JOptionPane.showMessageDialog(this, "UserManagerDialog: Client Warning in resetting user's groups:\n " + e.toString());
	  }
      }

    if (isChanged == false)
      JOptionPane.showMessageDialog(this, "User information doesn't get changed, no transaction performed!");
    return;
  }

  private void jbtnForwardActionPerformed (java.awt.event.ActionEvent evt)
  {
    if (jlstGroupList.isSelectionEmpty())
      return;
    Object[] values = jlstGroupList.getSelectedValues();
    for (int i = 0; i < values.length; i++)
      {
	nonUsergroups.remove(values[i]);
      }
    for (int i = 0; i < values.length; i++)
      {
	if (!usergroup.contains(values[i]))
	  usergroup.add(values[i]);
      }
    jlstUserGroup.setListData(usergroup);
    jlstGroupList.setListData(nonUsergroups);
    return;
  }
  
  private void jbtnDeleteActionPerformed (java.awt.event.ActionEvent evt)
  {
    int index = jcbUserList.getSelectedIndex();
    if (index <= 0)
      {
	JOptionPane.showMessageDialog(this, "Must select a user to delete.");
	return;
      }
    String user = (String)jcbUserList.getSelectedItem();
    int  result;
    result = JOptionPane.showConfirmDialog(this, "You are about to delete the user " + user + ". Are you sure you want to do that?" );
    if (result == JOptionPane.NO_OPTION)
      return;
    else if (result == JOptionPane.YES_OPTION)
      {
	Vector request = new Vector();
	request.add(user);
	try
	  {
	    //System.out.println("The user deleted " + request.toString());
	    Client.deleteUser(request);
	    JOptionPane.showMessageDialog(this, "The user: " + user + " has been successfully deleted.");
	  }
	catch(ClientErrorException e)
	  {
	    JOptionPane.showMessageDialog(this, "UserManager: Client Error in deleting user from DBConfig:\n " + e.toString() + "\nTransaction aborted!");
	    return;
	  }
	catch(Exception e)
	  {
	    JOptionPane.showMessageDialog(this, "UserManager: Client Error in deleting user from DBConfig:\n " + e.toString() + "\nTransaction aborted!");
	    return;
	  }
	users.remove(user);
      
	jcbUserList.setSelectedIndex(0);
	reinitialize();
	repaint();
      }
  }
  
  private long getPermission()
  {
    return addNewAlg | addNewDB | addNewGroup ;
  }

  private void jchbAddAlgActionPerformed (java.awt.event.ItemEvent evt) 
  {//GEN-FIRST:event_jchbAddAlgActionPerformed
    if (evt.getStateChange() == ItemEvent.SELECTED)
      addNewAlg = DBConfig.ADD_NEW_ALGORITHMS; 
    if (evt.getStateChange() == ItemEvent.DESELECTED)
      addNewAlg = 0; 
  }//GEN-LAST:event_jchbAddAlgActionPerformed
  
  private void jchbAddDBActionPerformed (java.awt.event.ItemEvent evt) 
  {//GEN-FIRST:event_jchbAddDBActionPerformed
    if (evt.getStateChange() == ItemEvent.SELECTED)
      addNewDB = DBConfig.ADD_NEW_DATABASES;
    if (evt.getStateChange() == ItemEvent.DESELECTED)
      addNewDB = 0;
  }//GEN-LAST:event_jchbAddDBActionPerformed
  
  private void jchbAddGroupActionPerformed (java.awt.event.ItemEvent evt) 
  {//GEN-FIRST:event_jchbAddGroupActionPerformed
    if (evt.getStateChange() == ItemEvent.SELECTED)
      addNewGroup = DBConfig.ADD_NEW_GROUPS;
    if (evt.getStateChange() == ItemEvent.DESELECTED)
      addNewGroup = 0;
  }//GEN-LAST:event_jchbAddGroupActionPerformed
  
  private void jbtnRefreshActionPerformed(java.awt.event.ActionEvent evt) 
  {//GEN-FIRST:event_jbtnRefreshActionPerformed
    refresh();
    if ((!users.contains(selectedUser)) && (selectedUser != null))
      {
	JOptionPane.showMessageDialog(this, "UserManager: the selected user " + selectedUser + " is likely to have been removed by another user.");
	reinitialize();
	jcbUserList.setSelectedIndex(0);
	selectedUser = null;
	jbtnAdd.setEnabled(true);
	jbtnSave.setEnabled(false);
	setNonUserGroups();
	jlstGroupList.setListData(nonUsergroups);
	jtxfUserName.requestFocus();
	return; 
      }
    isReload = true;
    jcbUserList.removeAllItems();
    for (int i = 0; i < refUsers.size(); i++)
      jcbUserList.addItem(refUsers.get(i));
    isReload = false;
    setNonUserGroups();
    jlstGroupList.setListData(nonUsergroups);
    if (selectedUser == null)
      return;
    jcbUserList.setSelectedItem(selectedUser);

    //userInfo = new UserInfo(selectedUser);
    //copy(userInfo.userGroups,usergroup);
    //jlstUserGroup.setListData(usergroup);
  }//GEN-LAST:event_jbtnRefreshActionPerformed

  private void setNonUserGroups()
  {
    nonUsergroups = (Vector)groups.clone();
    for (int i = 0; i < usergroup.size(); i++)
      {
	String temp = (String)usergroup.get(i);
	if (nonUsergroups.contains(temp))
	  nonUsergroups.remove(temp);
      }
  }
  
  private void refresh()
  {
    try
      {
	Client.getDBConfig1();
      }
    catch(ClientErrorException e)
      {
	JOptionPane.showMessageDialog(this, "UserManager: Client Error in getting DBConfig:\n " + e.toString() + "\nTransaction aborted!");
	return;
      }
    copy(Client.dbConfig.listUsers(),users);
    //System.out.println("refresh: GroupManager: Users:" + users);
    users.insertElementAt("New User", 0);
    refUsers = (Vector)users.clone();
    try
      {
	copy(Client.dbConfig.listGroups(Client.userName),groups);
	setNonUserGroups();
	//System.out.println("refresh: GroupManager: Groups:" + groups);
      }
    catch(DBConfigException e)
      {
	JOptionPane.showMessageDialog(this, "UserManager: Client Error in getting groups from DBConfig:\n " + e.toString() + "\nTransaction aborted!");
	return;
      }
  }
  
  private void copy(Vector v1, Vector v2)
  {
    v2.clear();
    for (int i = 0; i < v1.size(); i++)
      v2.add(v1.get(i));
    return;
  }   

  private void jbtnMakeDefaultActionPerformed(java.awt.event.ActionEvent evt)
  {//GEN-FIRST:event_jbtnMakeDefaultActionPerformed
    defAddDB = false;
    defAddAlg = false;
    defAddGrp = false;
    if (addNewDB == DBConfig.ADD_NEW_DATABASES)
      defAddDB = true;
    if (addNewAlg == DBConfig.ADD_NEW_ALGORITHMS)
      defAddAlg = true;
    if (addNewGroup == DBConfig.ADD_NEW_GROUPS)
      defAddGrp = true;
    defGroup.clear();
    defGroup = (Vector)usergroup.clone();
  }//GEN-LAST:event_jbtnMakeDefaultActionPerformed
  
  private void closeDialog(java.awt.event.WindowEvent evt) 
  {//GEN-FIRST:event_closeDialog
    setVisible (false);
    dispose ();
    parent.repaint();
  }//GEN-LAST:event_closeDialog
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  //private javax.swing.JLabel jlbUserList;
  
  private javax.swing.JComboBox jcbUserList;
  private javax.swing.JButton jbtnAdd;
  private javax.swing.JButton jbtnDelete;
  private javax.swing.JButton jbtnSave;
  private javax.swing.JButton jbtnClose;
  private javax.swing.JTextField jtxfUserName;
  private javax.swing.JList jlstGroupList;
  private javax.swing.JButton jbtnForward;
  private javax.swing.JButton jbtnBackward;
  private javax.swing.JList jlstUserGroup;
  private javax.swing.JCheckBox jchbAddAlg;
  private javax.swing.JCheckBox jchbAddDB;
  private javax.swing.JCheckBox jchbAddGroup;
  private javax.swing.JButton jbtnMakeDefault;
  private javax.swing.JButton jbtnRefresh;
  private javax.swing.JPasswordField jpfPassword;
  private javax.swing.JPasswordField jpfPasswordConfrm;
  private javax.swing.JFrame parent;
  private UserInfo userInfo = new UserInfo();
  private Vector usergroup = new Vector();
  private boolean defAddDB = false;
  private boolean defAddAlg = false;
  private boolean defAddGrp = false;
  private Vector defGroup = new Vector();
  private boolean isNewUser = true;
  private int addNewGroup = 0;
  private int addNewAlg = 0;
  private int addNewDB = 0;
  //private boolean isConfirmed = false;
  private boolean isReload = false;
  private javax.swing.JScrollPane scrollPane1;
  private javax.swing.JScrollPane scrollPane2;
  private Vector users = new Vector();
  private Vector refUsers = new Vector();
  private Vector groups = new Vector();
  private Vector nonUsergroups = new Vector();
  private String selectedUser;
  public Icon first = new ImageIcon("first.gif");
  public Icon last = new ImageIcon("last.gif");
  // End of variables declaration//GEN-END:variables

  class UserInfo
  {
    private String userName;
    private String password;
    private long permission;
    private Vector userGroups = new Vector();

    public UserInfo(String username)
    {
      try
	{
	  //copy(Client.dbConfig.listGroupsForUser(username),usergroups);
	  this.userName = username;
	  //System.out.println("user name: " + username);
	  //System.out.println("user groups: " + Client.dbConfig.listGroupsForUser(username).toString());
	  userGroups = Client.dbConfig.listGroupsForUser(username);
	}
      catch(DBConfigException e)
	{
	  JOptionPane.showMessageDialog(parent, "UserManagerDialog: Error in getting user groups from DBConfig:\n " + e.toString());
	  return;
	}
      try
	{
	  permission = Client.dbConfig.getPermissionsForUser(username);
	}
      catch(DBConfigException e)
	{
	  JOptionPane.showMessageDialog(parent,"UserManagerDialog: Error in getting user permissions from DBConfig:\n " + e.toString());
	  return;
	}
    }

    public UserInfo()
    {
      userName = new String();
      userGroups = new Vector();
      permission = 0;
    }

    public String toString()
    {
      String string = new String();
      string += "User name: " + userName + " ";
      string += "User groups: " + userGroups.toString() + " ";
      string += "User permission: " + permission;
      return string;
    }
  }
}
