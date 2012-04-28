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

/*

  Maintenance log started on November 17th, 2000 by Laurentiu Cristofor

  Nov. 17th, 2000   - made some style changes

*/ 

/**
 * CenteredDialog.java<P>
 * 
 * Create a dialog to allow the subclass be able to center with respect
 * to its parent window.
 */

public class CenteredDialog extends JDialog
{  
  public CenteredDialog(JFrame dw,String title, boolean modal)
  {  
    super(dw,title,modal);
  }

  public CenteredDialog(JDialog dw,String title, boolean modal)
  {  
    super(dw,title,modal);
  }

  public CenteredDialog()
  {  
    super();
  }

  public Point getDialogCenteredLoc(JFrame parent)
  {
    Toolkit tk = Toolkit.getDefaultToolkit();
    Point p = parent.getLocation();
    //System.out.println(p.toString());
    Dimension df = parent.getSize();
    //System.out.println(df.toString());
    Dimension dd = getSize();
    //System.out.println(dd.toString());
    int dy;
    int dx;
    if(df.height >= dd.height)
      dy = p.y + (df.height - dd.height) / 2;
    else 
      dy = p.y - (dd.height - df.height) / 2;
    if(df.width >= dd.width)
      dx = p.x + (df.width - dd.width) / 2;
    else 
      dx = p.x - (dd.width - df.width) / 2;
    return new Point(dx,dy);
  }	

  public Point getDialogCenteredLoc(JDialog parent)
  {
    Toolkit tk = Toolkit.getDefaultToolkit();
    Point p = parent.getLocation();
    Dimension df = parent.getSize();
    Dimension dd = getSize();
    int dy;
    int dx;
    if(df.height >= dd.height)
      dy = p.y + (df.height - dd.height) / 2;
    else 
      dy = p.y - (dd.height - df.height) / 2;
    if(df.width >= dd.width)
      dx = p.x + (df.width - dd.width) / 2;
    else 
      dx = p.x - (dd.width - df.width) / 2;
    return new Point(dx,dy);
  }	
}

