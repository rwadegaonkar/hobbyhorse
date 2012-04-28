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

  Maintenance log started on November 15th, 2000 by Laurentiu Cristofor

  Nov. 15th, 2000   - changed a call from deprecated getText to getPassword
                    - made some style changes
                    - changed button label from Start to Login

*/ 

/**

  Login.java<P>

  Login is a used to login to the system.<P>

*/

public class Login extends JDialog  
{
  public Login()
  {
    super();

    // This line prevents the "Swing: checked access to system event queue"
    // message seen in some browsers.
    getRootPane().putClientProperty("defeatSystemEventQueueCheck", 
				    Boolean.TRUE);

    getContentPane().setLayout(null);
    getContentPane().setBackground(java.awt.Color.white);
    setSize(460,500);
    setLocation(200, 200);
    setTitle("Login to ARMiner Server");

    label1.setText("User Name: ");
    getContentPane().add(label1);
    label1.setFont(new Font("SansSerif", Font.BOLD, 14));
    label1.setBounds(84,80,90,30);

    l.setText("Password:");
    getContentPane().add(l);
    l.setFont(new Font("SansSerif", Font.BOLD, 14));
    l.setBounds(84,110,108,30);

    lblHostName.setText("Host Name: ");
    getContentPane().add(lblHostName);
    lblHostName.setFont(new Font("SansSerif", Font.BOLD, 14));
    lblHostName.setBounds(84,140,90,30);

    lblPort.setText("Port: ");
    getContentPane().add(lblPort);
    lblPort.setFont(new Font("SansSerif", Font.BOLD, 14));
    lblPort.setBounds(84,170,90,30);

    getContentPane().add(txtUserName);
    txtUserName.setBounds(204,80,109,25);
    getContentPane().add(txtPassword);
    txtPassword.setBounds(204,110,109,25);

    txtHostName.setText("");
    getContentPane().add(txtHostName);
    txtHostName.setBounds(204,140,109,25);

    txtPort.setText("8072");
    getContentPane().add(txtPort);
    txtPort.setBounds(204,170,109,25);

    lblWarning.setText("Login Failed! ");
    getContentPane().add(lblWarning);
    lblWarning.setForeground(java.awt.Color.red);
    lblWarning.setFont(new Font("SansSerif", Font.BOLD, 14));
    lblWarning.setBounds(134,210,100,36);

    lblWarning.setVisible(false);

    btnSubmit.setText("Login");
    getContentPane().add(btnSubmit);
    btnSubmit.setBounds(120,250,84,33);
    btnCancel.setText("Cancel");
    getContentPane().add(btnCancel);
    btnCancel.setBounds(228,250,84,33);
   
    SymAction lSymAction = new SymAction();
    keyPressAction kAction = new keyPressAction();
    //txtUserName.addKeyListener(kAction);
    txtPassword.addKeyListener(kAction);
    txtHostName.addKeyListener(kAction);
    txtPort.addKeyListener(kAction);
    btnSubmit.addKeyListener(kAction);
    btnSubmit.addActionListener(lSymAction);
    btnCancel.addActionListener(lSymAction);
  }

  javax.swing.JLabel label1 = new javax.swing.JLabel();
  javax.swing.JLabel l = new javax.swing.JLabel();
  javax.swing.JLabel lblHostName = new javax.swing.JLabel();
  javax.swing.JLabel lblPort = new javax.swing.JLabel();
  javax.swing.JLabel lblWarning = new javax.swing.JLabel();
  javax.swing.JTextField txtUserName = new javax.swing.JTextField();
  javax.swing.JPasswordField txtPassword = new javax.swing.JPasswordField();
  javax.swing.JTextField txtHostName = new javax.swing.JTextField();
  javax.swing.JTextField txtPort = new javax.swing.JTextField();
  javax.swing.JButton btnSubmit = new javax.swing.JButton();
  javax.swing.JButton btnCancel = new javax.swing.JButton();

  class SymAction implements java.awt.event.ActionListener
  {
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
      Object object = event.getSource();
      if (object == btnSubmit)
	btnSubmit_actionPerformed(event);
      else if (object == btnCancel)
	btnCancel_actionPerformed(event);
    }
  }
  
  class keyPressAction implements KeyListener
  {
    public void keyPressed(java.awt.event.KeyEvent event)
    {
      int keyCode = event.getKeyCode();
      if(keyCode == KeyEvent.VK_ENTER){
	submit();
      }
    }
    
    public void keyReleased(KeyEvent e) {}
     
    public void keyTyped(KeyEvent e) {}

  }
  
  private void submit()
  {
    lblWarning.setVisible(false);

    //Get the value in the input fields
    String userName = txtUserName.getText();
    String password = new String(txtPassword.getPassword());
    String hostName = txtHostName.getText();

    Socket socket;

    try
      {
	int port = Integer.parseInt(txtPort.getText());

	InetAddress hostAddress;
	if (hostName.equals(""))
	  hostAddress = InetAddress.getLocalHost();
	else
	  hostAddress = InetAddress.getByName(hostName);
	  
	socket = new Socket(hostAddress, port);

	Client.out = new ObjectOutputStream(socket.getOutputStream());
	Client.in  = new ObjectInputStream(socket.getInputStream());

	Client.userName = userName;
      }
    catch (Exception e)
      {
	Client.reset();
	lblWarning.setVisible(true);
	return;
      }

    Vector feedBack = Client.login(userName, password);
    
    String response = (String)feedBack.elementAt(0);
    //System.out.println(response);
    if (response.equals("OK"))
      {
	hide();
	dispose();
	MainFrame mf = new MainFrame();
	mf.show();
      }
    else
      {
	try
	  {
	    Client.clientExit();
	    socket.close();
	  }
	catch(Exception e)
	  {
	    Client.reset();
	  }
	lblWarning.setVisible(true);
      }
  }
  	

  public void keyTyped(KeyEvent e) 
  {
    //System.out.println("OK");
    submit();
  }
  
  public void btnSubmit_actionPerformed(java.awt.event.ActionEvent event)
  {	
    submit();
  }

  public void btnCancel_actionPerformed(java.awt.event.ActionEvent event)
  {	
    hide();
    dispose();
    System.exit(0);
  }

  public static void main(String[] args)
  {
    Login login = new Login();
    login.show();
  }
}
