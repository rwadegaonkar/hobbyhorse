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

import java.io.*;

/**

   DBCacheWriter.java<P>

   A DBCacheWriter serializes itemsets to a cache.<P>
   
*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public class DBCacheWriter
{
  private ObjectOutputStream outstream;
  private String filename;

  /**
   * Initializes a DBCacheWriter to write to the specified cache file.
   *
   * @param name   name of the cache file
   * @exception IllegalArgumentException   <code>name</code> is null
   * @exception IOException   from java.io package
   */                                
  public DBCacheWriter(String name)
    throws IOException 
  {
    if (name == null)
      throw new IllegalArgumentException("Constructor argument must be non null");

    filename = name; 
    outstream = new ObjectOutputStream(new FileOutputStream(filename));
  }

  /**
   * Closes the cache file.
   *
   * @exception IOException   from java.io package
   */                                
  public void close()
    throws IOException
  {
    outstream.close();
  }

  /**
   * Write an itemset to the cache.
   *
   * @exception IOException   from java.io package
   */                
  public void writeItemset(Itemset is)
    throws IOException
  {
    outstream.writeObject(is);
  }

  /** 
   * for testing purposes only !!! 
   */
  public static void main(String args[])
  {
    try
      {
	DBCacheWriter dbcache = new DBCacheWriter("test.cache");
	
	Itemset is = new Itemset();

	is.addItem(1);
	is.addItem(13);
	is.setSupport((float)3.2);
	dbcache.writeItemset(is);
	
	is = new Itemset();
	is.addItem(2);
	is.addItem(7);
	is.setSupport((float)44.72);
	dbcache.writeItemset(is);
	
	is = new Itemset();
	is.addItem(10);
	is.addItem(5);
	is.addItem(5);
	is.addItem(7);
	is.setSupport((float)13.2);
	dbcache.writeItemset(is);
	
	is = new Itemset();
	is.addItem(51);
	is.addItem(13);
	is.setSupport((float)3.33);
	dbcache.writeItemset(is);

	dbcache.close();
      }
    catch (IOException e)
      {
	System.out.println(e);
      }
  }
}
 
