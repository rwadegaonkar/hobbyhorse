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
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/*

  Maintenance log started on November 15th, 2000 by Laurentiu Cristofor

  Nov. 15th, 2000   - made some style changes

  Nov. 16th, 2000   - changed dialog text

*/ 

class AboutDialog extends CenteredDialog  
{
  private JPanel TheTopPanel;
  private JPanel TheCenterPanel;
  private JPanel TheBottomPanel;
  private JButton TheCloseButton;
  private TextArea TheTextArea;
  JFrame parent;

  public AboutDialog (JFrame parent) 
  {
    this.parent = parent;
    CreateAboutDialog (); 
    setLocation(getDialogCenteredLoc((JFrame)parent));
  }

  public void CreateAboutDialog () 
  { 
    setModal(true);
    setTitle("About ARMiner...");
    setSize(350,350);
    setResizable(false);

    addWindowListener (new java.awt.event.WindowAdapter () {
        public void windowClosing (java.awt.event.WindowEvent evt) {
          closeDialog (evt);
        }
      });

    getContentPane().setLayout(new BorderLayout());
    TheTopPanel = new JPanel();
    TheTopPanel.add(new JLabel("ARMiner client application version 1.0b:"));
    getContentPane().add(TheTopPanel, "North");
    TheCenterPanel = new JPanel();

    TheTextArea = 
      new TextArea("The ARMiner client was written by:\n\n" +
		   "Abdelmajid Karatihy\n" +
		   "Xiaoyong Kuang\n" +
		   "Long-Tsong Li\n\n" +

		   "The ARMiner server was written by:\n\n" +
		   "Dana Cristofor\n" +
		   "Laurentiu Cristofor\n\n" +

		   "ARMiner (C) 2000-2001 UMass/Boston Computer Science Department\n" +
		   "Portions (C) 1998, 1999 Sun Microsystems, Inc.\n\n" +

		   "This software is distributed under the terms " +
		   "of the GNU General Public License", 15, 45, 
		   TextArea.SCROLLBARS_VERTICAL_ONLY);

    TheTextArea.setEditable(false);
    TheCenterPanel.add(TheTextArea);
    getContentPane().add(TheCenterPanel, "Center");
    TheBottomPanel = new JPanel();
    TheCloseButton = new JButton("Close");
    TheCloseButton.setMnemonic(KeyEvent.VK_C);
    TheBottomPanel.add(TheCloseButton);
    getContentPane().add(TheBottomPanel, "South");

    TheCloseButton.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e) {
	  hide();
	  dispose();
	  parent.repaint();
	}
      });
  }

  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) 
  {
    setVisible (false);
    dispose ();
    parent.repaint();
  }

  public static void main(String args[]) 
  {
    AboutDialog ad = new AboutDialog(new JFrame());
    ad.show();
  }  
}
