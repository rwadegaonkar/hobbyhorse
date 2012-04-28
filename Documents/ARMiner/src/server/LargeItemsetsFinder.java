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

   LargeItemsetsFinder.java<P>

   This interface must be implemented by the algorithms that will look
   for large itemsets.
   
*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public interface LargeItemsetsFinder
{
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
  int findLargeItemsets(DBReader dbReader, 
			DBCacheWriter cacheWriter,
			float minSupport);
}
