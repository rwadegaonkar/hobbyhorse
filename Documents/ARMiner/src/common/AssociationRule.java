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

/**

   AssociationRule.java<P>

   An association rule has two parts: the antecedent of the rule
   and the consequent of the rule, both of which are sets of items.
   Associated with these are a support and a confidence. The support
   tells how many rows of a database support this rule, the 
   confidence tells what percentage of the rows that contain the
   antecedent also contain the consequent.

*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public class AssociationRule implements java.io.Serializable
{
  public static final int ANTECEDENT_SIZE = 1;
  public static final int CONSEQUENT_SIZE = 2;
  public static final int SUPPORT         = 3;
  public static final int CONFIDENCE      = 4;

  /** 
   * The antecedent.
   *
   * @serial
   */
  private int[] antecedent;

  /** 
   * The consequent.
   *
   * @serial
   */
  private int[] consequent;

  /** 
   * The support of the association rule.
   *
   * @serial
   */
  private float support;

  /** 
   * The confidence of the association rule.
   *
   * @serial
   */
  private float confidence;

  /**
   * Creates a new association rule.
   *
   * @param antecedent   the antecedent of the association rule
   * @param consequent   the consequent of the association rule
   * @param support   the support of the association rule
   * @param confidence   the confidence of the association rule
   * @exception IllegalArgumentException   <code>antecedent</code> 
   * or <code>consequent</code> are null or <code>support</code>
   * or <code>confidence</code> are not between 0 and 1
   */
  public AssociationRule(Itemset antecedent, Itemset consequent,
			 float support, float confidence)
  {
    if (antecedent == null || consequent == null
	|| support < 0 || support > 1
	|| confidence < 0 || confidence > 1)
      throw new IllegalArgumentException("constructor requires itemsets as arguments");

    this.antecedent = new int[antecedent.size()];
    for (int i = 0; i < antecedent.size(); i++)
      this.antecedent[i] = antecedent.getItem(i);

    this.consequent = new int[consequent.size()];
    for (int i = 0; i < consequent.size(); i++)
      this.consequent[i] = consequent.getItem(i);

    this.support = support;

    this.confidence = confidence;
  }

  /**
   * Return size of antecedent.
   *
   * @return   size of antecedent
   */
  public int antecedentSize()
  {
    return antecedent.length;
  }

  /**
   * Return size of consequent.
   *
   * @return   size of consequent
   */
  public int consequentSize()
  {
    return consequent.length;
  }

  /**
   * Return support of association rule.
   */
  public float getSupport()
  {
    return support;
  }

  /**
   * Return confidence of association rule.
   */
  public float getConfidence()
  {
    return confidence;
  }

  /**
   * Return i-th item in antecedent.
   *
   * @param i   the index of the item to get
   * @exception IndexOutOfBoundsException   <code>i</code> is an invalid index
   * @return   the <code>i</code>-th item in antecedent
   */
  public int getAntecedentItem(int i)
  {
    if (i < 0 || i >= antecedent.length)
      throw new IndexOutOfBoundsException("invalid index");

    return antecedent[i];
  }
  
  /**
   * Return i-th item in consequent.
   *
   * @param i   the index of the item to get
   * @exception IndexOutOfBoundsException   <code>i</code> is an invalid index
   * @return   the <code>i</code>-th item in consequent
   */
  public int getConsequentItem(int i)
  {
    if (i < 0 || i >= consequent.length)
      throw new IndexOutOfBoundsException("invalid index");

    return consequent[i];
  }

  /**
   * Compare two AssociationRule objects on one of several criteria.
   *
   * @param ar   the AssociationRule object with which we want to
   * compare this object
   * @param criteria   the criteria on which we want to compare, can 
   * be one of ANTECEDENT_SIZE, CONSEQUENT_SIZE, SUPPORT or CONFIDENCE.
   * @exception IllegalArgumentException   <code>ar</code> is null
   * or criteria is invalid
   * @return   a negative value if this object is smaller than 
   * <code>ar</code>, 0 if they are equal, and a positive value if this
   * object is greater.
   */
  public int compareTo(AssociationRule ar, int criteria)
  {
    if (ar == null)
      throw new IllegalArgumentException("method requires association rule as argument");

    float diff;

    if (criteria == ANTECEDENT_SIZE)
      return this.antecedent.length - ar.antecedent.length;
    else if (criteria == CONSEQUENT_SIZE)
      return this.consequent.length - ar.consequent.length;
    else if (criteria == SUPPORT)
      diff = this.support - ar.support;
    else if (criteria == CONFIDENCE)
      diff = this.confidence - ar.confidence;
    else
      throw new IllegalArgumentException("invalid criteria");

    if (diff < 0)
      return -1;
    else if (diff > 0)
      return 1;
    else 
      return 0;
  }

  /**
   * Compare two AssociationRule objects on one of several criteria.
   *
   * @param ar   the AssociationRule object with which we want to
   * compare this object
   * @param criteria   the criteria on which we want to compare, can 
   * be one of ANTECEDENT_SIZE, CONSEQUENT_SIZE, SUPPORT or CONFIDENCE.
   * @return   true if the objects are equal in terms of antecedent
   * and consequent items; false otherwise.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof AssociationRule) || obj == null)
      return false;

    AssociationRule other = (AssociationRule)obj;

    if (antecedent.length != other.antecedent.length)
      return false;

    if (consequent.length != other.consequent.length)
      return false;

    for (int i = 0; i < antecedent.length; i++)
      if (antecedent[i] != other.antecedent[i])
	return false;

    for (int i = 0; i < consequent.length; i++)
      if (consequent[i] != other.consequent[i])
	return false;

    return true;
  }

  /**
   * Return a String representation of the AssociationRule.
   *
   * @return   String representation of AssociationRule
   */
  public String toString()
  {
    String s = "{";

    for (int i = 0; i < antecedent.length; i++)
      s += antecedent[i] + " ";
    s += "}->{";

    for (int i = 0; i < consequent.length; i++)
      s += consequent[i] + " ";
    s += "} (" + support + ", " + confidence + ")";

    return s;
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

    System.out.println("is1: " + is1);

    is2.addItem(12);
    is2.addItem(5);
    is2.addItem(8);

    System.out.println("is2: " + is2);

    AssociationRule ar = new AssociationRule(is1, is2, 
					     (float)0.5055, 
					     (float)0.3033); 

    System.out.println("ar: " + ar);
  }
}
