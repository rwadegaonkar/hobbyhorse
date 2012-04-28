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
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

/*

  Maintenance log started on November 15th, 2000 by Laurentiu Cristofor

  Nov. 15th, 2000   - made some style changes
                    - changed the format in which results are saved
                      in a file, it should be more lisible now
                    - the columns of the table can no longer be reordered,
                      this was not useful and the sort method had trouble
                      figuring out what column to sort

  Nov. 16th, 2000   - added paint method for resizing table
                    - added inner class ResultTableModel
                    - added method refresh to ResultTableModel
                    - corrected spelling errors for a message

  Nov. 21st, 2000   - modified fillCells to be static and to display
                      items separated by commas. It is static so that
                      it can be called in MiningDialog.

  Apr. 5th, 2001    - added methods getNextResults() and
                      getAllRemainingResults()
                    - added button SaveAll that enables the user
                      to save all discovered rules 
                      (with side effect of displaying all rules)
                    - resized and repositioned buttons
                    - created SaveActionListener class

*/ 

/**

  MiningResult.java<P>

  The page to show the result of mining.<P>

*/

public class MiningResult extends CenteredDialog
{
  // TableModel for our JTable
  private static class ResultTableModel extends AbstractTableModel
  {
    private Vector cells;
    private Vector columnNames;
    
    public ResultTableModel(Vector cells, Vector columnNames)
    {
      this.cells = cells;
      this.columnNames = columnNames;
    }
  
    public int getRowCount()
    {
      return cells.size();
    }
  
    public int getColumnCount()
    {
      return columnNames.size();
    }
  
    public Object getValueAt(int row, int column)
    {
      Vector cellRow = (Vector)cells.elementAt(row);
      Object cell = cellRow.elementAt(column);
      return cell;
    }
  
    public String getColumnName(int i)
    {
      try
	{
	  return columnNames.elementAt(i).toString();
	}
      catch(Exception e)
	{
	  return "";
	}
    }
  
    public void refresh(Vector cells, Vector columnNames)
    {
      this.cells = cells;
      this.columnNames = columnNames;
      fireTableDataChanged();
    }  

    public void refresh()
    {
      fireTableDataChanged();
    }  
  }

  private static final int CHAR_SIZE   = 2;
  private static final int CELL_LENGTH = 40;
  private static final int CELL_SIZE   = CELL_LENGTH * CHAR_SIZE;

  Vector cells;
  Vector columnNames;
  private Vector names;
  private ResultTableModel resultModel;

  JDialog parent;
  JDialog uparent;
  JTable jtResult;
  JScrollPane scrollPane;
  javax.swing.JLabel JLabel1 = new javax.swing.JLabel();
  javax.swing.JButton btnNext = new javax.swing.JButton();
  javax.swing.JButton btnSave = new javax.swing.JButton();
  javax.swing.JButton btnSaveAll = new javax.swing.JButton();
  javax.swing.JButton btnClose = new javax.swing.JButton();

  public MiningResult(Vector cells, Vector columnNames, Vector names, 
		      JDialog parent1)
  {
    super(parent1, "Mining Result", true);
    this.cells = cells;
    uparent = parent1;
    this.columnNames = columnNames;
    this.names = names;
    this.resultModel = new ResultTableModel(cells, columnNames);
    getRootPane().putClientProperty("defeatSystemEventQueueCheck", 
				    Boolean.TRUE);

    getContentPane().setLayout(null);
    setSize(580,580);
    setLocation(getDialogCenteredLoc(parent1)); 
    setTitle("Mining Result");

    jtResult = new JTable(resultModel);
    scrollPane = new JScrollPane(jtResult);
    getContentPane().add(scrollPane);
    scrollPane.setBounds(8,10,560,480);

    btnNext.setText("Next");
    getContentPane().add(btnNext);
    btnNext.setBounds(80,500,100,25);

    btnSave.setText("Save");
    getContentPane().add(btnSave);
    btnSave.setBounds(180,500,100,25);

    btnSaveAll.setText("Save All");
    getContentPane().add(btnSaveAll);
    btnSaveAll.setBounds(280,500,100,25);
	
    btnClose.setText("Close");
    getContentPane().add(btnClose);
    btnClose.setBounds(380,500,100,25);
   
    SymAction lSymAction = new SymAction();
    btnNext.addActionListener(lSymAction);
    btnClose.addActionListener(lSymAction);

    parent = this;

    btnSave.addActionListener(new SaveActionListener(false));
    btnSaveAll.addActionListener(new SaveActionListener(true));

    jtResult.getTableHeader().setReorderingAllowed(false); 
    jtResult.getTableHeader().addMouseListener(new MouseAdapter() {
	public void mouseClicked(MouseEvent event)
	{  
	  if (event.getClickCount() < 2) return;

	  int tableColumn = jtResult.columnAtPoint(event.getPoint());

	  sort(tableColumn);
	  btnNext.setEnabled(true);
	}
      });
  }

  public void paint(Graphics g)
  {
    super.paint(g);

    // get size of window
    Dimension dim = getSize();

    scrollPane.setBounds(8, 10, 
		    (int)dim.getWidth() - 20, 
		    (int)dim.getHeight() - 100);

    btnNext.setBounds((int)dim.getWidth()/2 - 200, 
		      (int)dim.getHeight() - 60, 100, 25);
    btnSave.setBounds((int)dim.getWidth()/2 - 100, 
		      (int)dim.getHeight() - 60, 100, 25);
    btnSaveAll.setBounds((int)dim.getWidth()/2, 
		      (int)dim.getHeight() - 60, 100, 25);
    btnClose.setBounds((int)dim.getWidth()/2 + 100, 
		       (int)dim.getHeight() - 60, 100, 25);
    
    // resize columns of table to fit and fill the available space
    resultModel.refresh();
  }

  class SymAction implements java.awt.event.ActionListener
  {
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
      Object object = event.getSource();
     
      if (object == btnNext)
	btnNext_actionPerformed(event);
      else if (object == btnClose)
	btnClose_actionPerformed(event);
    }
  }

  class SaveActionListener implements java.awt.event.ActionListener
  {
    private boolean bSaveAll;

    public SaveActionListener(boolean bSaveAll)
    {
      this.bSaveAll = bSaveAll;
    }

    public void actionPerformed(ActionEvent ae)
    {
      if (bSaveAll)
	getAllRemainingResults();
      
      JFileChooser chooser = new JFileChooser();
      int option = chooser.showSaveDialog(parent);
      if (option == JFileChooser.APPROVE_OPTION) 
	{
	  try
	    {
	      saveResult(chooser.getCurrentDirectory(), 
			 chooser.getSelectedFile().getName());
	    }
	  catch(IOException e)
	    {
	      JOptionPane.showMessageDialog(parent, 
					    "Failed to save file! " 
					    + e.toString());
	    }
	}
    }
  }

  private void saveResult(File directory, String fileName)
    throws IOException
  {
    File file = new File(directory, fileName);

    try
      {
	RandomAccessFile outStream = new RandomAccessFile(file, "rw");
	Vector innerCell = new Vector();
	for (int i = 0; i < cells.size(); i++)
	  {
	    innerCell = (Vector)cells.elementAt(i);
	    outStream.writeBytes("Association rule " + (i + 1) + ":\n");
	    for (int j = 0; j < 4; j++)
	      {
		switch (j)
		  {
		  case 0:
		    outStream.writeBytes("antecedent = <");
		    break;
		  case 1:
		    outStream.writeBytes("consequent = <");
		    break;
		  case 2:
		    outStream.writeBytes("support = <");
		    break;
		  case 3:
		    outStream.writeBytes("confidence = <");
		    break;
		  }

		String cellContent = (String)innerCell.elementAt(j);
		outStream.writeBytes(cellContent);
		outStream.writeBytes(">\n");
	      }
	    outStream.writeBytes("\n\n");
	  }
	
	outStream.close();
	JOptionPane.showMessageDialog(this, "The mining result has been saved successfully");    
      
      }
    catch(IOException e)
      {
	throw new IOException("Failed to write data to file! " 
			      + e.toString());
      }
  }

  // fill the JTable with all remaining results
  private void getAllRemainingResults()
  {
    // get remaining results one batch at a time
    while (getNextResults())
      ;
  }

  // returns true if the next results were obtained successfully
  // and false if there were no results or an error occurred
  // This method will issue an appropriate error box so the caller
  // doesn't have to handle any errors
  private boolean getNextResults()
  {
    try
      {
	Vector feedBack = Client.getNext();

	if (feedBack.size() > 0)
	  {
	    String response = feedBack.elementAt(0).toString();
	    if (!response.equals(new String("OK")))
	      {
		JOptionPane.showMessageDialog(this, feedBack.elementAt(1).toString());
		return false;
	      }
	    else
	      {
		Vector newResult = (Vector)feedBack.elementAt(1);

      		int resultSize = newResult.size();

		if (resultSize == 0)
		  {
		    btnNext.setEnabled(false);
		    return false;
		  }
		else
		  {
		    fillCells(newResult, names, cells);
		    resultModel.refresh(cells, columnNames);
		    return true;
		  }
	      }
	  }
	else
	  return false;
      }
    catch(Exception e)
      {
	JOptionPane.showMessageDialog(this, e.toString());
	return false;
      }
  }

  public void btnNext_actionPerformed(java.awt.event.ActionEvent event)
  {
    getNextResults();
  }

  public void btnClose_actionPerformed(java.awt.event.ActionEvent event)
  {
    this.hide();
    this.dispose();
  }

  public void sort(int tableColumn)
  {
    Client.order[tableColumn] = Math.abs(Client.order[tableColumn] - 1);
    int sort = Client.order[tableColumn];
    String sortOrder=null;
    String sortBy=null;

    if (sort == 0)
      sortOrder = "ASC";
    else
      sortOrder = "DESC";

    if (tableColumn == 0)
      sortBy = "ANTECEDENT";
    else if (tableColumn == 1)
      sortBy = "CONSEQUENT";
    else if (tableColumn == 2)
      sortBy = "SUPPORT";
    else
      sortBy = "CONFIDENCE";

    String action = "SORT";
    String sortString = action + sortBy + sortOrder;
    try
      {
	Vector feedBack = Client.sort(action, sortBy, sortOrder);
     
	if (feedBack.size()>0)
	  {
	    String response = feedBack.elementAt(0).toString();
	    if (!response.equals(new String("OK")))
	      JOptionPane.showMessageDialog(this, feedBack.elementAt(1).toString());
	    else
	      {
		Vector newResult = (Vector)feedBack.elementAt(1);
		Vector newCells = new Vector();

      		int resultSize = newResult.size();

		if (resultSize == 0)
		  btnNext.setEnabled(false);

		fillCells(newResult, names, newCells);
		cells = newCells;
		resultModel.refresh(newCells, columnNames);
	      }
	  }
      }
    catch(Exception e)
      {
	JOptionPane.showMessageDialog(this, e.toString());      
      }
  }

  // helper method for filling the cells of the table with results
  static void fillCells(Vector result, Vector names, Vector cells)
  {
    int resultSize = result.size();
    Vector innerCell;
    for (int i = 0; i < resultSize; i++)
      {
	AssociationRule rule = 
	  (AssociationRule)result.elementAt(i);
	
	innerCell = new Vector();
	String ant = new String();
	int j;
	for (j = 0; j < rule.antecedentSize() - 1; j++)
	  ant = ant 
	    + names.elementAt(rule.getAntecedentItem(j) - 1).toString() 
	    + ", " ;

	ant = ant 
	  + names.elementAt(rule.getAntecedentItem(j) - 1).toString();
		
	String con = new String();
	for (j = 0; j < rule.consequentSize() - 1; j++)
	  con = con 
	    + names.elementAt(rule.getConsequentItem(j) - 1).toString() 
	    + ", " ;

	con = con 
	  + names.elementAt(rule.getConsequentItem(j) - 1).toString();

	innerCell.add(ant);
	innerCell.add(con);
	innerCell.add(String.valueOf(rule.getSupport()));
	innerCell.add(String.valueOf(rule.getConfidence()));

	cells.add(innerCell);
      }
  }
}
