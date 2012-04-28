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
   
   (P)1999-2001 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

import java.util.*;

/*

  Maintenance log started on April 3rd, 2001 by Laurentiu Cristofor

  Apr. 3rd, 2001    - modified the HashTree so that it can change
                      dynamically its HashNode size in order to
                      index large numbers of itemsets.

*/ 

/**

   HashTree.java<P>

   A HashTree is a special data structure that is used to index
   a Vector of Itemset objects for more efficient processing.
   
*/

public class HashTree
{
  private static final int LIST_NODE = 1;
  private static final int HASH_NODE = 2;

  private static final int DEFAULT_LIST_SIZE = 20;
  private static final int DEFAULT_HASH_SIZE = 40;

  private int LIST_SIZE;
  private int HASH_SIZE;

  private static class Node
  {
    public int type; // LIST_NODE or HASH_NODE
  }

  private class ListNode extends Node
  {
    public int[] indexes;   // index in Vector of Itemsets
    public int size;        // how many indexes we keep in above array
    public boolean visited; // have we seen this node?

    public ListNode()
    {
      type = LIST_NODE;
      indexes = new int[LIST_SIZE];
      size = 0;
      visited = false;
    }
  }

  private class HashNode extends Node
  {
    public MyHashtable children;

    public HashNode()
    {
      type = HASH_NODE;
      children = new MyHashtable(HASH_SIZE);
    }
  }

  private static class MyHashtable
  {
    public Node[] contents;

    public MyHashtable(int size)
    {
      contents = new Node[size];
    }

    public void put(int key, Node n)
    {
      int index = key % contents.length;
      contents[index] = n;
    }

    public Node get(int key)
    {
      int index = key % contents.length;
      return contents[index];
    }

    public Enumeration elements()
    {
      return new Enumeration()
	{
	  int i = 0;
	  public boolean hasMoreElements()
	  {
	    while (i < contents.length 
		   && contents[i] == null)
	      i++;

	    if (i >= contents.length)
	      return false;
	    else
	      return true;
	  }
	  public Object nextElement()
	  {
	    while (i < contents.length 
		   && contents[i] == null)
	      i++;

	    if (i >= contents.length)
	      throw new NoSuchElementException();
	    else
	      return contents[i++];
	  }
	};
    }
  }
  
  private int counter;     // used for some computations

  private Vector leaves;   // keeps all leaves of the HashTree
  private Vector itemsets; // the Vector of Itemsets that we index

  private Node theRoot;    // the root of the HashTree

  private void unvisitLeaves()
  {
    for (int i = 0; i < leaves.size(); i++)
      ((ListNode)leaves.get(i)).visited = false;
  }

  /**
   * Create a new HashTree. The <code>listSize</code> parameter determines
   * after how many inserts in a ListNode we have to change it to a 
   * HashNode (i.e. perform a split). The <code>hashSize</code> parameter
   * can be specified to improve the efficiency of the structure.
   *
   * @param listSize   the size of the internal lists in the list nodes
   * @param hashSize   the size of the internal hashtables in the hash nodes
   * @param itemsets   the Vector of Itemsets that we should index
   * @exception IllegalArgumentException   <code>itemsets</code> is null
   * or <code>listSize <= 0</code> or <code>hashSize <= 0</code>  
   */
  public HashTree(int listSize, int hashSize, Vector itemsets)
  {
    if (itemsets == null || listSize <= 0 || hashSize <= 0)
      throw new IllegalArgumentException("invalid arguments to constructor");

    LIST_SIZE = listSize;
    HASH_SIZE = hashSize;
    this.itemsets = itemsets;

    theRoot = new ListNode();
    leaves = new Vector();
  }

  /**
   * Create a new HashTree. This initializes the HashTree with
   * default parameters.
   *
   * @param itemsets   the Vector of Itemsets that we should index
   * @exception IllegalArgumentException   <code>itemsets</code> is null
   */
  public HashTree(Vector itemsets)
  {
    this(DEFAULT_LIST_SIZE, DEFAULT_HASH_SIZE, itemsets);
  }

  /**
   * This method should be called before calling update() to gather
   * all leaves of the HashTree for more efficient processing.
   */
  public void prepareForDescent()
  {
    leaves.clear();
    prepare(theRoot);
  }

  // private recursive method
  private void prepare(Node node)
  {
    if (node.type == HASH_NODE)
      {
	Enumeration e = ((HashNode)node).children.elements();
	while (e.hasMoreElements())
	  prepare((Node)e.nextElement());
      }
    else // LIST_NODE
      leaves.add(node);
  }

  // reset HashTree and readd all itemsets added previously
  // this method may be called by add()
  private void readdAll()
  {
    // increase size of hash nodes
    HASH_SIZE = 2 * HASH_SIZE + 1;

    // first save leaf nodes
    prepareForDescent();

    // then reset HashTree
    theRoot = new ListNode();

    // readd everything that we added before
    for (int i = 0; i < leaves.size(); i++)
      {
	ListNode ln = (ListNode)leaves.get(i);
	for (int j = 0; j < ln.size; j++)
	  add(ln.indexes[j]);
      }

    // clean up
    leaves.clear();
  }

  private static class HashTreeOverflowException extends RuntimeException
  {
  }

  /**
   * This method indexes in the HashTree the Itemset at index
   * <code>index</code> from Vector <code>itemsets</code> which
   * was passed to the constructor of this HashTree.
   *
   * @param index   the index of the Itemset that we need to index in
   * this HashTree.
   */
  public void add(int index)
  {
    // repeat the operation if it results in a HashTree overflow
    while (true)
      try 
	{
	  theRoot = add(theRoot, 0, index);

	  // if we get here then the add() was successful and we can
	  // exit the loop
	  break;
	}
      catch (HashTreeOverflowException e)
	{
	  // call readdAll to increase the size of hash nodes 
	  // and readd all itemsets that were previously added
	  readdAll();
	}
  }

  // private recursive method
  private Node add(Node node, int level, int index)
  {
    if (node.type == LIST_NODE)
      {
	ListNode ln = (ListNode)node;

	if (ln.size == LIST_SIZE) // list is full
	  {
	    // if the level is equal to the itemsets size and we
	    // filled the list node, then we overflowed the HashTree.
	    if (((Itemset)itemsets.get(index)).size() == level)
	      throw new HashTreeOverflowException();

	    // else, must split!
	    HashNode hn = new HashNode();

	    // hash the list elements
	    for (int i = 0; i < LIST_SIZE; i++)
	      add(hn, level, ln.indexes[i]);

	    // add our node
	    add(hn, level, index);

	    // return this HashNode to replace old ListNode
	    return hn;
	  }
	else // append index at end of list
	  {
	    ln.indexes[ln.size++] = index;
	  }
      }
    else // HASH_NODE
      {
	HashNode hn = (HashNode)node;

	// compute hash key
	Itemset is = (Itemset)itemsets.get(index);
	int key = is.getItem(level);

	// try to get next node
	Node n = hn.children.get(key);
	if (n == null) // no node, must create a new ListNode
	  {
	    ListNode ln = new ListNode();
	    ln.indexes[ln.size++] = index; 
	    hn.children.put(key, ln);
	  }
	else // found a node, do a recursive call
	  {
	    n = add(n, level + 1, index);
	    hn.children.put(key, n);
	  }
      }

    return node;
  }

  /**
   * Update the weights of all indexed Itemsets that are included
   * in <code>row</code>
   *
   * @param row   the Itemset (normally a database row) against which 
   * we test for inclusion
   */
  public void update(Itemset row)
  {
    update(theRoot, row, 0);
    unvisitLeaves();
  }

  // private recursive method
  private void update(Node node, Itemset row, int index)
  {
    if (node.type == LIST_NODE)
      {
	ListNode ln = (ListNode)node;

	if (ln.visited)
	  return;

	for (int i = 0; i < ln.size; i++)
	  {
	    Itemset is = (Itemset)itemsets.get(ln.indexes[i]);
	    if (is.isIncludedIn(row))
	      is.incrementWeight();
	  }

	ln.visited = true; // now we've seen this node
      }
    else // HASH_NODE
      {
	HashNode hn = (HashNode)node;

	// this is a tricky piece of algorithm that ensures we
	// look for all possible subsets of row
	for (int i = index; i < row.size(); i++)
	  {
	    int key = row.getItem(i);
	    Node n = hn.children.get(key);
	    if (n != null)
	      update(n, row, i + 1);
	  }
      }
  }

  /**
   * Update the weights of all indexed Itemsets that are included
   * in <code>row</code> and also update the matrix <code>counts</code>
   *
   * @param row   the Itemset (normally a database row) against which 
   * we test for inclusion
   * @param counts   a matrix used by some algorithms to speed up
   * computations; its rows correspond to Itemsets and its columns
   * correspond to items; each value in the matrix tells for how many
   * times had an item appeared together with an itemset in the rows
   * of the database.
   */
  public void update(Itemset row, long[][] counts)
  {
    update(theRoot, row, 0, counts);
    unvisitLeaves();
  }

  // private recursive method
  private void update(Node node, Itemset row, int index, long[][] counts)
  {
    if (node.type == LIST_NODE)
      {
	ListNode ln = (ListNode)node;

	if (ln.visited)
	  return;

	for (int i = 0; i < ln.size; i++)
	  {
	    Itemset is = (Itemset)itemsets.get(ln.indexes[i]);
	    if (is.isIncludedIn(row))
	      {
		is.incrementWeight();

		for (int j = 0; j < row.size(); j++)
		  counts[ln.indexes[i]][row.getItem(j) - 1]++;
	      }
	  }

	ln.visited = true; // now we've seen this node
      }
    else // HASH_NODE
      {
	HashNode hn = (HashNode)node;

	// this is a tricky piece of algorithm that ensures we
	// look for all possible subsets of row
	for (int i = index; i < row.size(); i++)
	  {
	    int key = row.getItem(i);
	    Node n = hn.children.get(key);
	    if (n != null)
	      update(n, row, i + 1, counts);
	  }
      }
  }

  /**
   * Count how many frequent Itemsets (frequent = having weight 
   * greater than a specified minimum weight) are included in 
   * <code>itemset</code>
   *
   * @param itemset   the Itemset for which we count the subsets
   * @param minWeight   the minimum weight
   */
  public long countFrequentSubsets(Itemset itemset, long minWeight)
  {
    counter = 0;
    countFrequentSubsets(theRoot, itemset, 0, minWeight);
    unvisitLeaves();
    return counter;
  }

  // private recursive method
  private void countFrequentSubsets(Node node, Itemset itemset,
				    int index, long minWeight)
  {
    if (node.type == LIST_NODE)
      {
	ListNode ln = (ListNode)node;

	if (ln.visited)
	  return;

	for (int i = 0; i < ln.size; i++)
	  {
	    Itemset is = (Itemset)itemsets.get(ln.indexes[i]);
	    if (is.isIncludedIn(itemset) && is.getWeight() >= minWeight)
	      counter++;
	  }

	ln.visited = true; // now we've seen this node
      }
    else // HASH_NODE
      {
	HashNode hn = (HashNode)node;

	// this is a tricky piece of algorithm that ensures we
	// look for all possible subsets of row
	for (int i = index; i < itemset.size(); i++)
	  {
	    int key = itemset.getItem(i);
	    Node n = hn.children.get(key);
	    if (n != null)
	      countFrequentSubsets(n, itemset, i + 1, minWeight);
	  }
      }
  }

  /**
   * Count how many Itemsets are included in <code>itemset</code>
   *
   * @param itemset   the Itemset for which we count the subsets
   */
  public long countSubsets(Itemset itemset)
  {
    counter = 0;
    countSubsets(theRoot, itemset, 0);
    unvisitLeaves();
    return counter;
  }

  // private recursive method
  private void countSubsets(Node node, Itemset itemset, int index)
  {
    if (node.type == LIST_NODE)
      {
	ListNode ln = (ListNode)node;

	if (ln.visited)
	  return;

	for (int i = 0; i < ln.size; i++)
	  {
	    Itemset is = (Itemset)itemsets.get(ln.indexes[i]);
	    if (is.isIncludedIn(itemset))
	      counter++;
	  }

	ln.visited = true; // now we've seen this node
      }
    else // HASH_NODE
      {
	HashNode hn = (HashNode)node;

	// this is a tricky piece of algorithm that ensures we
	// look for all possible subsets of row
	for (int i = index; i < itemset.size(); i++)
	  {
	    int key = itemset.getItem(i);
	    Node n = hn.children.get(key);
	    if (n != null)
	      countSubsets(n, itemset, i + 1);
	  }
      }
  }

  /**
   * Verifies if any of the indexed Itemsets is not large by checking
   * whether they're included in the frequent itemset <code>itemset</code>.
   * If an Itemset is not large then it will be marked.
   *
   * @param itemset   the Itemset we check
   */
  public void checkLargeness(Itemset itemset)
  {
    checkLargeness(theRoot, itemset, 0);
    unvisitLeaves();
  }

  // private recursive method
  private void checkLargeness(Node node, Itemset itemset, int index)
  {
    if (node.type == LIST_NODE)
      {
	ListNode ln = (ListNode)node;

	if (ln.visited)
	  return;

	for (int i = 0; i < ln.size; i++)
	  {
	    Itemset is = (Itemset)itemsets.get(ln.indexes[i]);
	    if (is.isIncludedIn(itemset))
	      is.mark();
	  }

	ln.visited = true; // now we've seen this node
      }
    else // HASH_NODE
      {
	HashNode hn = (HashNode)node;

	// this is a tricky piece of algorithm that ensures we
	// look for all possible subsets of row
	for (int i = index; i < itemset.size(); i++)
	  {
	    int key = itemset.getItem(i);
	    Node n = hn.children.get(key);
	    if (n != null)
	      checkLargeness(n, itemset, i + 1);
	  }
      }
  }
}
