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
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

import java.util.*;
import java.io.*;
import java.net.*;

/*

  Maintenance log started on April 5th, 2001 by Laurentiu Cristofor

  Apr. 5th, 2001    - I have replaced the quicksort used for sorting
                      the mining results with a heapsort implementation,
                      which runs faster and uses less memory. This also
                      eliminates the StackOutOfMemory problem that 
                      would appear when sorting large results
                    - I have increased the number of results sent to
                      the client, the BATCH_SIZE, to 5000

*/ 

/**

   ServerChild.java<P>

   ServerChild will be responsible for the communication 
   with a particular client.<P>

*/

public class ServerChild extends Thread
{
  private Socket clientSocket;      // socket for communication with the client
  private ObjectInputStream in;     // for reading
  private ObjectOutputStream out;   // for writing

  private static long connection_sequence; // used to assign id's to
                                           // connections

  private long connectionID;        // the connection identifier

  private String user = null;       // user, owner of the session
  private DBConfig dbconf = null;   // configuration object

  private static final int BATCH_SIZE = 5000; // max number
                                              // of association rules 
                                              // to be returned

  private Vector results = null;    // mining results are kept here
  private int index = 0;            // next position to be returned 
                                    // from results

  public static final int ASC  = 1; // for sorting in ascending order
  public static final int DESC = 2; // for sorting in descending order

  /**
   * Initialize a ServerChild to communicate 
   * through <code>clientSocket</code>.
   *
   */
  public ServerChild(Socket clientSocket)
  {
    try
      {
	this.clientSocket = clientSocket; 

	// open socket I/O streams
	in = new ObjectInputStream(clientSocket.getInputStream());
	out = new ObjectOutputStream(clientSocket.getOutputStream());

	// assign this connection an identifier
	connection_sequence++;
	if (connection_sequence < 0)
	  connection_sequence = 0;
	connectionID = connection_sequence;
      }
    catch (Exception e)
      {
	log("ServerChild error: " + e);
      }
  }

  /**
   * Return this connection's identifier
   *
   */
  public long getID()
  {
    return connectionID;
  }

  /**
   * Start the execution of the ServerChild.
   *
   */
  public void run() 
  {
    try 
      {
	serviceClient();
	Server.remove(this);
	clientSocket.close();
      } 
    catch (Exception e) 
      {
	log("ServerChild error: " + e);
      }
  }

  /*
   * Loops over the client requests;
   */
  private void serviceClient()
  {
    String cmmd;
    boolean loggedOn = false;

    while(true)
      {
	try
	  {
	    cmmd = (String)in.readObject();
	    log(cmmd);

	    if (loggedOn)
	      {
		if (cmmd.equals("EXIT"))
		  {
		    try
		      {
			dbconf.unmarkLogged(user);
		      }
		    catch (DBConfigException e)
		      {
			log(e);
		      }
		    
		    break;
		  }
		else if ((cmmd.equals("GETCOLS")))
		  {
		    Vector dbs = (Vector)in.readObject();
		    log(dbs);
		    getColumns(dbs);
		  }
		else if ((cmmd.equals("GETDBCONFIG")))
		  getDBConfig();
		else if ((cmmd.equals("BENCHMARK")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    benchmark((Vector)info.get(0), (Vector)info.get(1),
			      (Vector)info.get(2));
		  }	    
		else if ((cmmd.equals("CHGPERM")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    chgPerm((String)info.get(0), ((Long)info.get(1)).longValue());
		  }
		else if ((cmmd.equals("CHGPASSW")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    chgPassw((String)info.get(0), (String)info.get(1));
		  }
		else if ((cmmd.equals("CHGNAMEUSR")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    chgNameUsr((String)info.get(0), (String)info.get(1));
		  }
		else if ((cmmd.equals("DELUSR")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    delUsr((String)info.get(0));
		  }
		else if ((cmmd.equals("ADDUSR")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    addUsr((String)info.get(0), (String)info.get(1),
			   ((Long)info.get(2)).longValue(), (Vector)info.get(3));
		  }
		else if ((cmmd.equals("DELUSRFROMGRPS")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    delUsrFromGrps((String)info.get(0), (Vector)info.get(1));
		  }
		else if ((cmmd.equals("ADDUSRTOGRPS")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    addUsrToGrps((String)info.get(0), (Vector)info.get(1));
		  }
		else if ((cmmd.equals("SETUSRSFORGRP")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    setUsrsForGrp((String)info.get(0), (Vector)info.get(1));
		  }
		else if ((cmmd.equals("SETGRPSFORUSR")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    setGrpsForUsr((String)info.get(0), (Vector)info.get(1));
		  }
		else if ((cmmd.equals("DELGRPFROMUSRS")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    delGrpFromUsrs((String)info.get(0), (Vector)info.get(1));
		  }
		else if ((cmmd.equals("ADDGRPTOUSRS")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    addGrpToUsrs((String)info.get(0), (Vector)info.get(1));
		  }
		else if ((cmmd.equals("CHGNAMEGRP")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    chgNameGrp((String)info.get(0), (String)info.get(1));
		  }
		else if ((cmmd.equals("DELGRP")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    delGrp((String)info.get(0));
		  }
		else if ((cmmd.equals("ADDGRP")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    addGrp((String)info.get(0), (Vector)info.get(1));
		  }
		else if ((cmmd.equals("CHGALGGRP")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    chgAlgGrp((String)info.get(0), (String)info.get(1));
		  }
		else if ((cmmd.equals("DELALG")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    delAlg((String)info.get(0));
		  }
		else if ((cmmd.equals("CHGDBGRP")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    chgDbGrp((String)info.get(0), (String)info.get(1));
		  }
		else if ((cmmd.equals("DELDB")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    delDb((String)info.get(0));
		  }
		else if ((cmmd.equals("ADDDB")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    addDb(info);
		  }
		else if ((cmmd.equals("ADDALG")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    addAlg(info);
		  }
		else if ((cmmd.equals("MINE")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    mine((String)info.get(0),
			 (String)info.get(1),
			 ((Float)info.get(2)).floatValue(),
			 ((Float)info.get(3)).floatValue());
		  }
		else if ((cmmd.equals("MINEADV")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    mineAdv((String)info.get(0),
			    (String)info.get(1),
			    ((Float)info.get(2)).floatValue(),
			    ((Float)info.get(3)).floatValue(),
			    (Vector)info.get(4),
			    (Vector)info.get(5),
			    (Vector)info.get(6),
			    ((Integer)info.get(7)).intValue(),
			    ((Integer)info.get(8)).intValue());
		  }
		else if ((cmmd.equals("GENDB")))
		  {
		    Vector info = (Vector)in.readObject();
		    log(info);
		    genDB((String)info.get(0),
			  (String)info.get(1),
			  ((Long)info.get(2)).longValue(),
			  ((Integer)info.get(3)).intValue(),
			  ((Integer)info.get(4)).intValue(),
			  ((Integer)info.get(5)).intValue(),
			  ((Integer)info.get(6)).intValue(),
			  ((Double)info.get(7)).doubleValue(),
			  ((Double)info.get(8)).doubleValue());
		  }
		else if ((cmmd.equals("GETNEXT")))
		  getNext();
		else if ((cmmd.equals("SORT")))
		  {
		    cmmd = (String)in.readObject();
		    log(cmmd);
		    if (cmmd.equals("ANTECEDENT"))
		      {
			cmmd = (String)in.readObject();
			log(cmmd);
			if (cmmd.equals("ASC"))
			  {
			    sort(AssociationRule.ANTECEDENT_SIZE, ASC);
			  }
			else if (cmmd.equals("DESC"))
			  {
			    sort(AssociationRule.ANTECEDENT_SIZE, DESC);
			  }
			else
			  {
			    sendWarning("unknown command");
			  }
		      }
		    else if (cmmd.equals("CONSEQUENT"))
		      {
			cmmd = (String)in.readObject();
			log(cmmd);
			if (cmmd.equals("ASC"))
			  {
			    sort(AssociationRule.CONSEQUENT_SIZE, ASC);

			  }
			else if (cmmd.equals("DESC"))
			  {
			    sort(AssociationRule.CONSEQUENT_SIZE, DESC);
			  }
			else
			  {
			    sendWarning("unknown command");
			  }

		      }
		    else if (cmmd.equals("SUPPORT"))
		      {
			cmmd = (String)in.readObject();
			log(cmmd);
			if (cmmd.equals("ASC"))
			  {
			    sort(AssociationRule.SUPPORT, ASC);
			  }
			else if (cmmd.equals("DESC"))
			  {
			    sort(AssociationRule.SUPPORT, DESC);
			  }
			else
			  {
			    sendWarning("unknown command");
			  }

		      } 
 		    else if (cmmd.equals("CONFIDENCE"))
		      {

			cmmd = (String)in.readObject();
			log(cmmd);
			if (cmmd.equals("ASC"))
			  {
			    sort(AssociationRule.CONFIDENCE, ASC);
			  }
			else if (cmmd.equals("DESC"))
			  {
			    sort(AssociationRule.CONFIDENCE, DESC);
			  }
			else
			  {
			    sendWarning("unknown command");
			  }
		      } 
		    else
		      {
			sendWarning("unknown command");
		      }
		  }
		else
		  {
		    sendWarning("unknown command");
		  }
	      }
	    else if (cmmd.equals("LOGIN"))
	      {
		loggedOn = true;
		Vector info = (Vector)in.readObject();
		log(info);
		login((String)info.get(0), (String)info.get(1));
	      }
	    else // exit if first command is not LOGIN
	      break;
	  }
	catch (Throwable e)
	  {
	    log(e);
	    break;
	  }
      }
  }

  /*
   * Creates the configuration object for this connection
   * verifies if the user is valid
   * sets true the boolean loggedOn
   * in case of errors throws an exception
   * that will break the loop and close the connection
   */
  private void login(String user, String password)
    throws ServerChildException, IOException
  {
    try
      {
	// create a configuration object for this connection
	dbconf = DBConfig.getDBConfig();
      }
    catch (DBConfigException e)
      {
	// send this error to the client too
	sendError(e.toString());
	// throws a ServerChildException which will exit the loop
	// of user requests and the connection will be closed
	throw new ServerChildException(e.toString());
      }

    try
      {
	dbconf.verifyPasswordForUser(user, password);

	this.user = user; // mark the owner of the connection
	
	// mark the user as being logged on
	dbconf.markLogged(user);

	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Mining
   */
  private void mine(String db, String alg, 
		    float minSupport, float minConfidence)
    throws IOException
  {
    try
      {
	AlgorithmManager algman = new AlgorithmManager();
	results = algman.mine(user, db, alg, minSupport, minConfidence);
	index = 0; // reset the pointer at the beginning of the data

	log(results.size() + " association rules were mined.");
	
	// prepare BATCH_SIZE association rules from results
	// or less if there are not enough association rules
	Vector results_to_send = new Vector(BATCH_SIZE);
	for (int i = 0; (i < BATCH_SIZE) && (index < results.size()); i++)
	  results_to_send.add((AssociationRule)results.get(index++));

	sendOK();
	// send first BATCH_SIZE association rules
	out.writeObject(results_to_send);
	out.flush();
      }
    catch (Exception e)
      {
	sendError(e.toString());
      }
  }
 
  // sorts the entire vector v according to the criteria
  // criteria can be AssociationRule.ANTECEDENT_SIZE, 
  // AssociationRule.CONSEQUENT_SIZE, AssociationRule.SUPPORT 
  // or AssociationRule.CONFIDENCE
  // type can be ASC or DESC
  private void sort(int criteria, int type)
    throws IOException
  {
    if (results.size() == 0)
      sendError("There are no results available");

    // reset index in the results vector
    index = 0;
    heapSort(results, criteria, type);
    
    // prepare BATCH_SIZE association rules from results
    // or less if there are not enough association rules
    Vector results_to_send = new Vector(BATCH_SIZE);
    for (int i = 0; (i < BATCH_SIZE) && (index < results.size()); i++)
      results_to_send.add((AssociationRule)results.get(index++));
    
    sendOK();
    // send first BATCH_SIZE association rules
    out.writeObject(results_to_send);
    out.flush();
  }

  // exchanges the values at index i and j in the vector v
  private static void exchange(Vector v, int i, int j)
  {
    Object temp = v.get(i);
    v.set(i, v.get(j));
    v.set(j, temp);
  }

  // helper method for HeapSort
  // criteria can be AssociationRule.ANTECEDENT_SIZE, 
  // AssociationRule.CONSEQUENT_SIZE, AssociationRule.SUPPORT 
  // or AssociationRule.CONFIDENCE
  // type can be ASC or DESC
  private static void siftDown(Vector v, int i, int n, int criteria, int type)
  {
    AssociationRule value = (AssociationRule)(v.get(i)); // value to sift down
      
    if (type == ASC)
      {
	for (int j = 2 * i + 1; j <= n; i = j, j = 2 * i + 1)
	  {
	    if (j < n)
	      // determine which child is greater
	      if (((AssociationRule)v.get(j + 1)).compareTo((AssociationRule)v.get(j), criteria) > 0)
		j++;

	    // break if children are smaller
	    if (((AssociationRule)v.get(j)).compareTo(value, criteria) <= 0)
	      break;

	    // move child up
	    v.set(i, v.get(j));
	  }
      }
    else
      {
	for (int j = 2 * i + 1; j <= n; i = j, j = 2 * i + 1)
	  {
	    if (j < n)
	      // determine which child is smaller
	      if (((AssociationRule)v.get(j + 1)).compareTo((AssociationRule)v.get(j), criteria) < 0)
		j++;
	    
	    // break if children are greater
	    if (((AssociationRule)v.get(j)).compareTo(value, criteria) >= 0)
	      break;

	    // move child up
	    v.set(i, v.get(j));
	  }
      }

    // place value into its final position
    v.set(i, value);
  }

  // heapSort will sort the elements 0 ... size() - 1 of array v
  // criteria can be AssociationRule.ANTECEDENT_SIZE, 
  // AssociationRule.CONSEQUENT_SIZE, AssociationRule.SUPPORT 
  // or AssociationRule.CONFIDENCE
  // type can be ASC or DESC
  private static void heapSort(Vector v, int criteria, int type)
  {
    int last = v.size() - 1;
      
    // heapify
    for (int i = (last - 1) / 2; i > 0; i--)
      siftDown(v, i, last, criteria, type);
      
    // the first siftDown call will finish the heapify step
    // then we will extract the top of the heap, exchange it
    // with the last element of the heap, decrease the size of 
    // the heap and start over again
    for (int i = last; i > 0; i--)
      {
	siftDown(v, 0, i, criteria, type);
	exchange(v, 0, i);
      }
  }

  /*
   * Returns next BATCH_SIZE association rules, or 
   * the rest of association rules if there are less
   * than BATCH_SIZE or an empty vector if all the association
   * rules have been already sent
   * This function does not return an ERROR message to the client
   */
  private void getNext()
    throws IOException
  {
    // prepare BATCH_SIZE association rules from results
    // or less if there are not enough association rules
    Vector results_to_send = new Vector(BATCH_SIZE);
    for (int i = 0; (i < BATCH_SIZE) && (index < results.size()); i++)
      results_to_send.add((AssociationRule)results.get(index++));
    
    sendOK();
    // send next BATCH_SIZE association rules (or less if there are not enough)
    // results_to_send is an empty vector if index became equal
    // to results.size()
    out.writeObject(results_to_send);
    out.flush();
    //log(results_to_send);
  }

  /*
   * Advanced mining
   */
  private void mineAdv(String db, String alg, 
		       float minSupport, float minConfidence,
		       Vector inAntecedent, Vector inConsequent,
		       Vector ignored,
		       int maxAntecedent, int minConsequent)
    throws IOException
  {
    try
      {
	AlgorithmManager algman = new AlgorithmManager();
	results = algman.advMine(user, db, alg, minSupport, minConfidence,
				 inAntecedent, inConsequent, ignored,
				 maxAntecedent, minConsequent);
	index = 0; // reset the pointer at the beginning of the data
	
	// prepare BATCH_SIZE association rules from results
	// or less if there are not enough association rules
	Vector results_to_send = new Vector(BATCH_SIZE);
	for (int i = 0; (i < BATCH_SIZE) && (index < results.size()); i++)
	  results_to_send.add((AssociationRule)results.get(index++));

	sendOK();
	// send first BATCH_SIZE association rules
	out.writeObject(results_to_send);
	out.flush();
	//log(results_to_send);
      }
    catch (Throwable e)
      {
	sendError(e.toString());
      }
  }
 
  /*
   * Deletes a database
   */
  private void delDb(String db)
    throws IOException
  {
    try
      {
	dbconf.deleteDatabase(user, db);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }
 
  /*
   * Changes the group of the algorithm
   */
  private void chgDbGrp(String db, String newgrp)
    throws IOException
  {
    try
      {
	dbconf.changeDatabaseGroup(user, db, newgrp);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Deletes an algorithm
   */
  private void delAlg(String alg)
    throws IOException
  {
    try
      {
	dbconf.deleteAlgorithm(user, alg);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Changes the group of the algorithm
   */
  private void chgAlgGrp(String alg, String newgrp)
    throws IOException
  {
    try
      {
	dbconf.changeAlgorithmGroup(user, alg, newgrp);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Adds a new group
   */
  private void addGrp(String group, Vector users)
    throws IOException
  {
    try
      {
	dbconf.addGroup(user, group, users);
	sendOK();
      }
    catch (DBConfigWarning w)
      {
	sendWarning(w.toString());
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Deletes a group
   */
  private void delGrp(String group)
    throws IOException
  {
    try
      {
	dbconf.deleteGroup(user, group);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Changes the name of the group
   */
  private void chgNameGrp(String group, String newName)
    throws IOException
  {
    try
      {
	dbconf.changeGroupName(user, group, newName);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Sets new users for a group
   */
  private void setUsrsForGrp(String group, Vector newUsers)
    throws IOException
  {
    try
      {
	dbconf.setUsersForGroup(user, group, newUsers);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Sets new groups for an user
   */
  private void setGrpsForUsr(String userName, Vector newGroups)
    throws IOException
  {
    try
      {
	dbconf.setGroupsForUser(user, userName, newGroups);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * adds a group to each user from a list of users
   */
  private void addGrpToUsrs(String group, Vector users)
    throws IOException
  {
    boolean all = true;

    try
      {
	for (int i = 0; i < users.size(); i++)
	  {
	    try
	      {
		dbconf.addUserToGroup(user, (String)users.get(i), group);
	      }
	    catch (DBConfigNonexistentUser e1)
	      {
		all = false;
	      }
	  }
	
	// have to see if it is OK or WARNING
	if (all)
	  sendOK();
	else
	  sendWarning("not all specified users are valid");
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * deletes a group from the list of users
   */
  private void delGrpFromUsrs(String group, Vector users)
    throws IOException
  {
    boolean all = true;

    try
      {
	for (int i = 0; i < users.size(); i++)
	  {
	    try
	      {
		dbconf.removeUserFromGroup(user, (String)users.get(i), group);
	      }
	    catch (DBConfigNonexistentUser e1)
	      {
		all = false;
	      }
	  }

	// have to see if it is OK or WARNING
	if (all)
	  sendOK();
	else
	  sendWarning("not all specified users are valid");
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * adds a user to each group from a list of groups
   */
  private void addUsrToGrps(String userName, Vector groups)
    throws IOException
  {
    boolean all = true;

    try
      {
	for (int i = 0; i < groups.size(); i++)
	  {
	    try
	      {
		dbconf.addUserToGroup(user, userName, (String)groups.get(i));
	      }
	    catch (DBConfigNonexistentGroup e1)
	      {
		all = false;
	      }
	  }
	  
	// have to see if it is OK or WARNING
	if (all)
	  sendOK();
	else
	  sendWarning("not all specified groups are valid.");
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * deletes a user from the list of groups
   */
  private void delUsrFromGrps(String userName, Vector groups)
    throws IOException
  {
    boolean all = true;

    try
      {
	for (int i = 0; i < groups.size(); i++)
	  {
	    try
	      {
		dbconf.removeUserFromGroup(user, userName, 
					   (String)groups.get(i));
	      }
	    catch (DBConfigNonexistentGroup e1)
	      {
		all = false;
	      }
	  }
	  
	// have to see if it is OK or WARNING
	if (all)
	  sendOK();
	else
	  sendWarning("not all specified groups are valid ");
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Adds a new user
   */
  private void addUsr(String userName, String password,
		      long permissions, Vector groups)
    throws IOException
  {
    try
      {
	dbconf.addUser(user, userName, password, permissions, groups);
	sendOK();
      }
    catch (DBConfigWarning w)
      {
	sendWarning(w.toString());
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Deletes the user
   */
  private void delUsr(String userName)
    throws IOException
  {
    try
      {
	dbconf.deleteUser(user, userName);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Changes the name for user
   */
  private void chgNameUsr(String userName, String newName)
    throws IOException
  {
    try
      {
	dbconf.changeUserName(user, userName, newName);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Changes the password for user
   */
  private void chgPassw(String userName, String password)
    throws IOException
  {
    try
      {
	dbconf.changePassword(user, userName, password);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Changes the permissions for user
   */
  private void chgPerm(String userName, long permissions)
    throws IOException
  {
    try
      {
	dbconf.changePermissions(user, userName, permissions);
	sendOK();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Sends the results of the benchmark process
   */
  private void benchmark(Vector dbs, Vector algs, Vector supps)
    throws IOException
  {
        try
      {
	AlgorithmManager algman = new AlgorithmManager();
	Vector results_to_send = algman.benchmark(user, dbs, algs, supps);

	sendOK();
	// send the vector containing the results of the minings
	out.writeObject(results_to_send);
	out.flush();
	//log(results_to_send);
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Sends a copy of the configuration object
   */
  private void getDBConfig()
    throws IOException
  {
    try
      {
	sendOK();
	DBConfig.sendUpdate(out);
	// out.flush() is done inside sendUpdate
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Sends to the client the list of columns associated with the 
   * database from the Vector dbs; in this version dbs will
   * contain only one element, the name of the database; 
   * in the future it might contain several
   */
  private void getColumns(Vector dbs)
    throws IOException
  {
    try
      {
	// find the path to the database
	String dbPath = dbconf.getPathDatabase((String)dbs.get(0));

	// create a DBReader for this database
	DBReader dbReader = new DBReader(dbPath);

	// ask a DBReader to return the columns for the database
	Vector dbCols = dbReader.getColumnNames();
	dbReader.close();
	
	sendOK();
	// send the result to the client
	out.writeObject(dbCols);
	out.flush();
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
  }

  /*
   * Adds a new database
   */
  private void addDb(Vector info)
    throws IOException, ClassNotFoundException 
  {
    Vector info1;

    String dbname = (String)info.get(0);

    try
      {
	dbconf.addDatabase(user, dbname, (String)info.get(1));
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }

    try
      {
	// find the directory of the database
	String dirName = dbconf.getDirDatabase(dbname);
	// find the path to the database
	String dbPath = dbconf.getPathDatabase(dbname);
	
	File dir = new File(dirName);
	    
	if (dir.mkdir() != true)
	  throw new IOException("Could not create database directory!");

	// create a DBWriter
	DBWriter dbw = new DBWriter(dbPath);
	    
	dbw.setColumnNames((Vector)info.get(2));
	dbw.setDescription((String)info.get(3));
	sendOK();	    

	// get the Itemsets
	while ((info1 = (Vector)in.readObject()).size() > 0)
	  {
	    //log("Another round saved to file");
	    //log(info1);
	    for (int i = 0; i < info1.size(); i++)
	      dbw.addRow((Itemset)info1.get(i));
	  }

	dbw.close();
	sendOK();
      }
    catch (Throwable e)
      {
	try
	  {
	    dbconf.deleteDatabase(user, dbname);
	    sendError(e.toString());
	  }
	catch (DBConfigException e2) 
	  {
	    sendError(e.toString() + " and " + e2.toString());
	  }
      }
  }

  /*
   * Adds a new synthetic database
   */
  private void genDB(String dbname, String group,
		     long num_transactions, int avg_transaction_size, 
		     int num_large_itemsets, int avg_large_itemset_size,
		     int num_items,
		     double correlation_mean, double corruption_mean)
    throws IOException, ClassNotFoundException 
  {
    try
      {
	dbconf.addDatabase(user, dbname, group);
      }
    catch (DBConfigException e)
      {
	sendError(e.toString());
      }
	
    try
      {
	// find the directory of the database
	String dirName = dbconf.getDirDatabase(dbname);
	// find the path to the database
	String dbPath = dbconf.getPathDatabase(dbname);

	File dir = new File(dirName);
	    
	if (dir.mkdir() != true)
	  throw new IOException("Could not create database directory!");

	// create a synthetic data generator
	SyntheticDataGenerator syngen =
	  new SyntheticDataGenerator(num_transactions,
				     avg_transaction_size,
				     num_large_itemsets,
				     avg_large_itemset_size,
				     num_items,
				     correlation_mean,
				     corruption_mean);
	
	// create a DBWriter
	DBWriter dbw = new DBWriter(dbPath);
	    
	// generate the column names C1, C2, ...
	Vector columnNames = new Vector();
	for (int i = 1; i <= num_items; i++)
	  columnNames.add("C" + i);
	    
	dbw.setColumnNames(columnNames);

	String description = "Synthetic database: " 
	  + "number of transactions=" + num_transactions 
	  + "; average transaction size=" + avg_transaction_size 
	  + "; number of large itemsets=" + num_large_itemsets 
	  + "; average large itemset size=" + avg_large_itemset_size 
	  + "; number of items=" + num_items 
	  + "; correlation=" + correlation_mean 
	  + "; corruption=" + corruption_mean;
	    
	dbw.setDescription(description);

	// get the Itemsets
	while (syngen.hasMoreTransactions())
	  {
	    Itemset is = syngen.getNextTransaction();
	    dbw.addRow(is);
	    //log(is);
	  }

	dbw.close();
	sendOK();
      }
    catch (Throwable e)
      {
	try
	  {
	    dbconf.deleteDatabase(user, dbname);
	    sendError(e.toString());
	  }
	catch (DBConfigException e2) 
	  {
	    sendError(e.toString() + " and " + e2.toString());
	  }
      }
  }

  /*
   * Adds a new algorithm
   */
  private void addAlg(Vector info)
    throws IOException 
  {
    String algname = (String)info.get(0);
    String grpname = (String)info.get(1);
    // get the number of bytes in the file
    int dim = ((Integer)info.get(2)).intValue(); 

    byte[] buf = new byte[dim];

    try
      {
	// read the next byte[] from the socket in order to 
	// save the information into a jar file
        int bytes_read = 0;
        while (bytes_read != dim)
	  {
	    int r = in.read(buf, bytes_read, dim - bytes_read);
	    if (r < 0)
	      break;
	    bytes_read += r;
	  }
        if (bytes_read != dim)
          throw new DBConfigException("Cannot read algorithm from socket");

	// we don't want to overwrite some file!
	File algjar = new File(algname + ".jar");
	if (algjar.exists())
	  throw new DBConfigException("File " + algname 
				      + ".jar already exists!");

	dbconf.addAlgorithm(user, algname, grpname);
      }
    catch (Throwable e)
      {
	sendError(e.toString());
      }
  
    try
      {
	// write the bytes in the file
	BufferedOutputStream alg_writer = 
	  new BufferedOutputStream(new FileOutputStream(algname
							+ ".jar"));
	
	alg_writer.write(buf, 0, dim);
	alg_writer.close();

	// send OK
	sendOK();
      }
    catch (Throwable e)
      {
	try
	  {
	    dbconf.deleteAlgorithm(user, algname);
	    sendError(e.toString());
	  }
	catch (DBConfigException e2) 
	  {
	    sendError(e.toString() + " and " + e2.toString());
	  }
      }
  }

  /*
   * Sends an OK message
   */
  private void sendOK()
    throws IOException
  {
    out.writeObject("OK");
    out.flush();
    log("Sent OK");
  }

  /*
   * Sends a warning message
   */
  private void sendWarning(String msg)
    throws IOException
  {
    Vector err = new Vector(1);
    err.add(msg);

    out.writeObject("WARNING");
    out.flush();
    out.writeObject(err);
    out.flush();
    log("Sent warning: " + err);
  }

  /*
   * Sends an error message
   */
  private void sendError(String msg)
    throws IOException
  {
    Vector err = new Vector(1);
    err.add(msg);

    out.writeObject("ERROR");
    out.flush();
    out.writeObject(err);
    out.flush();
    log("Sent error: " + err);
  }

  /*
   * Prints to System.out the connection id and a dump of data.
   */
  private void log(Object data)
  {
    System.out.println("<" + connectionID + "> " + data.toString());
  }
}
