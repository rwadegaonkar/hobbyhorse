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
import java.util.Hashtable;
import java.io.*;

/**

   AlgorithmManager.java<P>

   This class implements the algorithm manager module that runs
   algorithms and hands back the results computed by the algorithms.<P>
   
*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public class AlgorithmManager
{
  private DBConfig dbconf;

  /**
   * Construct a new AlgorithmManager object
   */
  public AlgorithmManager()
    throws DBConfigException
  {
    dbconf = DBConfig.getDBConfig();
  }

  private LargeItemsetsFinder loadAlgorithm(String algName)
    throws AlgorithmManagerException
  {
    try
      {
	return (LargeItemsetsFinder)(Class.forName(algName).newInstance());
      }
    catch (ClassNotFoundException e)
      {
	// try loading the class from a jar file
	try
	  {
	    JarClassLoader jcl = new JarClassLoader(algName + ".jar");

	    return (LargeItemsetsFinder)(jcl.loadClass(algName).newInstance());
	  }
	catch (Exception e2)
	  {
	    throw new AlgorithmManagerException("Cannot load algorithm " 
						+ algName
						+ " : " + e
						+ " and " + e2);
	  }
      }
    catch (Exception e)
      {
	throw new AlgorithmManagerException("Cannot load algorithm " + algName
					    + " : " + e);
      }
  }

  private Vector mine(String user, String dbName, String algName,
		      boolean advanced,
		      float minSupport, float minConfidence,
		      Vector inAntecedent, Vector inConsequent,
		      Vector ignored, 
		      int maxAntecedent, int minConsequent)
    throws AlgorithmManagerException
  {
    int old_cache_status = 0;
    float old_cache_support;
    int db_usage_count;

    boolean algorithm_marked = false;
    boolean database_marked = false;

    DBCacheWriter cache_writer = null;
    DBCacheReader cache_reader = null;
    DBReader db_reader = null;

    // *** begin critical section ***, lock on dbconf
    synchronized (dbconf)
      {
	try
	  {
	    // first mark algorithm in use
	    dbconf.markAlgorithmInUse(user, algName);
	    algorithm_marked = true;

	    // get cache status and support for this database
	    old_cache_status = dbconf.getCacheStatus(dbName);
	    old_cache_support = dbconf.getCacheSupport(dbName);
	    // get use count for database
	    db_usage_count = dbconf.getDatabaseUseCount(dbName);

	    switch (old_cache_status)
	      {
	      case DBConfig.CACHE_INITIAL_CREATION:
	      case DBConfig.CACHE_IN_CREATION:
		// if cache is being created
		// then we cannot use this database, exit
		dbconf.unmarkAlgorithmInUse(algName);
		
		throw new AlgorithmManagerException("A database cache is being created, cannot use database at this time");
		//break;

	      case DBConfig.CACHE_CREATED:
		// see if we can use the already created cache
		if (old_cache_support <= minSupport)
		  {
		    // we can use the cache !
		    dbconf.markDatabaseInUse(user, dbName);
		    database_marked = true;

		    // we won't use the algorithm !
		    dbconf.unmarkAlgorithmInUse(algName);

		    String cache_path = dbconf.getPathCache(dbName);
		    cache_reader = new DBCacheReader(cache_path);
		  }
		else 
		  {
		    // we cannot use the cache, see if we can create new one
		    if (db_usage_count > 0)
		      {
			dbconf.unmarkAlgorithmInUse(algName);
		
			throw new AlgorithmManagerException("Cannot create cache if database is in use");
		      }
		    else
		      {
			dbconf.markDatabaseInUse(user, dbName);
			database_marked = true;
			dbconf.setCacheStatus(dbName, 
					      DBConfig.CACHE_IN_CREATION);

			String db_path = dbconf.getPathDatabase(dbName);
			String cache_path = dbconf.getPathCache(dbName);
			db_reader = new DBReader(db_path);
			// we'll try to create new cache into a temporary file
			cache_writer = new DBCacheWriter(cache_path + "tmp");
		      }
		  }
		break;

	      case DBConfig.CACHE_NOT_CREATED:
		dbconf.markDatabaseInUse(user, dbName);
		database_marked = true;
		dbconf.setCacheStatus(dbName, DBConfig.CACHE_INITIAL_CREATION);

		String db_path = dbconf.getPathDatabase(dbName);
		String cache_path = dbconf.getPathCache(dbName);
		db_reader = new DBReader(db_path);
		// we'll try to create new cache
		cache_writer = new DBCacheWriter(cache_path);
		break;

	      default:
		throw new AlgorithmManagerException("Internal error: Invalid cache status!");
	      }
	  }
	catch (AlgorithmManagerException e)
	  {
	    throw e; // rethrow
	  }
	catch (Exception e)
	  {
	    try
	      {
		if (algorithm_marked)
		  dbconf.unmarkAlgorithmInUse(algName);

		if (database_marked)
		  {
		    dbconf.unmarkDatabaseInUse(dbName);
		    if (old_cache_status != 0)
		      dbconf.setCacheStatus(dbName, old_cache_status);
		  }
	      }
	    catch (Exception e2)
	      {
		throw new AlgorithmManagerException("Two errors occurred:\n" 
						    + e + "\nand\n" + e2);
	      }

	    throw new AlgorithmManagerException(e.toString());
	  }
      } // *** end of critical section ***

    // now we have to run the algorithms !!!

    // now either cache_reader or cache_writer has been initialized

    if (cache_writer != null)
      {
	// we must first create the cache

	try
	  {
	    // get hold of the algorithm object
	    LargeItemsetsFinder algorithm = loadAlgorithm(algName);
	    
	    // we try to run the algorithm
	    algorithm.findLargeItemsets(db_reader, cache_writer, minSupport);
	  }
	catch (Throwable e) // catches both Error and Exception !
	  {
	    try
	      {
		// we catch out of memory errors here (among others)
		String cache_path = dbconf.getPathCache(dbName);
		
		if (old_cache_status == DBConfig.CACHE_NOT_CREATED)
		  {
		    File f = new File(cache_path);
		    f.delete();
		  }
		else
		  {
		    File f = new File(cache_path + "tmp");
		    f.delete();
		  }
		
		dbconf.unmarkDatabaseInUse(dbName);
		dbconf.setCacheStatus(dbName, old_cache_status);
	      }
	    catch (Exception e2)
	      {
		throw new AlgorithmManagerException("Two errors occurred:\n" 
						    + e + "\nand\n" + e2);
	      }
		
	    throw new AlgorithmManagerException("An error occurred: " + e);
	  }
	finally
	  {
	    // close these objects, they're not needed anymore
	    try
	      {
		db_reader.close();
		cache_writer.close();
		// we won't use the algorithm anymore !
		dbconf.unmarkAlgorithmInUse(algName);
	      }
	    catch (Exception e)
	      {
		throw new AlgorithmManagerException("PANIC! " + e);
	      }
	  }

	// clean-up after we created cache
	try
	  {
	    String cache_path = dbconf.getPathCache(dbName);

	    if (old_cache_status != DBConfig.CACHE_NOT_CREATED)
	      {
		// replace old cache with new one

		// first we delete old cache
		File f_target = new File(cache_path);
		f_target.delete();
		// then we rename new cache
		File f_source = new File(cache_path + "tmp");
		f_source.renameTo(f_target);
	      }

	    // indicate that we have created the cache
	    dbconf.setCacheStatus(dbName, DBConfig.CACHE_CREATED);

	    // indicate current cache support
	    dbconf.setCacheSupport(dbName, minSupport);

	    // open the cache for reading
	    cache_reader = new DBCacheReader(cache_path);
	  }
	catch (Exception e)
	  {
	    throw new AlgorithmManagerException("PANIC! " + e);
	  }
      }

    // read database columns
    Itemset is_in_antecedent = null;
    Itemset is_in_consequent = null;
    Itemset is_ignored = null;

    if (advanced)
      {
	try
	  {
	    // get first the column names
	    String db_path = dbconf.getPathDatabase(dbName);
	    db_reader = new DBReader(db_path);
	    Vector column_names = db_reader.getColumnNames();
	    db_reader.close();
	    
	    // create a map from names to indexes
	    Hashtable name_to_index = new Hashtable();
	    for (int i = 0; i < column_names.size(); i++)
	      name_to_index.put((String)(column_names.get(i)),
				new Integer(i + 1));

	    if (inAntecedent.size() > 0)
	      {
		// create an itemset corresponding to inAntecedent
		is_in_antecedent = new Itemset(inAntecedent.size());
		for (int i = 0; i < inAntecedent.size(); i++)
		  {
		    String item_name = (String)(inAntecedent.get(i));
		    int index = ((Integer)(name_to_index.get(item_name))).intValue();
		    is_in_antecedent.addItem(index);
		  }
	      }

	    if (inConsequent.size() > 0)
	      {
		// create an itemset corresponding to inConsequent
		is_in_consequent = new Itemset(inConsequent.size());
		for (int i = 0; i < inConsequent.size(); i++)
		  {
		    String item_name = (String)(inConsequent.get(i));
		    int index = ((Integer)(name_to_index.get(item_name))).intValue();
		    is_in_consequent.addItem(index);
		  }
	      }

	    if (ignored.size() > 0)
	      {
		// create an itemset corresponding to ignored
		is_ignored = new Itemset(ignored.size());
		for (int i = 0; i < ignored.size(); i++)
		  {
		    String item_name = (String)(ignored.get(i));
		    int index = ((Integer)(name_to_index.get(item_name))).intValue();
		    is_ignored.addItem(index);
		  }
	      }
	  }
	catch (Exception e)
	  {
	    throw new AlgorithmManagerException("PANIC! " + e);
	  }
      }

    // at this point we should have a cache_reader initialized
    if (cache_reader == null)
      throw new AlgorithmManagerException("Internal application error: cache_reader not initialized!");
      
    AssociationsFinder algorithm = new AprioriRules();
    Vector results;
    
    try
      {
	if (advanced)
	  {
	    results = algorithm.findAssociations(cache_reader, 
						 minSupport, 
						 minConfidence,
						 is_in_antecedent, 
						 is_in_consequent,
						 is_ignored, 
						 maxAntecedent, 
						 minConsequent);
	  }
	else
	  {
	    results = algorithm.findAssociations(cache_reader, 
						 minSupport, 
						 minConfidence);
	  }
      }
    catch (Throwable e)
      {
	throw new AlgorithmManagerException("An error occurred: " + e);
      }
    finally
      {
	try
	  {
	    dbconf.unmarkDatabaseInUse(dbName);
	    cache_reader.close();
	  }
	catch (Exception e)
	  {
	    throw new AlgorithmManagerException("PANIC! " + e);
	  }
      }

    return results;
  }

  /**
   * Find association rules with minimum support <code>minSupport</code>
   * and minimum confidence <code>minConfidence</code>
   * in database <code>dbName</code> using
   * algorithm <code>algName</code>. The user that requests this operation
   * is <code>user</code>.
   *
   * @param user   the user that requests this operation
   * @param dbName   the database to mine
   * @param algName   the algorithm to use
   * @param minSupport   the minimum support of the rules
   * @param minConfidence   the minimum confidence of the rules
   * @exception AlgorithmManagerException   a problem happened
   * @return   a Vector of AssociationRule objects
   */
  public Vector mine(String user, String dbName, String algName, 
		     float minSupport, float minConfidence)
    throws AlgorithmManagerException
  {
    if (user == null
	|| dbName == null
	|| algName == null
	|| minSupport <= 0
	|| minSupport > 1
	|| minConfidence <= 0
	|| minConfidence > 1)
      throw new AlgorithmManagerException("Invalid mining parameters!");

    return mine(user, dbName, algName, false,
		minSupport, minConfidence,
		null, null, null, 
		0, 0);
  }

  /**
   * Find association rules with minimum support <code>minSupport</code>
   * and minimum confidence <code>minConfidence</code>
   * in database <code>dbName</code> using
   * algorithm <code>algName</code>. The user that requests this operation
   * is <code>user</code>. Additional constraints are specified:
   * the items that must appear in the antecedent of the rules are
   * listed in <code>inAntecedent</code>, the items that must appear
   * in the consequent of the rules are listed in <code>inConsequent</code>,
   * the items that should be ignored are listed in <code>ignored</code>.
   * Also the rules must have at most <code>maxAntecedent</code> items
   * in the antecedent and at least <code>minConsequent</code> items
   * in the consequent. An empty Vector or a value of 0 will indicate that
   * a constraint is not required.
   *
   * @param user   the user that requests this operation
   * @param dbName   the database to mine
   * @param algName   the algorithm to use
   * @param minSupport   the minimum support of the rules
   * @param minConfidence   the minimum confidence of the rules
   * @param inAntecedent   items to appear in antecedent, if empty then
   * this constraint is ignored
   * @param inConsequent   items to appear in consequent, if empty then
   * this constraint is ignored
   * @param ignored   items to ignore, if empty then
   * this constraint is ignored
   * @param maxAntecedent   the maximum number of items in antecedent,
   * if 0 this constraint is ignored
   * @param minConsequent   the minimum number of items in consequent,
   * if 0 this constraint is ignored
   * @exception AlgorithmManagerException   a problem happened
   * @return   a Vector of AssociationRule objects
   */
  public Vector advMine(String user, String dbName, String algName,
			float minSupport, float minConfidence,
			Vector inAntecedent, Vector inConsequent,
			Vector ignored, 
			int maxAntecedent, int minConsequent)
    throws AlgorithmManagerException
  {
    if (user == null
	|| dbName == null
	|| algName == null
	|| minSupport <= 0
	|| minSupport > 1
	|| minConfidence <= 0
	|| minConfidence > 1
	|| inAntecedent == null
	|| inConsequent == null
	|| ignored == null
	|| maxAntecedent < 0
	|| minConsequent < 0)
      throw new AlgorithmManagerException("Invalid mining parameters!");

    return mine(user, dbName, algName, true,
		minSupport, minConfidence,
		inAntecedent, inConsequent, ignored, 
		maxAntecedent, minConsequent);
  }

  /**
   * Run a test to see how algorithms run on different databases for
   * different supports. This tests only the algorithms for finding
   * large itemsets.
   *
   * Each algorithm will be run on each database for all the supports
   * listed. If tests fail, results of 0 will be returned. This method
   * deals silently with errors since it tries to test as much as it can.
   *
   * The returned Vector will contain:
   *    - at index 0 the Vector <code>supports</code>
   *    - from index 1 on, we will have for each database/algorithm pair
   *      a Vector structured as follows:
   *      - at index 0 a String representing the name of the database
   *      - at index 1 a String representing the name of the algorithm
   *      - at index 2 a Vector containing the results of running the
   *        algorithm on the database for the full range of supports.
   *        This Vector corresponds to the <code>supports</code>
   *        Vector as follows:
   *           For each support at index <code>i</code> in the Vector 
   *           <code>supports</code>, we will find the time as a Long at
   *           index <code>2*i</code> and the number of passes as an 
   *           Integer at index <code>2*i+1</code> in this Vector of
   *           results.
   *
   * @param user   the user that requests this operation
   * @param databases   the databases to use in the test
   * @param algorithms   the algorithms to use in the test
   * @param supports   the supports to use in the test
   * @return   a Vector containing the results of the test
   */
  public Vector benchmark(String user, Vector databases, Vector algorithms,
			  Vector supports)
  {
    if (user == null
	|| databases == null
	|| algorithms == null
	|| supports == null)
      return new Vector(); // qui pro quo

    // the following will let me know if I marked the 
    // databases and algorithms
    boolean[] db_ready = new boolean[databases.size()];
    boolean[] alg_ready = new boolean[algorithms.size()];

    // mark all databases in use
    for (int i = 0; i < databases.size(); i++)
      {
	String dbName = (String)databases.get(i);
	try
	  {
	    dbconf.markDatabaseInUse(user, dbName);
	    db_ready[i] = true;
	  }
	catch (DBConfigException e)
	  {
	    db_ready[i] = false;
	  }
      }

    // mark all algorithms in use
    for (int i = 0; i < algorithms.size(); i++)
      {
	String algName = (String)algorithms.get(i);
	try
	  {
	    dbconf.markAlgorithmInUse(user, algName);
	    alg_ready[i] = true;
	  }
	catch (DBConfigException e)
	  {
	    alg_ready[i] = false;
	  }
      }

    // at this point we made sure that the algorithms and databases
    // that we just marked won't be deleted in the near future so
    // we can use them safely. Those algorithms and databases that
    // we couldn't mark were probably either deleted or scheduled
    // to be deleted so we'll just skip them.

    Timer timer = new Timer();
    timer.start(); // keep it running, we'll use reset() and time() to
    // read the time spent on something

    Vector results = new Vector();
    results.add(supports);

    for (int d = 0; d < databases.size(); d++)
      {
	if (db_ready[d] == false)
	  continue; // go to next one

	String dbName = (String)databases.get(d);

	// run all algorithms on this database so that we can
	// unmark this database
	for (int a = 0; a < algorithms.size(); a++)
	  {
	    if (alg_ready[a] == false)
	      continue; // go to next one

	    String algName = (String)algorithms.get(a);

	    Vector testrun = new Vector();
	    testrun.add(dbName);
	    testrun.add(algName);

	    // here we'll add for each test a Long indicating the time
	    Vector time_results = new Vector();
	    // here we'll add for each test an Integer indicating 
	    // the database passes
	    Vector num_passes_results = new Vector();

	    testrun.add(time_results);
	    testrun.add(num_passes_results);
	    results.add(testrun);

	    for (int s = 0; s < supports.size(); s++)
	      {
		float minSupport = ((Float)supports.get(s)).floatValue();

		// run algorithm algName on database dbName for minimum
		// support minSupport

		try
		  {
		    // get hold of the algorithm object
		    LargeItemsetsFinder algorithm = loadAlgorithm(algName);

		    String db_path = dbconf.getPathDatabase(dbName);
		    DBReader db_reader = new DBReader(db_path);

		    timer.reset();
		    // we try to run the algorithm
		    int passes = algorithm.findLargeItemsets(db_reader, 
							     null,
							     minSupport);
		    long time = timer.time();

		    db_reader.close();

		    time_results.add(new Long(time));
		    num_passes_results.add(new Integer(passes));
		  }
		catch (Throwable e) // catches both Error and Exception !
		  {
		    // we indicate that something went wrong by returning
		    // 0 values for time and passes.
		    time_results.add(new Long(0));
		    num_passes_results.add(new Integer(0));
		    System.err.println("Error trying to run algorithm "
				       + algName + " on database "
				       + dbName + " : " + e);
		  }
	      }
	  }

	// we've finished our work with this database so we can unmark it
	try
	  {
	    dbconf.unmarkDatabaseInUse(dbName);
	  }
	catch (DBConfigException e)
	  {
	    System.err.println("Error trying to unmark database "
			       + dbName);
	  }
      }

    // here we can finally unmark the algorithms

    for (int i = 0; i < algorithms.size(); i++)
      {
	if (alg_ready[i] == false)
	  continue; // go to next one

	String algName = (String)algorithms.get(i);

	try
	  {
	    dbconf.unmarkAlgorithmInUse(algName);
	  }
	catch (DBConfigException e)
	  {
	    System.err.println("Error trying to unmark algorithm "
			       + algName);
	  }
      }

    return results;
  }
}
