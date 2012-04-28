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

/*

  Maintenance log started on November 16th, 2000 by Laurentiu Cristofor

  Nov. 16th, 2000   - made some style changes
                    - added inner class GraphPanel

*/ 

/**
 * BenchmarkGraph.java<P>
 * 
 * Displays benchmark results in a graphical format.
 * 
 */

public class BenchmarkGraph extends CenteredDialog 
{ 
  public BenchmarkGraph(Vector V)
  {
    setSize(400,400);
    setLocation(300,300);
    setTitle("Benchmark Graph");
    JPanel panel = new JPanel();
    JButton jbtclose = new JButton("Close");
    panel.add(jbtclose);
    GraphPanel bench = new GraphPanel(V);
    getContentPane().add(bench,BorderLayout.CENTER);
    getContentPane().add(panel,BorderLayout.SOUTH);
    
    jbtclose.addActionListener(new ActionListener()
      {
	public void actionPerformed(ActionEvent e) 
	{
	  hide();
	  dispose();
	}
      });
  }

  private static class GraphPanel extends JPanel 
  { 
    private static final int SUPPORTS_INDEX       = 0;
    private static final int DB_NAME_INDEX        = 0;
    private static final int ALG_NAME_INDEX       = 1;
    private static final int TIME_RESULTS_INDEX   = 2;
    private static final int PASSES_RESULTS_INDEX = 3;

    private static final double PCT_GRAPH_USED = 0.9;

    private static final int LEFT_MARGIN   = 60;
    private static final int RIGHT_MARGIN  = 60;
    private static final int TOP_MARGIN    = 60;
    private static final int BOTTOM_MARGIN = 60;

    private int LEFT_FRAME_MARGIN   = 10;
    private int RIGHT_FRAME_MARGIN  = 20;
    private int TOP_FRAME_MARGIN    = 10;
    private int BOTTOM_FRAME_MARGIN = 20;  

    private int INITIAL_WIDTH  = 600;
    private int INITIAL_HEIGHT = 400;

    private Vector data;

    private int[] rvals;
    private int[] gvals;
    private int[] bvals;

    private int support_size;
  
    private long MIN_TIME;
    private long MAX_TIME;
    private float MIN_SUPPORT;
    private float MAX_SUPPORT;

    private String title;

    public GraphPanel(Vector results)
    {      
      //System.out.println("data from Benchmark :" + results);

      data = results;      
      setPreferredSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT)); 
      Vector supports = (Vector)data.get(SUPPORTS_INDEX); 
      support_size = supports.size();
      // the supports are ordered!
      MIN_SUPPORT = ((Float)supports.get(0)).floatValue();
      MAX_SUPPORT = ((Float)supports.get(support_size - 1)).floatValue();
      title = "ARMiner Benchmark (time/support)";

      // we determine the minimum and maximum time
      MIN_TIME = Long.MAX_VALUE;
      MAX_TIME = 0;
      for(int i = 1; i < data.size(); i++)
	{
	  Vector test_result = (Vector)results.get(i);
	  Vector times = (Vector)test_result.get(TIME_RESULTS_INDEX);
	  for(int j = 0; j < times.size(); j++)
	    {
	      long time = ((Long)times.get(j)).longValue();
	      if (time > MAX_TIME)
		MAX_TIME = time;
	      if (time < MIN_TIME)
		MIN_TIME = time;
	    }     
	}
      //System.out.println("MIN_TIME is: " + MIN_TIME);
      //System.out.println("MAX_TIME is: " + MAX_TIME);
    }
  
    public void paintComponent(Graphics g) 
    {
      // get size of window
      Dimension dim = getSize();

      super.paintComponent(g);
      // the following also draws the frame
      paintBackground(g, Color.white);   
      g.setColor(Color.black);

      // draw title
      g.drawString(title, (int)(dim.getWidth() / 2) - 90, 40);   

      // draw y axis
      g.drawLine(LEFT_MARGIN, TOP_MARGIN, LEFT_MARGIN,
		 (int)dim.getHeight() - BOTTOM_MARGIN);

      // draw x axis
      g.drawLine(LEFT_MARGIN, (int)dim.getHeight() - BOTTOM_MARGIN,
		 (int)dim.getWidth() - RIGHT_MARGIN,
		 (int)dim.getHeight() - BOTTOM_MARGIN);

      // draw y end arrow
      g.drawLine(LEFT_MARGIN - 5, TOP_MARGIN + 5, LEFT_MARGIN, TOP_MARGIN); 
      g.drawLine(LEFT_MARGIN + 5, TOP_MARGIN + 5, LEFT_MARGIN, TOP_MARGIN); 

      // draw x end arrow
      g.drawLine((int)dim.getWidth() - RIGHT_MARGIN - 5,
		 (int)dim.getHeight() - BOTTOM_MARGIN - 5,
		 (int)dim.getWidth() - RIGHT_MARGIN,
		 (int)dim.getHeight() - BOTTOM_MARGIN);
      g.drawLine((int)dim.getWidth() - RIGHT_MARGIN - 5,
		 (int)dim.getHeight() - BOTTOM_MARGIN + 5,
		 (int)dim.getWidth() - RIGHT_MARGIN,
		 (int)dim.getHeight() - BOTTOM_MARGIN);

      // generate some colors randomly (for fun)
      if (rvals == null)
	{
	  rvals = new int[data.size()];
	  gvals = new int[data.size()];
	  bvals = new int[data.size()];

	  for ( int i = 1; i < data.size(); i++) {
	    rvals[i] = (int)Math.floor(Math.random() * 256);
	    rvals[i] %= 256;
	    gvals[i] = (int)Math.floor(Math.random() * 256) + i;
	    gvals[i] %= 256;
	    bvals[i] = (int)Math.floor(Math.random() * 256);
	    bvals[i] %= 256;
	  }
	}

      int rval, gval, bval;
      int legend_y = 0;

      // loop through all tests
      for (int i = 1; i < data.size(); i++) 
	{
	  // set color for this graph
	  rval = rvals[i];
	  gval = gvals[i];
	  bval = bvals[i];
	  g.setColor(new Color(rval,gval,bval));
	  Font subft=new Font("Helvetica", Font.BOLD, 10);
	  g.setFont(subft);

	  Vector supports = (Vector)(data.get(SUPPORTS_INDEX));
	  Vector test_result = (Vector)(data.get(i));

	  // build the legend for this test
	  String s = (String)test_result.get(ALG_NAME_INDEX) + " on "
	    + (String)test_result.get(DB_NAME_INDEX);

	  g.drawString(s, (int)dim.getWidth() - RIGHT_MARGIN - 100, 
		       TOP_MARGIN + legend_y); 
	  legend_y += 10;

	  int xmin, xmax, ymin, ymax;    

	  // loop through all graph segments
	  for (int j = 0; j < support_size - 1; j++) {
	    Vector time_results = 
	      (Vector)test_result.get(TIME_RESULTS_INDEX);

	    String xlabel = ((Float)supports.get(j)).toString();
	    String ylabel = ((Long)time_results.get(j)).toString();

	    // scale the x dimensions
	    xmin = (int) (((((Float)supports.get(j)).floatValue() 
			    - MIN_SUPPORT)
			   / (MAX_SUPPORT - MIN_SUPPORT)) 
			  * PCT_GRAPH_USED 
			  * (dim.getWidth() 
			     - LEFT_MARGIN - RIGHT_MARGIN)
			  + 10);
	    xmax = (int) (((((Float)supports.get(j+1)).floatValue() 
			    - MIN_SUPPORT)
			   / (MAX_SUPPORT - MIN_SUPPORT)) 
			  * PCT_GRAPH_USED 
			  * (dim.getWidth() -
			     LEFT_MARGIN - RIGHT_MARGIN)
			  + 10); 
	    // scale the y dimensions
	    long ytime = ((Long)time_results.get(j)).longValue();
	    long y2time = ((Long)time_results.get(j+1)).longValue();

	    ymin = (int) ((((double)ytime - (double)MIN_TIME)
			   / (double)(MAX_TIME - MIN_TIME)) 
			  * PCT_GRAPH_USED 
			  * (dim.getHeight() - BOTTOM_MARGIN - TOP_MARGIN) 
			  + 10);
	    ymax = (int) ((((double)y2time - (double)MIN_TIME)
			   / (double)(MAX_TIME - MIN_TIME)) 
			  * PCT_GRAPH_USED 
			  * (dim.getHeight() - BOTTOM_MARGIN - TOP_MARGIN) 
			  + 10);
      
	    // draw one line, a portion of this graph
	    g.drawLine(xmin + LEFT_MARGIN, 
		       (int)dim.getHeight() - BOTTOM_MARGIN - ymin,
		       xmax + LEFT_MARGIN, 
		       (int)dim.getHeight() - BOTTOM_MARGIN - ymax);

	    // reset color for printing labels
	    g.setColor(Color.black);  
	    Font subff=new Font("Helvetica", Font.BOLD, 10);
	    g.setFont(subff);
	    // draw a y label
	    g.drawString(ylabel, LEFT_MARGIN - 30, 
			 (int)dim.getHeight() - BOTTOM_MARGIN - ymin);
	    // draw a x label
	    g.drawString(xlabel, xmin + LEFT_MARGIN,
			 (int)dim.getHeight() - BOTTOM_MARGIN + 10);

	    // for the last segment we need to also
	    // write labels for the last point
	    if (j == support_size - 2)
	      {
		xlabel = ((Float)supports.get(j + 1)).toString();
		ylabel = ((Long)time_results.get(j + 1)).toString();
		// draw a y label
		g.drawString(ylabel, LEFT_MARGIN - 30, 
			     (int)dim.getHeight() - BOTTOM_MARGIN 
			     - ymax);
		// draw a x label
		g.drawString(xlabel, xmax + LEFT_MARGIN,
			     (int)dim.getHeight() - BOTTOM_MARGIN 
			     + 10);
	      }

	    // reset back color for next piece of graph
	    g.setColor(new Color(rval,gval,bval));
	  }   
	}
    }

    public void paintBackground(Graphics g, Color c) 
    {   
      Dimension dim = getSize();
    
      Color old_c = g.getColor();
      g.setColor(c);   
      g.fillRect(LEFT_FRAME_MARGIN, TOP_FRAME_MARGIN,
		 (int)dim.getWidth() - RIGHT_FRAME_MARGIN,
		 (int)dim.getHeight() - BOTTOM_FRAME_MARGIN);
      g.setColor(old_c);  
      g.drawRect(LEFT_FRAME_MARGIN, TOP_FRAME_MARGIN,
		 (int)dim.getWidth() - RIGHT_FRAME_MARGIN,
		 (int)dim.getHeight() - BOTTOM_FRAME_MARGIN); 
    }
  }
}    
