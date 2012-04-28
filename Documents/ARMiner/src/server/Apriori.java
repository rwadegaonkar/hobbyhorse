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

/**

   Apriori.java<P>

   This class implements the Apriori algorithm 
   for finding large itemsets.

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

public class Apriori implements LargeItemsetsFinder
{
  private static final int INITIAL_CAPACITY = 10000;

  // our collections of itemsets
  private Vector candidates;
  private Vector k_frequent;
  private Vector large;

  // the hashtrees associated with candidates and k_frequent
  private HashTree ht_candidates;
  private HashTree ht_k_frequent;

  // this remembers the number of passes and also indicates the
  // current cardinality of the candidates
  private int pass_num;

  // our interface to the outside world
  private DBReader db_reader;
  private DBCacheWriter cache_writer;
  
  // useful information
  private long num_rows;
  private long min_weight;

  /**
   * Find the frequent itemsets in a database
   *
   * @param dbReader   the object used to read from the database
   * @param cacheWriter   the object used to write to the cache
   * if this is null, then nothing will be saved, this is useful
   * for benchmarking
   * @param minSupport   the minimum support
   * @return   the number of passes executed over the database
   */
  public int findLargeItemsets(DBReader dbReader, 
			       DBCacheWriter cacheWriter,
			       float minSupport)
  {
    // save the following into member fields
    db_reader = dbReader;
    cache_writer = cacheWriter;
    num_rows = dbReader.getNumRows();
    min_weight = (long)(num_rows * minSupport);

    // initialize the collections
    candidates = new Vector(INITIAL_CAPACITY);
    k_frequent = new Vector(INITIAL_CAPACITY);
    large = new Vector(INITIAL_CAPACITY);

    // initialize the hash trees
    ht_k_frequent = new HashTree(k_frequent);
    ht_candidates = new HashTree(candidates);

    // The initial candidates are all 1-itemsets
    Itemset is;
    for (int i = 1; i <= db_reader.getNumColumns(); i++)
      {
	is = new Itemset(1);
	is.addItem(i);
	candidates.add(is);
	ht_candidates.add(candidates.size() - 1);
      }
    
    // we start with first pass
    for (pass_num = 1; ; pass_num++)
      {
	// compute the weight of each candidate
	weighCandidates();

	// verify which candidates are frequent (have weight
	// greater than or equal to min_weight)
	evaluateCandidates();

	//Itemset.pruneNonMaximal(large);
	// compute maximum cardinality of large itemsets found so far
	int card, maxcard = 0;
	for (int i = 0; i < large.size(); i++)
	  if ((card = ((Itemset)large.get(i)).size()) > maxcard)
	    maxcard = card;

	// if last pass didn't produce any large itemsets
	// then we can stop the algorithm
	if (pass_num > maxcard)
	  break;

	// if we just examined the top itemset (the one containing
	// all items) then we're done, nothing more to do.
	if (pass_num >= db_reader.getNumColumns())
	  break;

	// generate new candidates from frequent itemsets
	generateCandidates();

	// exit if no more candidates
	if (candidates.size() == 0)
	  break;
      }

    return pass_num;
  }

  // this procedure scans the database and computes the weight of each
  // candidate
  private void weighCandidates()
  {
    ht_candidates.prepareForDescent();

    try
      {
	Itemset row = db_reader.getFirstRow();
	ht_candidates.update(row);

	while (db_reader.hasMoreRows())
	  {
	    row = db_reader.getNextRow();
	    ht_candidates.update(row);
	  }
      }
    catch (Exception e)
      {
	System.err.println("Error scanning database!!!\n" + e);
      }
  }

  // this procedure checks to see which itemsets are frequent
  private void evaluateCandidates()
  {
    Itemset is;

    for (int i = 0; i < candidates.size(); i++)
      // if this is a frequent itemset
      if ((is = (Itemset)candidates.get(i)).getWeight() >= min_weight)
	{
	  // compute support of itemset
	  is.setSupport((float)is.getWeight()/(float)num_rows);

	  // write itemset to the cache
	  try
	    {
	      if (cache_writer != null)
		cache_writer.writeItemset(is);
	    }
	  catch (IOException e)
	    {
	      System.err.println("Fishy error!!!\n" + e);
	    }

	  // then add it to the large and k_frequent collections
	  large.add(is);
	  k_frequent.add(is);
	  ht_k_frequent.add(k_frequent.size() - 1);
	}

    // reinitialize candidates for next step
    candidates.clear();
    ht_candidates = new HashTree(candidates);
  }

  // this procedure generates new candidates out of itemsets
  // that are frequent according to the procedure described
  // by Agrawal a.o.
  private void generateCandidates()
  {
    ht_k_frequent.prepareForDescent();

    if (k_frequent.size() == 0)
      return;

    for (int i = 0; i < k_frequent.size() - 1; i++)
      for (int j = i + 1; j < k_frequent.size(); j++)
	if (!getCandidate(i, j))
	  break;

    // reinitialize k_frequent for next step
    k_frequent.clear();
    ht_k_frequent = new HashTree(k_frequent);
  }

  // this procedure tries to combine itemsets i and j and returns
  // true if succesful, false if it can't combine them
  private boolean getCandidate(int i, int j)
  {
    Itemset is_i = (Itemset)k_frequent.get(i);
    Itemset is_j = (Itemset)k_frequent.get(j);

    // if we cannot combine element i with j then we shouldn't
    // waste time for bigger j's. This is because we keep the
    // collections ordered, an important detail in this implementation
    if (!is_i.canCombineWith(is_j))
      return false;
    else
      {
	Itemset is = is_i.combineWith(is_j);

	// a real k-frequent itemset has k (k-1)-frequent subsets
	if (ht_k_frequent.countSubsets(is) == is.size())
	  {
	    candidates.add(is);
	    ht_candidates.add(candidates.size() - 1);
	  }

	return true;
      }
  }
}
