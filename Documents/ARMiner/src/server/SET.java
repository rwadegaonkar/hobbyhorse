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

import java.util.*;
	
/*

  Maintenance log started on November 30th, 2000

  Nov. 30th, 2000   - added getItemsets method
                    - added and renamed some private methods
                    - improved toString method

*/

/**

   SET.java<P>

   Implements a Set Enumeration Tree, which is a prefix tree used
   for storing and retrieving itemset information.<P>
   
*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Laurentiu Cristofor
   Lung-Tsung Li

*/

public class SET
{
  // inner class
  private class HashNode
  {
    Hashtable children;
    float support;
	
    public HashNode()
    {
      support = 0;
      children = new Hashtable();
    }
    
    public String toString()
    {
      String s = new String();
      s += "<children: " + children.toString() 
	+ " support: " + support + ">\n";
      return s;
    }
  }

  private HashNode root;
  private int level = 0;

  /**
   * Create a new empty SET.
   */
  public SET()
  {
    root = new HashNode();
  }

  /**
   * Insert a new itemset in the SET.
   *
   * @param itemset   the itemset to be inserted
   * @exception IllegalArgumentException   <code>itemset</code> is null
   * or is empty
   */
  public void insert(Itemset itemset)
  {
    if (itemset == null || itemset.size() == 0)
      throw new IllegalArgumentException("argument to insert() must be non null and non empty");

    Itemset is = new Itemset(itemset);
    Object obj;
    HashNode node;
    HashNode walker = root;
    Integer key;

    while (is.hasMoreItems())
      {
	key = new Integer(is.getNextItem());
		
	if ((obj = walker.children.get(key)) != null)
	  walker = (HashNode)obj;
	else
	  {
	    node = new HashNode();
	    walker.children.put(key, node);
	    walker = node;
	  }
      }

    walker.support = is.getSupport();
  }

  /**
   * Return the support for a given itemset.
   *
   * @param itemset   the itemset for which we want to obtain the support
   * @exception IllegalArgumentException   <code>itemset</code> is null
   * or is empty
   * @exception SETException   <code>itemset</code> not found in SET
   * @return   support
   */
  public float getSupport(Itemset itemset)
    throws SETException
  {
    if (itemset == null || itemset.size() == 0)
      throw new IllegalArgumentException("argument to getSupport() must be non null and non empty");

    Object obj;
    Itemset is = new Itemset(itemset);
    HashNode walker = root;
    Integer key;

    while (is.hasMoreItems())
      {
	key = new Integer(is.getNextItem());

	if ((obj = walker.children.get(key)) == null)
	  throw new SETException("itemset not found in SET!");
	
	walker = (HashNode)obj;
      }

    return walker.support;
  }

  /**
   * Return the maximal itemsets of the SET. 
   *
   * @return   a vector containing the maximal itemsets from the SET
   */
  public Vector getLargeItemsets() 
  { 
    Vector v = new Vector();

    if (!root.children.isEmpty())
      traverseGatherLeaves(root, new Itemset(), v);

    Itemset.pruneNonMaximal(v);

    return v;
  }

  /**
   * Return the itemsets of the SET. 
   *
   * @return   a vector containing the itemsets from the SET
   */
  public Vector getItemsets() 
  { 
    Vector v = new Vector();

    if (!root.children.isEmpty())
      traverseGatherAll(root, new Itemset(), v);

    return v;
  }

  /*
   * A private method which gets called recursively to retrieve itemsets 
   * from the leaf nodes of the SET.
   *
   * @param   node node starts from the root node of the SET. 
   * @param   itemset for storing hashtable keys as it traverses node by 
   * node to the leaf. 
   * @param   vector for storing itemsets . 
   */
  private void traverseGatherLeaves(HashNode node, Itemset itemset, 
				    Vector vector)
  {
    if (node.children.isEmpty())
      {
	Itemset is = new Itemset(itemset);
	is.setSupport(node.support);
	vector.addElement(is);
	return;
      }

    Enumeration e = node.children.keys();

    while (e.hasMoreElements())
      {
	Integer key = (Integer)e.nextElement();
	itemset.addItem(key.intValue());
	traverseGatherLeaves((HashNode)node.children.get(key), itemset, 
			     vector);
	itemset.removeLastItem();
      }
  }
  
  /*
   * A private method which gets called recursively to retrieve itemsets 
   * from all the nodes of the SET.
   *
   * @param   node node starts from the root node of the SET. 
   * @param   itemset for storing hashtable keys as it traverses node by 
   * node to the leaf. 
   * @param   vector for storing itemsets . 
   */
  private void traverseGatherAll(HashNode node, Itemset itemset, 
				 Vector vector)
  {
    if (node.children.isEmpty())
      return;

    Enumeration e = node.children.keys();

    while (e.hasMoreElements())
      {
	Integer key = (Integer)e.nextElement();
	HashNode child_node = (HashNode)node.children.get(key);
	itemset.addItem(key.intValue());

	Itemset is = new Itemset(itemset);
	is.setSupport(child_node.support);
	vector.addElement(is);

	traverseGatherAll(child_node, itemset, vector);
	itemset.removeLastItem();
      }
  }
  
  /*
   * A private method which gets called recursively to retrieve itemsets
   * from each node of the SET and print them to a String.
   *
   * @param node   node to traverse
   * @param s   StringBuffer in which we store the representation
   * of the nodes we saw so far
   */
  private void traversePrint(HashNode node, StringBuffer sb)
  {
    level++;

    if (node.children.isEmpty())
      {
	level--;
	return;
      }

    Enumeration e = node.children.keys();
    while (e.hasMoreElements())
      {
	Integer key = (Integer)e.nextElement();
	for (int i = 1; i < level; i++)
	  sb.append("   ");
	sb.append("<" + key.toString() + ">" + ":[" + level + "]\n");
	traversePrint((HashNode)node.children.get(key), sb);
      }

    level--;
  }
	
  /**
   * Return a string representation of the SET.
   *
   * @return   string representation of SET
   */
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    traversePrint(root, sb);
    return sb.toString();
  }
  
  /** 
   * for testing purposes only !!! 
   */
  public static void main(String[] args)
  {
    SET set = new SET();
    
    Itemset is2 = new Itemset();
    Itemset is1 = new Itemset();
    Itemset is3 = new Itemset();
    Itemset is4 = new Itemset();
    Itemset is5 = new Itemset();
    Itemset is6 = new Itemset();
    Itemset is7 = new Itemset();
    Itemset is8 = new Itemset();
    Itemset is9 = new Itemset();
    
    is1.addItem(1);
    is1.addItem(2);
    is1.addItem(3);
    is1.addItem(4);
    is1.setSupport((float)0.4);
    is2.addItem(1);
    is2.addItem(2);
    is2.addItem(3);
    is2.addItem(7);
    is2.setSupport((float)0.3);
    is3.addItem(1);
    is3.addItem(2);
    is3.addItem(3);
    is3.addItem(5);
    is3.addItem(6);
    is3.setSupport((float)0.5);
    is4.addItem(1);
    is4.addItem(2);
    is4.addItem(4);
    is4.setSupport((float)0.65);
    is5.addItem(1);
    is5.addItem(2);
    is5.addItem(5);
    is5.setSupport((float)0.6);
    is6.addItem(2);
    is6.addItem(4);
    is6.addItem(5);
    is6.setSupport((float)0.55);
    is7.addItem(1);
    is7.addItem(2);
    is7.setSupport((float)0.2);
    is8.addItem(2);
    is8.addItem(4);
    is8.addItem(7);
    is8.addItem(8);
    is8.setSupport((float)0.52);
    is9.addItem(2);
    is9.addItem(4);
    is9.addItem(6);
    is9.setSupport((float)0.59);
    
    set.insert(is2);
    set.insert(is3);
    set.insert(is1);
    set.insert(is4);
    set.insert(is5);		
    set.insert(is6);
    set.insert(is7);
    set.insert(is8);
    set.insert(is9);
    
    System.out.println(set);
    
    try
      {
	System.out.println("Support for is1: " + set.getSupport(is1));
	System.out.println("Support for is2: " + set.getSupport(is2));
	System.out.println("Support for is3: " + set.getSupport(is3));
	System.out.println("Support for is4: " + set.getSupport(is4));
	System.out.println("Support for is5: " + set.getSupport(is5));
	System.out.println("Support for is6: " + set.getSupport(is6));
	System.out.println("Support for is7: " + set.getSupport(is7));
	System.out.println("Support for is8: " + set.getSupport(is8));
	System.out.println("Support for is9: " + set.getSupport(is9));
      }
    catch (SETException e)
      {
	System.out.println(e);
      }

    Vector v = set.getLargeItemsets();
    System.out.println("\nLarge itemsets are: " + '\n');
    for(int i = 0; i < v.size(); i++)
      System.out.println(v.get(i));
  }
}
