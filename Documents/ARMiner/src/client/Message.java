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

import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/*

  Maintenance log started on November 17th, 2000 by Laurentiu Cristofor

  Nov. 17th, 2000   - made some style changes

*/ 

public class Message extends JDialog
{
  public Message(String message, JDialog parent)
  {
    super();
    this.parent = parent;
    this.parent.setModal(false);
    setModal(true);
		
    // This line prevents the "Swing: checked access to system event queue"
    // message seen in some browsers.
    getRootPane().putClientProperty("defeatSystemEventQueueCheck", 
				    Boolean.TRUE);
		
    getContentPane().setLayout(new BorderLayout());
    //getContentPane().setBackground(java.awt.Color.white);
    getContentPane().setSize(200, 150);
		
    JLabel lblMessage = new JLabel(message, JLabel.CENTER);
    getContentPane().add(lblMessage, BorderLayout.CENTER);
		
    btnOK.setText("OK");
    JPanel okPanel = new JPanel();
    okPanel.add(btnOK);
    getContentPane().add(okPanel, BorderLayout.SOUTH);
		
    SymAction lSymAction = new SymAction();
    btnOK.addActionListener(lSymAction);
  }
		
  javax.swing.JButton btnOK = new javax.swing.JButton();
  JDialog parent;
	
  class SymAction implements java.awt.event.ActionListener
  {
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
      Object object = event.getSource();
      if (object == btnOK)
	btnOK_actionPerformed(event);
				
    }
  }
	
  public void btnOK_actionPerformed(java.awt.event.ActionEvent event)
  {
    this.hide();
    parent.setModal(true);
    this.dispose();
  }
	
  public static void main(String args[])
  {
    //Message ms = new Message("this is a long test");
    //ms.setSize(350,150);
    //ms.show();
  }
}
		
