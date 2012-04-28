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
import java.util.*;
import java.io.*;     
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.JTable.*;

/*

  Maintenance log started on November 16th, 2000 by Laurentiu Cristofor

  Nov. 16th, 2000   - made some style changes
                    - added inner class ResultTableModel
                    - disabled table cell selection and column swaping
                    - changed a label to say Previous instead of Prev

*/ 

/**

   BenchmarkResult.java<P>

   The dialog for displaying the benchmark results table .<P>

*/        

public class BenchmarkResult extends CenteredDialog 
{
  private static class ResultTableModel extends AbstractTableModel 
  {
    private static final int SUPPORTS_INDEX       = 0;
    private static final int DB_NAME_INDEX        = 0;
    private static final int ALG_NAME_INDEX       = 1;
    private static final int TIME_RESULTS_INDEX   = 2;
    private static final int PASSES_RESULTS_INDEX = 3;

    private Vector data ;
    private Vector results;
    private String[] headers;
    private int colCount;
    private int rowCount;
    private Hashtable ALG_names_hash;
    private Hashtable DB_names_hash;
    private Vector algorithm_names;
    private Vector database_names;
    private Vector supp_array;

    /**
     * Construct a new ResultTableModel object.
     */        
    public ResultTableModel(Vector results) 
    {
      this.results = results;

      supp_array = (Vector)results.elementAt(SUPPORTS_INDEX);

      // we deal with the algorithm names, we will
      // create a mapping between a name and a position
      // in an array which will eventually be converted to
      // a row index in the table
      ALG_names_hash = new Hashtable();
      // put all names in a hashtable
      for (int i = 1; i < results.size(); i++) 
	{
	  Vector v = (Vector)results.elementAt(i);
	  ALG_names_hash.put((String)v.elementAt(ALG_NAME_INDEX), "");
	}

      // get all names in some arbitrary order in a vector
      // and establish the mapping between the name and its
      // position in the vector
      algorithm_names = new Vector();
      for (Enumeration e = ALG_names_hash.keys(); e.hasMoreElements(); )
	{
	  String algorithm_name = (String)e.nextElement();
	  algorithm_names.add(algorithm_name);
	  ALG_names_hash.put(algorithm_name,
			     new Integer(algorithm_names.size() - 1));
	}

      rowCount = algorithm_names.size();
      //System.out.println("algorithm_names is " + algorithm_names);

      // now do the same thing for database names
      DB_names_hash  = new Hashtable();
      for (int i = 1; i < results.size(); i++)
	{
	  Vector v = (Vector)results.elementAt(i);
	  DB_names_hash.put((String)v.elementAt(DB_NAME_INDEX), "");
	}

      database_names = new Vector();
      for(Enumeration e = DB_names_hash.keys(); e.hasMoreElements(); )
	{
	  String database_name = (String)e.nextElement();
	  database_names.add(database_name);
	  DB_names_hash.put(database_name,
			    new Integer(database_names.size() - 1));
	}

      // we count one extra column for the column that
      // will keep the algorithm names
      colCount = database_names.size() + 1;
      //System.out.println("database_names is " + database_names);

      // now initialize the headers array
      headers = new String[colCount];
      headers[0] = "Algorithms \\ Databases";
      for (int i = 0 ; i < database_names.size() ; i++)
	headers[i + 1] = (String)database_names.get(i);
    }
    
    public String getColumnName(int i) 
    { 
      return headers[i];
    }

    public int getColumnCount() 
    { 
      return colCount;
    }

    public int getRowCount() 
    { 
      return rowCount;
    }
    
    public Object getValueAt(int row, int col) 
    {
      return ((Vector)data.get(row)).get(col);
    }
    
    /**
     * This method will be called to change the contents of the table,
     * when we want to display the results obtained for a different 
     * support value.
     */
    public void change_data(int supp) 
    {
      // reinitialize data
      data = new Vector(); 

      if (supp >= supp_array.size())
	supp = supp_array.size() - 1;
      if (supp < 0)
	supp = 0;
        
      for (int RowCount = 0; RowCount < algorithm_names.size(); RowCount++)
	{
	  //System.out.println("RowCount is " + RowCount);

	  // rowdata will contain the information that will
	  // be displayed in one row of the table
	  // we fill initially the rowdata vector with nulls
	  // and then we will overwrite these nulls with
	  // the appropriate test results
	  Vector rowdata = new Vector(colCount);
	  rowdata.setSize(colCount);
	  Object algorithm_name = algorithm_names.get(RowCount);
	  rowdata.set(0, algorithm_name);

	  for (int counter = 1; counter < results.size(); counter ++) 
	    {
	      Vector result = (Vector)results.elementAt(counter);
	      // check if this is a result regarding our algorithm
	      if (algorithm_name.equals(result.get(ALG_NAME_INDEX))) 
		{
		  Vector time_results =
		    (Vector)result.get(TIME_RESULTS_INDEX);
		  Vector passes_results =
		    (Vector)result.get(PASSES_RESULTS_INDEX);

		  Object time = time_results.get(supp); 
		  Object passes = passes_results.get(supp);

		  Object database_name = result.get(DB_NAME_INDEX);
		  // find the column index for the test result
		  // involving this specific database
		  // here we use the hashtable
		  int col_index = 
		    ((Integer)DB_names_hash.get(database_name)).intValue();
		  // note that we adjust the index by 1 since we have the
		  // algorithm name on the first position
		  rowdata.set(col_index + 1 , "" + time + " / " + passes);
		}
	    } 

	  // add this row
	  data.add(rowdata);
	} 

      // signal that the table contents have been changed
      fireTableDataChanged();
    }
  }


  private ResultTableModel qtm;
  private int new_support = 0;
  private int support_size; 
  private Vector V = new Vector();
  private Vector supp_array = new Vector();
  private JLabel lbv;
  private String filename = ""; 

  public BenchmarkResult(Vector new_data) 
  {
    setSize(400, 350);
    setLocation(300,300);
    setTitle("Benchmark Results");
    V = new_data;
    supp_array = (Vector)V.elementAt(0);
    support_size = supp_array.size();

    qtm = new ResultTableModel(new_data);
    JTable table = new JTable(qtm);
    table.setPreferredScrollableViewportSize(new Dimension(390, 180)); 
    table.getTableHeader().setReorderingAllowed(false); 
    table.setCellSelectionEnabled(false);
    table.setColumnSelectionAllowed(false);
    table.setRowSelectionAllowed(false);

    JScrollPane sPane = new JScrollPane();
    sPane.getViewport().add(table);
    sPane.setVisible(true);
    JLabel lb = new JLabel("Current support is :");
    final JLabel lbv = new JLabel();
    Vector vec = new Vector();
    repaint();
    vec = (Vector)V.elementAt(0);
    lbv.setText((String)vec.elementAt(new_support).toString());      
        
    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JButton jbnext = new JButton("Next Support");
    JButton jbprevious = new JButton("Previous Support");
    JButton jbsavetime = new JButton("Save Time");
    JButton jbsavepasses = new JButton("Save Passes");
    JButton jbgraph = new JButton("Draw Graph");
    JButton jbclose = new JButton("Close");
        
    panel.setLayout(new GridLayout(3, 2,10,10));
    qtm.change_data(0); 
        
    jbnext.addActionListener(new ActionListener()
      {
	public void actionPerformed(ActionEvent e) 
	{          
	  new_support++;
	  if(new_support >= support_size)
	    new_support = support_size - 1;
	  repaint();
	  Vector vt = (Vector)V.elementAt(0);
	  lbv.setText((String)vt.elementAt(new_support).toString());
	  qtm.change_data(new_support);
	}
      });
        
    jbprevious.addActionListener(new ActionListener()
      {
	public void actionPerformed(ActionEvent e) 
	{
	  new_support--;
	  if(new_support < 0)
	    new_support = 0;
	  Vector vtt = new Vector();
	  repaint();
	  vtt = (Vector)V.elementAt(0);
	  lbv.setText((String)vtt.elementAt(new_support).toString());
	  qtm.change_data(new_support);
	}
      });
        
    jbsavetime.addActionListener(new ActionListener()
      {
	public void actionPerformed(ActionEvent e) 
	{
	  Save_data(2);
	  JOptionPane.showMessageDialog(null ,
					"Times have been saved in " 
					+ filename ,
					"" , 
					JOptionPane.INFORMATION_MESSAGE ); 
	}
      });
        
    jbsavepasses.addActionListener(new ActionListener()
      {
	public void actionPerformed(ActionEvent e) 
	{
	  Save_data(3);
	  JOptionPane.showMessageDialog(null ,
					"Passes have been saved in " 
					+ filename , 
					"" ,
					JOptionPane.INFORMATION_MESSAGE ); 
	}
      });
        
    jbclose.addActionListener(new ActionListener()
      {
	public void actionPerformed(ActionEvent e) 
	{
	  hide();
	  dispose();
	}
      });
        
    jbgraph.addActionListener(new ActionListener()
      {
	public void actionPerformed(ActionEvent e)
	{ 
	  BenchmarkGraph d = new BenchmarkGraph(V);
	  d.show();  
	}
      });
        
    panel.add(jbprevious);
    panel.add(jbnext);
    panel.add(jbsavetime);
    panel.add(jbsavepasses);
    panel.add(jbgraph);
    panel.add(jbclose);
    panel2.add(lb);
    panel2.add(lbv); 
    getContentPane().add(sPane,BorderLayout.NORTH);
    getContentPane().add(panel2,BorderLayout.CENTER);
    getContentPane().add(panel,BorderLayout.SOUTH);
    setModal(true);
  }
    
  /*
   * Save result in a text File
   * @t_or_p
   *  t_or_p = 2 ---> save Times
   *  t_or_p = 3 ---> save Passes
   * */
  public void Save_data(int t_or_p) 
  {
    Calendar calendar = new GregorianCalendar();        
    int second = calendar.get(Calendar.SECOND);
    int min = calendar.get(Calendar.MINUTE);
    int hour = calendar.get(Calendar.HOUR_OF_DAY); 
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH) + 1;
    int year = calendar.get(Calendar.YEAR) ;
    String sz = Integer.toString(year) +  "-" 
      + Integer.toString(month) + "-"
      + Integer.toString(day) + "@"
      + Integer.toString(hour) + "-" 
      + Integer.toString(min) + "-"
      + Integer.toString(second);

    //System.out.println(sz);
    if(t_or_p == 2)
      filename = "timeres_" + sz + ".txt";
    else if(t_or_p == 3)
      filename = "passres_" + sz + ".txt";
        
    File file = new File(filename);
    try 
      {
	DataOutputStream writer 
	  = new DataOutputStream(new 
	    BufferedOutputStream(new 
	      FileOutputStream(file)));
	writer.writeBytes(";ARMiner Benchmark ");
	if (t_or_p == 2)
	  writer.writeBytes("Time results\n");
	if (t_or_p == 3)
	  writer.writeBytes("Passes results\n");
	writer.writeBytes(";Date :");
	writer.writeBytes(new Date()+ "\n");
	writer.writeBytes(";Support ");
	Vector temp = new Vector();
	for(int i = 1; i < V.size(); i++)
	  {
	    String s =(String)(((Vector)V.elementAt(i)).elementAt(0));
	    String t =(String)(((Vector)V.elementAt(i)).elementAt(1));
	    writer.writeBytes("\t" + s + " " + t );
	  } 
            
	writer.writeBytes("\n");
	for(int k = 0; k < supp_array.size();k++)   
	  {
	    writer.writeBytes("\n");
	    writer.writeBytes((String)(((Vector)V.elementAt(0)).elementAt(k)).toString());
	    for(int t = 1; t < V.size()  ; t++)
	      {
		writer.writeBytes("\t\t");
		temp = (Vector)(((Vector)V.elementAt(t)).elementAt(t_or_p));
		writer.writeBytes((String)temp.elementAt(k).toString());
	      }
	  }
	writer.flush();
	writer.close();
      }
    catch(IOException e)
      { 
	JOptionPane.showMessageDialog(null,
				      "An error happened: " + e,
				      "Error",
				      JOptionPane.ERROR_MESSAGE );
	System.out.println(e);
      }
  }
}
