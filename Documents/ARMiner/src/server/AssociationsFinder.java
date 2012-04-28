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

/**

   AssociationsFinder.java<P>

   This interface must be implemented by the algorithms that will look
   for associations.
   
*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public interface AssociationsFinder
{
  /**
   * Find association rules in a database, given the set of
   * frequent itemsets.
   *
   * @param cacheReader   the object used to read from the cache
   * @param minSupport   the minimum support
   * @param minConfidence   the minimum confidence
   * @return   a Vector containing all association rules found
   */
  Vector findAssociations(DBCacheReader cacheReader,
			  float minSupport,
			  float minConfidence);

  /**
   * Find association rules in a database, given the set of
   * frequent itemsets and a set of restrictions.
   *
   * @param cacheReader   the object used to read from the cache
   * @param minSupport   the minimum support
   * @param minConfidence   the minimum confidence
   * @param inAntecedent   the items that must appear in the antecedent 
   * of each rule
   * @param inConsequent   the items that must appear in the consequent
   * of each rule
   * @param ignored   the items that should be ignored
   * @param maxAntecedent   the maximum number of items that can appear
   * in the antecedent of each rule
   * @param minConsequent   the minimum number of items that should appear
   * in the consequent of each rule
   * @return   a Vector containing all association rules found
   */
  Vector findAssociations(DBCacheReader cacheReader, 
			  float minSupport,
			  float minConfidence,
			  Itemset inAntecedent,
			  Itemset inConsequent,
			  Itemset ignored,
			  int maxAntecedent, 
			  int minConsequent);
}
