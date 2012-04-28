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
 * TestAlg.java
 *
 * This program first displays information about the csc.db database,
 * then runs the Apriori and AprioriRules algorithms on this database.
 *
 * Modules tested: DBReader, Itemset, DBCacheReader/Writer, SET, HashTree,
 * Apriori, AprioriRules (the standard mining only), AssociationRule
 *
 * (P) 2000 Dana & Laurentiu Cristofor
 */

import java.util.Vector;
import java.io.*;

class TestAlg
{
  private final static String dbName = "csc.db";
  private final static String cacheName = "csc.cache";

  private static float sup = (float)0.2;
  private static float conf = (float)0.6;

  public static void main(String args[])
  {
    testDB(dbName);

    mineDB(dbName);
  }

  private static void mineDB(String dbName)
  {
    try
      {
	// try to mine the database for all rules with support 20%
	// and confidence 60%
	System.out.println("\n\t\tMining database\n\t\t---------------\n");

	// First step: find frequent itemsets
	System.out.println("1. finding frequent itemsets");
	DBReader dbr = new DBReader(dbName);
	DBCacheWriter dbcw = new DBCacheWriter(cacheName);

	LargeItemsetsFinder alg = new Apriori();
	System.out.println("" + alg.findLargeItemsets(dbr, dbcw, sup) 
			   + " passes were done over the database");

	dbr.close();
	dbcw.close();

	// Second step: display large itemsets
	System.out.println("2. displaying large itemsets");
	// First we read the contents of the cache in a SET
	System.out.println("2.1. reading cache contents");
	DBCacheReader dbcr = new DBCacheReader(cacheName);

	SET frequents = new SET();
	try
	  {
	    Itemset is;
	    while (true)
	      {
		is = dbcr.getNextItemset();
		if (is.getSupport() >= sup)
		  frequents.insert(is);
	      }
	  }
	catch (EOFException e)
	  {
	    // do nothing, we just reached the EOF
	  }

	dbcr.close();

	// Second we get and display the large itemsets from the SET
	System.out.println("2.2. getting large itemsets");
	Vector large = frequents.getLargeItemsets();

	for (int i = 0; i < large.size(); i++)
	  System.out.println(large.get(i));

	// Third step: find association rules
	System.out.println("3. finding association rules");
	dbcr = new DBCacheReader(cacheName);

	AprioriRules algRules = new AprioriRules();
	Vector rules = algRules.findAssociations(dbcr, sup, conf);

	dbcr.close();

	// Fourth step: display association rules
	System.out.println("4. displaying association rules");
	for (int i = 0; i < rules.size(); i++)
	  System.out.println(rules.get(i));
      }
    catch (Exception e)
      {
	System.out.println(e);
	e.printStackTrace();
      }
  }

  private static void testDB(String dbName)
  {
    try
      {
	DBReader dbr = new DBReader(dbName);
    
	System.out.println("DB version: " + dbr.getVersion());
    
	System.out.println("DB description: " + dbr.getDescription());
    
	System.out.println("DB columns (" + dbr.getNumColumns() + ") : ");
	Vector col_names = dbr.getColumnNames();
	for(int i = 0; i < col_names.size(); i++)
	  System.out.print(col_names.get(i) + " ");
    
	System.out.println("\nDB rows (" + dbr.getNumRows() + ") : ");
	while (dbr.hasMoreRows())
	  System.out.println(dbr.getNextRow());

	System.out.println();

	dbr.close();
      }
    catch (Exception e)
      {
	System.out.println(e);
      }
  }
}
