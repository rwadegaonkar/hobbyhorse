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
import java.util.*;

/*

  Maintenance log started on November 20th, 2000 by Laurentiu Cristofor

  Nov. 20th, 2000   - made some style changes
                    - changed text of some labels

  Nov. 21st, 2000   - commented label font and color setting
                    - resized and repositioned widgets

*/ 

/**

  MiningAdvanced.java<P>

  UI for advanced mining input.<P>

*/

public class MiningAdvanced extends JPanel
{
  public MiningAdvanced(JDialog parent)
  {
    antList = new Vector();
    ignList = new Vector();
    conList = new Vector();
    allListValue = new Vector();

    setLayout(null);
    setSize(500,400);

    lstAllusers.setValueIsAdjusting(true);
    lstAllusers.setVisibleRowCount(20);
    JScrollPane scrollPane1 = new JScrollPane(lstAllusers);
    add(scrollPane1);
    scrollPane1.setBounds(5,44,145,312);

    JLabel2.setText("All Items: ");
    add(JLabel2);
    //JLabel2.setForeground(java.awt.Color.black);
    //JLabel2.setFont(new Font("DialogInput", Font.PLAIN, 12));
    JLabel2.setBounds(5,8,135,34);
   
    add(btnAdd);
    btnAdd.setBounds(190,140,40,30);
	
    add(btnSubstract);
    btnSubstract.setBounds(190,200,40,30);

    rdOption.setActionCommand("ant");
    rdOption.setSelected(true);
    add(rdOption);
    rdOption.setBounds(280,14,25,20);
    rdOption1.setActionCommand("con");
    add(rdOption1);
    rdOption1.setBounds(280,134,25,20);
    rdOption2.setActionCommand("ign");
    add(rdOption2);
    rdOption2.setBounds(280,254,25,20);

    JLabel3.setText("Antecedent should contain: ");
    add(JLabel3);
    //JLabel3.setForeground(java.awt.Color.black);
    //JLabel3.setFont(new Font("DialogInput", Font.PLAIN, 12));
    JLabel3.setBounds(310,8,204,32);
    JLabel4.setText("Consequent should contain: ");
    add(JLabel4);
    //JLabel4.setForeground(java.awt.Color.black);
    //JLabel4.setFont(new Font("DialogInput", Font.PLAIN, 12));
    JLabel4.setBounds(310,128,192,32);
    JLabel5.setText("Items ignored: ");
    add(JLabel5);
    //JLabel5.setForeground(java.awt.Color.black);
    //JLabel5.setFont(new Font("DialogInput", Font.PLAIN, 12));
    JLabel5.setBounds(310,248,180,32);

    group.add(rdOption);
    group.add(rdOption1);
    group.add(rdOption2);

    JScrollPane scrollPane2 = new JScrollPane(lstAntecedent);
    add(scrollPane2 );
    scrollPane2.setBounds(320,44,165,72);

    JScrollPane scrollPane3 = new JScrollPane(lstConsequent);
    add(scrollPane3);
    scrollPane3.setBounds(320,164,165,72);

    JScrollPane scrollPane4 = new JScrollPane(lstItemsIgnored);
    add(scrollPane4 );
    scrollPane4.setBounds(320,284,165,72);

    JLabel7.setText("Antecedent items no more than: ");
    add(JLabel7);
    //JLabel7.setForeground(java.awt.Color.black);
    //JLabel7.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
    JLabel7.setBounds(5,364,210,41);

    add(txtAntecedent);
    txtAntecedent.setBounds(215,370,30,24);
    txtAntecedent.setText("0");

    JLabel6.setText("Consequent items no less than: ");
    add(JLabel6);
    //JLabel6.setForeground(java.awt.Color.black);
    //JLabel6.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
    JLabel6.setBounds(250,364,210,41);

    add(txtConsequent);
    txtConsequent.setBounds(460,370,30,24);
    txtConsequent.setText("0");

    SymAction lSymAction = new SymAction();
    btnAdd.addActionListener(lSymAction);
    btnSubstract.addActionListener(lSymAction);
  }

  private Vector antList;
  private Vector ignList;
  private Vector conList;
  private Vector allListValue;
  private JDialog parent;

  final ButtonGroup group = new ButtonGroup();

  javax.swing.JLabel JLabel1 = new javax.swing.JLabel();
  javax.swing.JList lstAllusers = new javax.swing.JList();
  javax.swing.JLabel JLabel2 = new javax.swing.JLabel();
  
  public Icon add = new ImageIcon("last.gif");
  public Icon sub = new ImageIcon("first.gif");
  javax.swing.JButton btnAdd = new javax.swing.JButton(add);
  javax.swing.JButton btnSubstract = new javax.swing.JButton(sub);
  javax.swing.JRadioButton rdOption = new javax.swing.JRadioButton();
  javax.swing.JLabel JLabel3 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel4 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel5 = new javax.swing.JLabel();
  javax.swing.JRadioButton rdOption1 = new javax.swing.JRadioButton();
  javax.swing.JRadioButton rdOption2 = new javax.swing.JRadioButton();
  javax.swing.JList lstAntecedent = new javax.swing.JList();
  javax.swing.JList lstConsequent = new javax.swing.JList();
  javax.swing.JList lstItemsIgnored = new javax.swing.JList();
  javax.swing.JLabel JLabel6 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel7 = new javax.swing.JLabel();
  javax.swing.JTextField txtAntecedent = new javax.swing.JTextField();
  javax.swing.JTextField txtConsequent = new javax.swing.JTextField();
  javax.swing.JLabel JLabel8 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel9 = new javax.swing.JLabel();

  class SymAction implements java.awt.event.ActionListener
  {
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
      Object object = event.getSource();
      if (object == btnAdd)
	btnAdd_actionPerformed(event);
      else if (object == btnSubstract)
	btnSubstract_actionPerformed(event);
    }
  }

  public void setList(Vector newValue)
  {
    allListValue = newValue;
    Vector newData = new Vector();
    this.lstAllusers.setListData(newValue);
    this.lstAntecedent.setListData(newData);
    lstConsequent.setListData(newData);
    lstItemsIgnored.setListData(newData);
  }

  public Vector getAnt()
  {
    return antList;
  }

  public Vector getCon()
  {
    return conList;
  }

  public Integer getMaxAnt() throws ClientException
  {
    Integer maxAnt = new Integer(0);
    try
      {
	maxAnt = Integer.valueOf(txtAntecedent.getText().toString());
      }
    catch(Exception f)
      {
	throw new ClientException("Value in this field must be an integer" + f.toString());
      }
    return maxAnt;
  }

  public Integer getMinCon() throws ClientException
  {
    Integer minCon = new Integer(0);
    try
      {
	minCon = Integer.valueOf(txtConsequent.getText().toString());
      }
    catch(Exception f)
      {
	throw new ClientException("Value in this field must be an integer" + f.toString());
      }
    return minCon;
  }

  public Vector getIgn()
  {
    return ignList;
  }


  void btnAdd_actionPerformed(java.awt.event.ActionEvent event)
  {
    Vector selected = new Vector();
    int [] selectedIndex = lstAllusers.getSelectedIndices();

    for(int i=0; i<selectedIndex.length; i++)
      {
	selected.add((String)lstAllusers.getModel().getElementAt(selectedIndex[i]));
      }
    try
      {
	allListValue.removeAll(selected);
      
	lstAllusers.setListData(allListValue);

	if(rdOption.isSelected())
	  {
	    antList.addAll(selected);
	    lstAntecedent.setListData(antList);
	  }

	if(rdOption1.isSelected())
	  {
	    conList.addAll(selected);
	    lstConsequent.setListData(conList);
	  }
	if(rdOption2.isSelected())
	  {
	    ignList.addAll(selected);
	    lstItemsIgnored.setListData(ignList);
	  }
      }
    catch(Exception f)
      {
	JOptionPane.showMessageDialog(this, "Error in setting list data: " + f.toString());
      }
  }

  void btnSubstract_actionPerformed(java.awt.event.ActionEvent event)
  {
    Vector selected = new Vector();

    if(rdOption.isSelected())
      {
	int [] selectedIndex = lstAntecedent.getSelectedIndices();

	for(int i=0; i<selectedIndex.length; i++)
	  {
	    selected.add((String)lstAntecedent.getModel().getElementAt(selectedIndex[i]));
	  }

	allListValue.addAll(selected);
	antList.removeAll(selected);

	lstAllusers.setListData(allListValue);
	lstAntecedent.setListData(antList);
      }

    if(rdOption1.isSelected())
      {
	int [] selectedIndex = lstConsequent.getSelectedIndices();

	for(int i=0; i<selectedIndex.length; i++)
	  {
	    selected.add((String)lstConsequent.getModel().getElementAt(selectedIndex[i]));
	  }

	allListValue.addAll(selected);
	conList.removeAll(selected);

	lstAllusers.setListData(allListValue);
	lstConsequent.setListData(conList);
      }

    if(rdOption2.isSelected())
      {
	int [] selectedIndex = lstItemsIgnored.getSelectedIndices();
	for(int i=0; i<selectedIndex.length; i++)
	  {
	    selected.add((String)lstItemsIgnored.getModel().getElementAt(selectedIndex[i]));
	  }
	allListValue.addAll(selected);
	ignList.removeAll(selected);

	lstAllusers.setListData(allListValue);
	lstItemsIgnored.setListData(ignList);
      }
  }  
}
