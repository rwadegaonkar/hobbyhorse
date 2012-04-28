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

import java.util.Vector;

/*

  Maintenance log started on November 30th, 2000

  Dec. 5th, 2001    - fixed an error in add method

  Nov. 30th, 2000   - added pruneDuplicates method
                    - added doesIntersect method

*/

/**

   Itemset.java<P>

   An itemset is an ordered list of integers that identify items
   coupled with a float value representing the support of the itemset
   as a percentage.<P>
   
*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public class Itemset implements java.io.Serializable
{
  private static final int SIZE_INCR = 7;

  /** 
   * The capacity of the itemset.
   *
   * @serial
   */
  private int capacity;

  /** 
   * The number of items in the itemset.
   *
   * @serial
   */
  private int size;

  /** 
   * The itemset.
   *
   * @serial
   */
  private int[] set;

  /** 
   * The support of the itemset.
   *
   * @serial
   */
  private float support;

  /** 
   * The weight of the itemset.
   *
   * @serial
   */
  private long weight;

  /** 
   * The mark of the itemset.
   *
   * @serial
   */
  private boolean mark; // this can be used to mark the itemset for
  // various purposes

  /** 
   * Internal index used for cycling through the itemset's items.
   *
   * @serial
   */
  private int index;

  /**
   * Creates a new empty itemset.
   */
  public Itemset()
  {
    capacity = SIZE_INCR;
    set = new int[capacity];
    size = 0;
    support = 0;
    weight = 0;
    mark = false;
    index = 0;
  }

  /**
   * Create a new empty itemset of specified capacity.
   *
   * @param c   the capacity of the itemset
   * @exception IllegalArgumentException   <code>c</code> is negative or zero
   */
  public Itemset(int c)
  {
    if (c < 1)
      throw new IllegalArgumentException("constructor requires positive argument value");

    capacity = c;
    set = new int[capacity];
    size = 0;
    support = 0;
    weight = 0;
    mark = false;
    index = 0;
  }

  /**
   * Create a new itemset by copying a given one.
   *
   * @param itemset   the itemset to be copied
   * @exception IllegalArgumentException   <code>itemset</code> is null
   */
  public Itemset(Itemset itemset)
  {
    if (itemset == null)
      throw new IllegalArgumentException("constructor requires an itemset as argument");

    capacity = itemset.capacity;
    set = new int[capacity];
    size = itemset.size;
    support = itemset.support;
    weight = itemset.weight;
    mark = itemset.mark;
    for (int i = 0; i < size; i++)
      set[i] = itemset.set[i];
    index = 0;
  }

  /**
   * Return support of itemset.
   */
  public float getSupport()
  {
    return support;
  }

  /**
   * Return weight of itemset.
   */
  public long getWeight()
  {
    return weight;
  }

  /**
   * Return i-th item in set.
   *
   * @param i   the index of the item to get
   * @exception IndexOutOfBoundsException   <code>i</code> is an invalid index
   * @return   the <code>i</code>-th item
   */
  public int getItem(int i)
  {
    if (i < 0 || i >= size)
      throw new IndexOutOfBoundsException("invalid index");

    return set[i];
  }

  /**
   * Return first item in set.
   *
   * @exception IndexOutOfBoundsException   there is no first item
   * @return   first item
   */
  public int getFirstItem()
  {
    index = 0;

    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("no first item");

    return set[index++];
  }

  /**
   * Return next item in set.
   *
   * @exception IndexOutOfBoundsException   there is no next item
   * @return   next item
   */
  public int getNextItem()
  {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("no next item");

    return set[index++];
  }

  /**
   * Return true if there are more items in the itemset. You can call
   * this method to find out whether you can call getNext without
   * raising an exception.
   *
   * @return   true if there are more items, false if not
   */
  public boolean hasMoreItems() 
  { 
    if (index < 0 || index >= size) 
      return false; 
    else 
      return true; 
  }
  
  /**
   * Return size of itemset.
   *
   * @return   size of itemset
   */
  public int size()
  {
    return size;
  }

  /**
   * Return true if this itemset has items in common
   * with <code>itemset</code>.
   *
   * @param itemset   the itemset with which we compare
   * @exception IllegalArgumentException   <code>itemset</code> is null
   * @return   true if <code>itemset</code> contains items of this 
   * itemset, false otherwise.
   */
  public boolean doesIntersect(Itemset itemset)
  {
    if (itemset == null)
      throw new IllegalArgumentException("subtract() requires an itemset as argument");

    Itemset result = new Itemset(capacity);
    int i = 0;
    int j = 0;
    for ( ; i < size && j < itemset.size; )
      {
	// if elements are equal, return true
	if (set[i] == itemset.set[j])
	  return true;
	// if the element in this Itemset is bigger then
	// we need to move to the next item in itemset.
	else if (set[i] > itemset.set[j])
	  j++;
	// the element in this Itemset does not appear
	// in itemset so we need to add it to result
	else
	  i++;
      }

    return false;
  }

  /**
   * Return a new Itemset that contains only those items that do not
   * appear in <code>itemset</code>.
   *
   * @param itemset   the itemset whose items we want to subtract
   * @exception IllegalArgumentException   <code>itemset</code> is null
   * @return   an Itemset containing only those items of this Itemset that
   * do not appear in <code>itemset</code>.
   */
  public Itemset subtract(Itemset itemset)
  {
    if (itemset == null)
      throw new IllegalArgumentException("subtract() requires an itemset as argument");

    Itemset result = new Itemset(capacity);
    int i = 0;
    int j = 0;
    for ( ; i < size && j < itemset.size; )
      {
	// if elements are equal, move to next ones
	if (set[i] == itemset.set[j])
	  {
	    i++;
	    j++;
	  }
	// if the element in this Itemset is bigger then
	// we need to move to the next item in itemset.
	else if (set[i] > itemset.set[j])
	  j++;
	// the element in this Itemset does not appear
	// in itemset so we need to add it to result
	else
	  result.set[result.size++] = set[i++];
      }

    // copy any remaining items from this Itemset 
    while (i < size)
      result.set[result.size++] = set[i++];

    // NOTE: the size of the resulting itemset
    // has been automatically updated
    return result;
  }

  /**
   * Return a new Itemset that contains all those items that appear
   * in this Itemset and in <code>itemset</code>.
   *
   * @param itemset   the itemset whose items we want to add
   * @exception IllegalArgumentException   <code>itemset</code> is null
   * @return   an Itemset containing all those items that appear
   * in this Itemset and in <code>itemset</code>.
   */
  public Itemset add(Itemset itemset)
  {
    if (itemset == null)
      throw new IllegalArgumentException("add() requires an itemset as argument");

    Itemset result = new Itemset(capacity);
    int i = 0;
    int j = 0;
    for ( ; i < size && j < itemset.size; )
      {
	// if elements are equal, copy then move to next ones
	if (set[i] == itemset.set[j])
	  {
	    result.set[result.size++] = set[i++];
	    j++;
	  }
	// if the element in this Itemset is bigger then
	// we need to copy from itemset then move to the next item.
	else if (set[i] > itemset.set[j])
	  result.set[result.size++] = itemset.set[j++];
	// else we need to copy from this Itemset
	else
	  result.set[result.size++] = set[i++];
      }

    // copy any remaining items from this Itemset 
    while (i < size)
      result.set[result.size++] = set[i++];
    // copy any remaining items from itemset 
    while (j < itemset.size)
      result.set[result.size++] = itemset.set[j++];

    // NOTE: the size of the resulting itemset
    // has been automatically updated
    return result;
  }

  /**
   * Add a new item to the itemset.
   *
   * @param item   the item to be added
   * @exception IllegalArgumentException   <code>item</code> is <= 0
   * @return   true if item was added, false if it wasn't added (was
   * already there!)
   */
  public boolean addItem(int item)
  {
    if (item <= 0)
      throw new IllegalArgumentException("negative or zero value for item not allowed");

    if (size == 0)
      set[0] = item;
    else
      {
	// look for place to insert item
	int index;
	for (index = 0; index < size && item > set[index]; index++)
	  ;

	// if item was already in itemset then return
	if (index < size && item == set[index])
	  return false;

	// if set is full then allocate new array
	if (size == capacity)
	  {
	    capacity = size + SIZE_INCR;
	    int[] a = new int[capacity];

	    int i;
	    for (i = 0; i < index; i++)
	      a[i] = set[i];
	    a[i] = item;
	    for ( ; i < size; i++)
	      a[i + 1] = set[i];

	    set = a;
	  }
	// otherwise make place and insert new item
	else
	  {
	    int i;
	    for (i = size; i > index; i--)
	      set[i] = set[i - 1];

	    set[i] = item;
	  }
      }

    // update size
    size++;
    return true;
  }

  /**
   * Removes a given item from the itemset.
   *
   * @param item   the item to remove
   * @exception IllegalArgumentException   <code>item</code> is <= 0
   * @return   true if item was removed, false if it wasn't removed (was
   * not found in itemset!)
   */
  public boolean removeItem(int item)
  {
    if (item <= 0)
      throw new IllegalArgumentException("negative or zero value for item not allowed");

    int index;
    for (index = 0; index < size && item != set[index] ; index++)
      ;

    if (item == set[index])
      {
	for (++index; index < size; index++)
	  set[index - 1] = set[index];
	size--;
	return true;
      }
    else 
      return false;
  }

  /**
   * Removes last item (which has the greatest value) from the itemset. 
   *
   * @return   true if item was removed, false if it wasn't removed (the
   * itemset was empty)
   */
  public boolean removeLastItem()
  {
    if (size > 0)
      {
	size--;
	return true;
      }
    else
      return false;
  }

  /**
   * Set the support of the itemset.
   *
   * @param newSupport   the support of the itemset
   * @exception IllegalArgumentException   <code>newSupport</code> is < 0
   * or > 100
   */
  public void setSupport(float newSupport)
  {
    if (newSupport < 0 || newSupport > 1)
      throw new IllegalArgumentException("support must be between 0 and 1");

    support = newSupport;
  }

  /**
   * Set the weight of the itemset.
   *
   * @param newWeight   the weight of the itemset
   * @exception IllegalArgumentException   <code>newWeight</code> is < 0
   */
  public void setWeight(long newWeight)
  {
    if (newWeight < 0)
      throw new IllegalArgumentException("weight must be positive");

    weight = newWeight;
  }

  /**
   * Increment the weight of the itemset.
   */
  public void incrementWeight()
  {
    weight++;
  }

  /**
   * Checks equality with a given itemset.
   *
   * @param itemset   the itemset against which we test for equality
   * @exception IllegalArgumentException   <code>itemset</code> is null
   */
  public boolean isEqualTo(Itemset itemset)
  {
    if (itemset == null)
      throw new IllegalArgumentException("itemset required as argument");

    if (size != itemset.size())
      return false;

    for (int i = 0; i < size; i++)
      if (set[i] != itemset.set[i])
	return false;

    return true;
  }

  /**
   * Checks inclusion in a given itemset.
   *
   * @param itemset   the itemset against which we test for inclusion
   * @exception IllegalArgumentException   <code>itemset</code> is null
   */
  public boolean isIncludedIn(Itemset itemset)
  {
    if (itemset == null)
      throw new IllegalArgumentException("itemset required as argument");

    if (itemset.size() < size)
      return false;

    int i, j;

    for (i = 0, j = 0; 
	 i < size && j < itemset.size() && set[i] >= itemset.set[j]; 
	 j++)
      if (set[i] == itemset.set[j])
	i++;

    if (i == size)
      return true;
    else 
      return false;
  }

  /**
   * Mark the itemset.
   *
   * @return   true if itemset was already marked, false otherwise
   */
  public boolean mark()
  {
    boolean old_mark = mark;
    mark = true;
    return old_mark;
  }

  /**
   * Unmark the itemset.
   *
   * @return   true if itemset was marked, false otherwise
   */
  public boolean unmark()
  {
    boolean old_mark = mark;
    mark = false;
    return old_mark;
  }

  /**
   * Return itemset mark.
   *
   * @return   true if itemset is marked, false otherwise
   */
  public boolean isMarked()
  {
    return mark;
  }

  /**
   * Return a String representation of the Itemset.
   *
   * @return   String representation of Itemset
   */
  public String toString()
  {
    String s = "{";

    for (int i = 0; i < size; i++)
      s += set[i] + " ";
    s += "}/[" + support + "/" + weight + "] (" + size + ")";

    return s;
  }

  /**
   * Check whether two itemsets can be combined. Two itemsets can be
   * combined if they differ only in the last item.
   *
   * @param itemset   itemset with which to combine
   * @exception IllegalArgumentException   <code>itemset</code> is null
   * @return   true if the itemsets can be combined, false otherwise
   */
  public boolean canCombineWith(Itemset itemset)
  {
    if (itemset == null)
      throw new IllegalArgumentException("itemset required as argument");

    if (size != itemset.size)
      return false;

    if (size == 0)
      return false;

    for (int i = 0; i < size - 1; i++)
      if (set[i] != itemset.set[i])
	return false;

    return true;
  }

  /**
   * Combine two itemsets into a new one that will contain all the
   * items in the first itemset plus the last item in the second
   * itemset.
   *
   * @param itemset   itemset with which to combine
   * @exception IllegalArgumentException   <code>itemset</code> is null
   * @return   an itemset that combines the two itemsets as described
   * above 
   */
  public Itemset combineWith(Itemset itemset)
  {
    if (itemset == null)
      throw new IllegalArgumentException("itemset required as argument");

    Itemset is = new Itemset(this);
    is.support = 0;
    is.weight = 0;

    is.addItem(itemset.set[itemset.size - 1]);

    return is;
  }

  /**
   * Remove all non-maximal itemsets from the vector v
   *
   * @param v   the collection of itemsets
   */
  public static synchronized void pruneNonMaximal(Vector v)
  {
    int i, j;
    int size = v.size();

    for (i = 0; i < size; i++)
      {
	// see if anything is included in itemset at index i
	for (j = i + 1; j < size; j++)
	  if (((Itemset)v.get(j)).isIncludedIn((Itemset)v.get(i)))
	      {
		// replace this element with last, delete last,
		// and don't advance index
		v.set(j, v.lastElement());
		v.remove(--size);
		j--;
	      }

	// see if itemset at index i is included in another itemset
	for (j = i + 1; j < size; j++)
	  if (((Itemset)v.get(i)).isIncludedIn((Itemset)v.get(j)))
	      {
		// replace this element with last, delete last,
		// and don't advance index
		v.set(i, v.lastElement());
		v.remove(--size);
		i--;
		break;
	      }
      }
  }

  /**
   * Remove all duplicate itemsets from the vector v
   *
   * @param v   the collection of itemsets
   */
  public static synchronized void pruneDuplicates(Vector v)
  {
    int i, j;
    int size = v.size();

    for (i = 0; i < size; i++)
      {
	// see if anything is equal to itemset at index i
	for (j = i + 1; j < size; j++)
	  if (((Itemset)v.get(j)).isEqualTo((Itemset)v.get(i)))
	      {
		// replace this element with last, delete last,
		// and don't advance index
		v.set(j, v.lastElement());
		v.remove(--size);
		j--;
	      }
      }
  }

  /** 
   * for testing purposes only !!! 
   */
  public static void main(String[] args)
  {
    Itemset is1 = new Itemset();
    Itemset is2 = new Itemset();

    is1.addItem(7);
    is1.addItem(3);
    is1.addItem(15);
    is1.addItem(5);
    is1.addItem(12);
    is1.addItem(12);

    System.out.println("is1: " + is1);

    is2.addItem(12);
    is2.addItem(15);
    is2.addItem(7);
    is2.addItem(5);
    is2.addItem(3);
    is2.addItem(8);

    System.out.println("is2: " + is2);

    System.out.println("do is1 and is2 share items: " 
		       + is1.doesIntersect(is2));
    System.out.println("do is2 and is1 share items: " 
		       + is2.doesIntersect(is1));

    Itemset is3 = is1.subtract(is2);
    System.out.println("is3 <= subtracting is2 from is1:" + is3);

    System.out.println("do is1 and is3 share items: " 
		       + is1.doesIntersect(is3));
    System.out.println("do is3 and is1 share items: " 
		       + is3.doesIntersect(is1));

    is3 = is2.subtract(is1);
    System.out.println("is3 <= subtracting is1 from is2:" + is3);

    System.out.println("do is1 and is3 share items: " 
		       + is1.doesIntersect(is3));
    System.out.println("do is3 and is1 share items: " 
		       + is3.doesIntersect(is1));

    System.out.println("do is3 and is2 share items: " 
		       + is3.doesIntersect(is2));
    System.out.println("do is2 and is3 share items: " 
		       + is2.doesIntersect(is3));

    System.out.println("adding is2 to is1:" + is1.add(is2));
    System.out.println("adding is1 to is2:" + is2.add(is1));

    System.out.println("is1 equal to is2: " + is1.isEqualTo(is2));
    System.out.println("is1 included in is2: " + is1.isIncludedIn(is2));
    System.out.println("is2 included in is1: " + is2.isIncludedIn(is1));

    is1.addItem(8);

    System.out.println("is1: " + is1);

    System.out.println("is1 equal to is2: " + is1.isEqualTo(is2));
    System.out.println("is1 included in is2: " + is1.isIncludedIn(is2));
    System.out.println("is2 included in is1: " + is2.isIncludedIn(is1));

    is1.addItem(1);

    System.out.println("is1: " + is1);

    System.out.println("is1 equal to is2: " + is1.isEqualTo(is2));
    System.out.println("is1 included in is2: " + is1.isIncludedIn(is2));
    System.out.println("is2 included in is1: " + is2.isIncludedIn(is1));

    is1.addItem(50);

    System.out.println("is1: " + is1);

    System.out.println("is1 equal to is2: " + is1.isEqualTo(is2));
    System.out.println("is1 included in is2: " + is1.isIncludedIn(is2));
    System.out.println("is2 included in is1: " + is2.isIncludedIn(is1));

    is1.addItem(100);

    System.out.println("is1: " + is1);

    System.out.println("is1 equal to is2: " + is1.isEqualTo(is2));
    System.out.println("is1 included in is2: " + is1.isIncludedIn(is2));
    System.out.println("is2 included in is1: " + is2.isIncludedIn(is1));

    System.out.println("adding 70 to is2: " + is2.addItem(70));
    System.out.println("adding 70 to is2: " + is2.addItem(70));

    System.out.println("is2: " + is2);

    System.out.println("is1 equal to is2: " + is1.isEqualTo(is2));
    System.out.println("is1 included in is2: " + is1.isIncludedIn(is2));
    System.out.println("is2 included in is1: " + is2.isIncludedIn(is1));

    System.out.println("removing 1 from is1: " + is1.removeItem(1));
    System.out.println("removing 1 from is1: " + is1.removeItem(1));
    System.out.println("is1: " + is1);
    System.out.println("removing 50 from is1: " + is1.removeItem(50));
    System.out.println("is1: " + is1);
    System.out.println("removing 70 from is1: " + is2.removeItem(70));
    System.out.println("is2: " + is2);

    System.out.print("going through items of is1:");
    while (is1.hasMoreItems())
      System.out.print(" " + is1.getNextItem());
    System.out.println("");

    System.out.print("going through items of is2:");
    while (is2.hasMoreItems())
      System.out.print(" " + is2.getNextItem());
    System.out.println("");

    System.out.println("is1 first item: " + is1.getFirstItem());
    System.out.println("is1 next item: " + is1.getNextItem());
    System.out.println("is2 first item: " + is2.getFirstItem());
    System.out.println("is2 next item: " + is2.getNextItem());

    while (is2.removeLastItem())
      ;
    System.out.println("is2: " + is2);

    System.out.println("mark is1, previous state: " + is1.mark());
    System.out.println("mark is1, previous state: " + is1.mark());
    System.out.println("is1 mark state: " + is1.isMarked());
    System.out.println("unmark is1, previous state: " + is1.unmark());
    System.out.println("unmark is1, previous state: " + is1.unmark());
  }
}
