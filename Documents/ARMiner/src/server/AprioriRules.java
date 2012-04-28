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
import java.io.IOException;
import java.io.EOFException;

/*

  Maintenance log started on November 30th, 2000

  Nov. 30th, 2000   - fixed rule generation procedure to use
                      all frequent itemsets, not only the
                      maximal ones

*/

/**

   AprioriRules.java<P>

   This class implements the Apriori algorithm 
   for finding association rules.

   (see "Fast Algorithms for Mining Association Rules"
   by Rakesh Agrawal and Ramakrishnan Srikant
   from IBM Almaden Research Center 1994)
   
*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public class AprioriRules implements AssociationsFinder
{
  private SET supports;
  private Vector rules;

  private float min_support;
  private float min_confidence;

  private Itemset is_in_antecedent;
  private Itemset is_in_consequent;
  private Itemset is_ignored;

  private int max_antecedent;
  private int min_consequent;

  // this method stores all frequent itemsets that have support
  // greater than the minimum support in a SET for more efficient
  // access times.
  private void initializeSupports(DBCacheReader cacheReader)
  {
    // create new SET
    supports = new SET();

    try
      {
	Itemset is;
	while (true)
	  {
	    // get item from cache
	    is = cacheReader.getNextItemset();
	    // if item has support greater than the minimum support
	    // required then we add it to the SET
	    if (is.getSupport() >= min_support)
	      {
		supports.insert(is);
	      }
	  }
      }
    catch (EOFException e)
      {
	// do nothing, we just reached the EOF
      }
    catch (IOException e)
      {
	System.err.println("Error scanning cache!!!\n" + e);
      }
    catch (ClassNotFoundException e)
      {
	System.err.println("Error scanning cache!!!\n" + e);
      }
  }

  /**
   * Find association rules in a database, given the set of
   * frequent itemsets.
   *
   * @param cacheReader   the object used to read from the cache
   * @param minSupport   the minimum support
   * @param minConfidence   the minimum confidence
   * @return   a Vector containing all association rules found
   */
  public Vector findAssociations(DBCacheReader cacheReader,
				 float minSupport,
				 float minConfidence)
  {
    min_support = minSupport;
    min_confidence = minConfidence;

    // create the vector where we'll put the rules
    rules = new Vector();
    
    // read from cache supports of frequent itemsets
    initializeSupports(cacheReader);

    // get the frequent itemsets
    Vector frequent = supports.getItemsets();

    // generate rules from each frequent itemset
    for (int i = 0; i < frequent.size(); i++)
      {
	// get a frequent itemset
	Itemset is_frequent = (Itemset)frequent.get(i);

	// skip it if it's too small
	if (is_frequent.size() <= 1)
	  continue;

	// get all possible 1 item consequents
	Vector consequents = new Vector(is_frequent.size());
	for (int k = 0; k < is_frequent.size(); k++)
	  {
	    int item = is_frequent.getItem(k);
	    Itemset is_consequent = new Itemset(1);
	    is_consequent.addItem(item);

	    // is_consequent now contains a possible consequent
	    // verify now that the rule having this consequent
	    // satisfies our requirements

	    Itemset is_antecedent = is_frequent.subtract(is_consequent);
	    float antecedent_support = (float)0.00001;
	    try
	      {
		antecedent_support = supports.getSupport(is_antecedent);
	      }
	    catch (SETException e)
	      {
		System.err.println("Error geting support from SET!!!\n" + e);
	      }
	    float confidence = is_frequent.getSupport() / antecedent_support;

	    if (confidence >= min_confidence)
	      {
		consequents.add(is_consequent);

		// we add the rule to our collection if it satisfies
		// our conditions
		rules.add(new AssociationRule(is_antecedent, is_consequent,
					      is_frequent.getSupport(), 
					      confidence));
	      }
	  }

	// call the ap_genrules procedure for generating all rules
	// out of this frequent itemset
	ap_genrules(is_frequent, consequents);
      }

    return rules;
  }

  // this is the ap-genrules procedure that generates rules out
  // of a frequent itemset.
  private void ap_genrules(Itemset is_frequent, Vector consequents)
  {
    if (consequents.size() == 0)
      return;

    // the size of frequent must be bigger than the size of the itemsets
    // in consequents by at least 2, in order to be able to generate
    // a rule in this call
    if (is_frequent.size() > ((Itemset)(consequents.get(0))).size() + 1)
      {
	Vector new_consequents = apriori_gen(consequents);
	AssociationRule ar;

	for (int i = 0; i < new_consequents.size(); i++)
	  {
	    Itemset is_consequent = (Itemset)new_consequents.get(i);
	    Itemset is_antecedent = is_frequent.subtract(is_consequent);
	    float antecedent_support = (float)0.00001;
	    try
	      {
		antecedent_support = supports.getSupport(is_antecedent);
	      }
	    catch (SETException e)
	      {
		System.err.println("Error geting support from SET!!!\n" + e);
	      }
	    float confidence = is_frequent.getSupport() / antecedent_support;
	    
	    // if the rule satisfies our requirements we add it
	    // to our collection
	    if (confidence >= min_confidence)
	      rules.add(new AssociationRule(is_antecedent, is_consequent,
					    is_frequent.getSupport(), 
					    confidence));
	    // otherwise we remove the consequent from the collection
	    // and we update the index such that we don't skip a consequent
	    else
	      new_consequents.remove(i--);
	  }

	ap_genrules(is_frequent, new_consequents);
      }
  }

  // this is the apriori_gen procedure that generates starting from
  // a k-itemset collection a new collection of (k+1)-itemsets.
  private Vector apriori_gen(Vector itemsets)
  {
    if (itemsets.size() == 0)
      return new Vector(0);

    // create a hashtree so that we can check more efficiently the
    // number of subsets
    // this may not really be necessary when generating rules since
    // itemsets will probably be a small collection, but just in case
    HashTree ht_itemsets = new HashTree(itemsets);
    for (int i = 0; i < itemsets.size(); i++)
      ht_itemsets.add(i);
    ht_itemsets.prepareForDescent();

    Vector result = new Vector();
    Itemset is_i, is_j;
    for (int i = 0; i < itemsets.size() - 1; i++)
      for (int j = i + 1; j < itemsets.size(); j++)
	{
	  is_i = (Itemset)itemsets.get(i);
	  is_j = (Itemset)itemsets.get(j);

	  // if we cannot combine element i with j then we shouldn't
	  // waste time for bigger j's. This is because we keep the
	  // collections ordered, an important detail in this implementation
	  if (!is_i.canCombineWith(is_j))
	    break;
	  else
	    {
	      Itemset is = is_i.combineWith(is_j);

	      // a real k-itemset has k (k-1)-subsets
	      // so we test that this holds before adding to result
	      if (ht_itemsets.countSubsets(is) 
		  == is.size())
		result.add(is);
	    }
	}

    return result;
  }

  /**
   * Find association rules in a database, given the set of
   * frequent itemsets and a set of restrictions.
   *
   * @param cacheReader   the object used to read from the cache
   * @param minSupport   the minimum support
   * @param minConfidence   the minimum confidence
   * @param inAntecedent   the items that must appear in the antecedent 
   * of each rule, if null then this constraint is ignored 
   * @param inConsequent   the items that must appear in the consequent
   * of each rule, if null then this constraint is ignored 
   * @param ignored   the items that should be ignored,
   * if null then this constraint is ignored 
   * @param maxAntecedent   the maximum number of items that can appear
   * in the antecedent of each rule, if 0 then this constraint is ignored 
   * @param minConsequent   the minimum number of items that should appear
   * in the consequent of each rule, if 0 then this constraint is ignored 
   * @return   a Vector containing all association rules found
   */
  public Vector findAssociations(DBCacheReader cacheReader, 
				 float minSupport,
				 float minConfidence,
				 Itemset inAntecedent,
				 Itemset inConsequent,
				 Itemset ignored,
				 int maxAntecedent, 
				 int minConsequent)
  {
    min_support = minSupport;
    min_confidence = minConfidence;

    is_in_antecedent = inAntecedent;
    is_in_consequent = inConsequent;
    is_ignored = ignored;
    max_antecedent = maxAntecedent;
    min_consequent = minConsequent;

    // create the vector where we'll put the rules
    rules = new Vector();
    
    // read from cache supports of frequent itemsets
    initializeSupports(cacheReader);

    // get the frequent itemsets
    Vector frequent = supports.getItemsets();

    if (frequent.size() == 0)
      return rules;

    // if we need to ignore some items
    if (ignored != null)
      {
	// remove all frequent itemsets that contain
	// items to be ignored; their subsets that do
	// not contain those items will remain
	for (int i = 0; i < frequent.size(); i++)
	  {
	    Itemset is = (Itemset)frequent.get(i);
	    if (is.doesIntersect(ignored))
	      {
		// replace this element with last, delete last,
		// and don't advance index
		frequent.set(i, frequent.lastElement());
		frequent.remove(frequent.size() - 1);
		i--;
	      }
	  }

	if (frequent.size() == 0)
	  return rules;
      }

    // if we need to have some items in the antecedent or consequent
    if (inAntecedent != null || inConsequent != null)
      {
	// remove frequent itemsets that don't have the
	// required items
	for (int i = 0; i < frequent.size(); i++)
	  {
	    Itemset is = (Itemset)frequent.get(i);
	    if (inAntecedent != null && !inAntecedent.isIncludedIn(is))
	      {
		// replace this element with last, delete last,
		// and don't advance index
		frequent.set(i, frequent.lastElement());
		frequent.remove(frequent.size() - 1);
		i--;
	      }
	    else if (inConsequent != null && !inConsequent.isIncludedIn(is))
	      {
		// replace this element with last, delete last,
		// and don't advance index
		frequent.set(i, frequent.lastElement());
		frequent.remove(frequent.size() - 1);
		i--;
	      }
	  }

	if (frequent.size() == 0)
	  return rules;
      }

    // generate rules from each frequent itemset
    for (int i = 0; i < frequent.size(); i++)
      {
	// get a frequent itemset
	Itemset is_frequent = (Itemset)frequent.get(i);

	// skip it if it's too small
	if (is_frequent.size() <= 1 ||
	    is_frequent.size() <= minConsequent)
	  continue;

	// get all possible 1 item consequents
	Vector consequents = new Vector(is_frequent.size());
	for (int k = 0; k < is_frequent.size(); k++)
	  {
	    int item = is_frequent.getItem(k);
	    Itemset is_consequent = new Itemset(1);
	    is_consequent.addItem(item);

	    // is_consequent now contains a possible consequent
	    // verify now that the rule having this consequent
	    // satisfies our requirements

	    Itemset is_antecedent = is_frequent.subtract(is_consequent);
	    float antecedent_support = (float)0.00001;
	    try
	      {
		antecedent_support = supports.getSupport(is_antecedent);
	      }
	    catch (SETException e)
	      {
		System.err.println("Error geting support from SET!!!\n" + e);
	      }
	    float confidence = is_frequent.getSupport() / antecedent_support;

	    if (confidence >= min_confidence)
	      {
		consequents.add(is_consequent);

		// check whether it also satisfies our constraints
		boolean approved = true;

		if (approved && is_in_antecedent != null
		    && !is_in_antecedent.isIncludedIn(is_antecedent))
		  approved = false;

		if (approved && is_in_consequent != null
		    && !is_in_consequent.isIncludedIn(is_consequent))
		  approved = false;

		if (approved && max_antecedent > 0
		    && is_antecedent.size() > max_antecedent)
		  approved = false;

		if (approved && min_consequent > 0 
		    && is_consequent.size() < min_consequent)
		  approved = false;

		// if the rule satisifes all requirements then
		// we add it to the rules collection
		if (approved)
		  rules.add(new AssociationRule(is_antecedent, 
						is_consequent,
						is_frequent.getSupport(), 
						confidence));
	      }
	  }

	// call the ap-genrules procedure for generating all rules
	// out of this frequent itemset
	ap_genrules_constraint(is_frequent, consequents);
      }

    return rules;
  }

  // this is the ap-genrules procedure that generates rules out
  // of a frequent itemset.
  private void ap_genrules_constraint(Itemset is_frequent, Vector consequents)
  {
    if (consequents.size() == 0)
      return;

    // the size of frequent must be bigger than the size of the itemsets
    // in consequents by at least 2, in order to be able to generate
    // a rule in this call
    if (is_frequent.size() > ((Itemset)(consequents.get(0))).size() + 1)
      {
	Vector new_consequents = apriori_gen(consequents);
	AssociationRule ar;

	for (int i = 0; i < new_consequents.size(); i++)
	  {
	    Itemset is_consequent = (Itemset)new_consequents.get(i);
	    Itemset is_antecedent = is_frequent.subtract(is_consequent);
	    float antecedent_support = (float)0.00001;
	    try
	      {
		antecedent_support = supports.getSupport(is_antecedent);
	      }
	    catch (SETException e)
	      {
		System.err.println("Error geting support from SET!!!\n" + e);
	      }
	    float confidence = is_frequent.getSupport() / antecedent_support;
	    
	    // if the rule satisfies our confidence requirements
	    if (confidence >= min_confidence)
	      {
		// check whether it also satisfies our constraints
		boolean approved = true;

		if (approved && is_in_antecedent != null
		    && !is_in_antecedent.isIncludedIn(is_antecedent))
		  approved = false;

		if (approved && is_in_consequent != null
		    && !is_in_consequent.isIncludedIn(is_consequent))
		  approved = false;

		if (approved && max_antecedent > 0
		    && is_antecedent.size() > max_antecedent)
		  approved = false;

		if (approved && min_consequent > 0 
		    && is_consequent.size() < min_consequent)
		  approved = false;

		// if the rule satisifes all requirements then
		// we add it to the rules collection
		if (approved)
		  rules.add(new AssociationRule(is_antecedent, 
						is_consequent,
						is_frequent.getSupport(), 
						confidence));
	      }
	    // otherwise we remove the consequent from the collection
	    // and we update the index such that we don't skip a consequent
	    else
	      new_consequents.remove(i--);
	  }

	ap_genrules_constraint(is_frequent, new_consequents);
      }
  }
}
