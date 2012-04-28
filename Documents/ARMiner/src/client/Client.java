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

   (P)1999-2000 by ARMiner Client Team:

   Abdelmajid Karatihy
   Xiaoyong Kuang
   Lung-Tsung Li

*/

import java.net.*;
import java.io.*;
import java.util.Vector;

/*

  Maintenance log started on November 17th, 2000 by Laurentiu Cristofor

  Nov. 17th, 2000   - made some style changes
                    - made error messages a little more explicit

		    NOTE: The way errors are dealt with 
		    in the client application is intriguing at best 
                    but I lack the time to do the right thing.

                    - corrected some errors in dealing with errors in
                      several places, setDBConfig, getDBConfig, 
                      and getDBConfig1 being the most important

*/ 

/**

  Client.java<P>

  Client encapsulates the communication with the server.<P>

*/

public class Client
{
  /**
   * User's login name.
   *
   * @userName
   */
  public static String userName;

  /**
   * Database object from server side.
   *
   * @dbConfig
   */
  public static DBConfig dbConfig;

  /**
   * Output stream to send information from client to server.
   *
   * @out
   */
  public static ObjectOutputStream out ;

  /**
   * input stream to send information from client to server.
   *
   * @in
   */
  public static ObjectInputStream in ;
  
  /**
   * array to control the sort direction for mining result.
   *
   * @order
   */
  public static int[] order = {0, 0, 0, 0};

  private static String status;
  private static Vector response;

  private static final String strOK      = "OK";
  private static final String strERROR   = "ERROR";
  private static final String strWARNING = "WARNING";

  /**
   * Static method to initiate the static class when 
   * the class is first called.
   *
   */
  static
  {
    userName = "";
  }

  /**
   * login to server
   *
   * @param name     user name
   * @param password user password
   */
  public static Vector login(String name, String password)
  {
    Vector result = new Vector();
    Vector login = new Vector(2);
    login.add(name);
    login.add(password);

    try
      {
	out.reset();
	out.writeObject("LOGIN");
	out.writeObject(login);
	out.flush();

	//Get the server response.
	String response = (String)in.readObject();
	result.add(response);
	if (!response.equals(strOK))
	  result.add((Vector)in.readObject());
      }
    catch (Exception f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }

    return result;
  }

  public static void clientExit()
  {
    try
      {
	out.writeObject("EXIT");
	reset();
      }
    catch (IOException e)
      {
	System.err.println("An error occurred in Client.clientExit():\n\t" 
			   + e);
      }
  }
 
  /**
   * Static method to reset the static class value.
   *
   */
  public static void reset()
  {
    userName = "";

    try
      {
	if (in != null)
	  {
	    in.close();
	    in = null;
	  }

	if (out != null)
	  {
	    out.close();
	    out = null;
	  }
      }
    catch (Exception e)
      {
	System.err.println("An error occurred in Client.reset():\n\t" + e);
      }
  }

  /**
   * Delete an algorithm from the system.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes the algorithm's name to be deleted.
   * @return   The messages returned from server.
   */
  public static Vector deleteAlgorithms(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("DELALG");
	//System.out.println("deleteAlgorithms: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("deleteAlgorithms: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Delete Algorithms " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Delete Algorithms " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Add a new algorithm to the system.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes the algorithm's name and its group and number of bytes.
   * @return   The messages returned from server.
   */
  public static Vector addAlgorithms (Vector request, byte[] buff)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("ADDALG");
	out.writeObject(request);
	out.flush();
	out.write(buff);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("addAlgorithms: status: " + status); 

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Add Algorithms " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Add Algorithms " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Modify an algorithm's group access.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes the algorithm's name and its new group.
   * @return   The messages returned from server.
   */
  public static Vector modifyAlgorithms(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("CHGALGGRP");
	//System.out.println("modifyAlgorithms: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("modifyAlgorithms: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Modify Algorithms " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Modify Algorithms " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Add a new group to the system.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @exception ClientWarningException   The operation can be completed,
   * the warning message from the server will be thrown.
   * @param request  a vector includes a group's name and a vector of users.
   * @return   The messages returned from server.
   */
  public static Vector addGroup(Vector request)
    throws ClientErrorException, ClientWarningException
  {
    try 
      {
	out.reset();
	out.writeObject("ADDGRP");
	//System.out.println("addGroup: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("addGroup: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	if (status.equals(strERROR))
	  throw new ClientErrorException((String)response.get(0));
	else // if (status.equals(strWARNING))
	  throw new ClientWarningException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Add Group " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Add Group " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Delete a group from the system.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes a group's name to be removed.
   * @return   The messages returned from server.
   */
  public static Vector deleteGroup(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("DELGRP");
	//System.out.println("deleteGroup: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("deleteGroup: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Delete Group " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Delete Group " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Modify a group's name.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes a group's name and its new name.
   * @return   The messages returned from server.
   */
  public static Vector modifyGroupName(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("CHGNAMEGRP");
	//System.out.println("modifyGroupName: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("modifyGroupName: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Modify Group " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Modify Group " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Add groups for an user.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @exception ClientWarningException   The operation can be completed,
   * the warning message from the server will be thrown.
   * @param request  a vector includes a group's name and a vector of users.
   * @return   The messages returned from server.
   */
  public static Vector addGroupToUser(Vector request)
    throws ClientErrorException, ClientWarningException
  {
    try 
      {
	out.reset();
	out.writeObject("ADDGRPTOUSRS");
	//System.out.println("addGroupToUser: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("addGroupToUser: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	if (status.equals(strERROR))
	  throw new ClientErrorException((String)response.get(0));
	else //if (status.equals(strWARNING))
	  throw new ClientWarningException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Add group to user" + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Add group to user " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Set groups for an user.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @exception ClientWarningException   The operation can be completed,
   * the warning message from the server will be thrown.
   * @param request  a vector includes an user's name and a vector of groups.
   * @return   The messages returned from server.
   */
  public static Vector setGroupsForUser(Vector request)
    throws ClientErrorException, ClientWarningException
  {
    try 
      {
	out.reset();
	out.writeObject("SETGRPSFORUSR");
	//System.out.println("setGroupsForUser: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("setGroupsForUser: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	if (status.equals(strERROR))
	  throw new ClientErrorException((String)response.get(0));
	else //if (status.equals(strWARNING))
	  throw new ClientWarningException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Set groups for user" + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Set groups for user " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Remove group access for an user.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes a group's name and its users.
   * @return   The messages returned from server.
   */
  public static Vector deleteGroupFromUser(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("DELGRPFROMUSRS");
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Delete group from user" + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Delete group from user " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Add an user to the system.
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @exception ClientWarningException   The operation can be completed,
   * the warning message from the server will be thrown.
   * @param request  a vector includes an user's name, password, permission and groups to which the user belong.
   * @return   The messages returned from server.
   */
  public static Vector addUser(Vector request)
    throws ClientErrorException, ClientWarningException
  {
    try 
      {
	out.reset();
	out.writeObject("ADDUSR");
	//System.out.println("addUser: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("addUser: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	if (status.equals(strERROR))
	  throw new ClientErrorException((String)response.get(0));
	else //if (status.equals(strWARNING))
	  throw new ClientWarningException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Add user" + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Add user " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Delete an user from the system
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes an user's name.
   * @return   The messages returned from server.
   */
  public static Vector deleteUser(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("DELUSR");
	//System.out.println("deleteUser: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("addUser: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Delete user" + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Delete user " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Modify an user's name
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes an user name and a new name.
   * @return   The messages returned from server.
   */
  public static Vector modifyUserName(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("CHGNAMEUSR");
	//System.out.println("modifyUserName: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("modifyUserName: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Modify user name" + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Modify user name" + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Modify an user's password
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes an user name and password.
   * @return   The messages returned from server.
   */
  public static Vector modifyUserPassword(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("CHGPASSW");
	//System.out.println("modifyUserPassword: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("modifyUserPassword: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Modify user password " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Modify user password " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Modify an user's privilege
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @param request  a vector includes an user name and permission.
   * @return   The messages returned from server.
   */
  public static Vector modifyUserPrivlg(Vector request)
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("CHGPERM");
	//System.out.println("modifyUserPrivlg: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("modifyUserPrivlg: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	throw new ClientErrorException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Modify user permission " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Modify user permission " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Add an user to a group
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @exception ClientWarningException   The operation can be completed,
   * the warning message from the server will be thrown.
   * @param request  a vector includes an user name and a vector of groups.
   * @return   The messages returned from server.
   */
  public static Vector addUserToGroup(Vector request)
    throws ClientErrorException, ClientWarningException
  {
    try 
      {
	out.reset();
	out.writeObject("ADDUSRTOGRPS");
	//System.out.println("addUserToGroup: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("addUserToGroup: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	if (status.equals(strERROR))
	  throw new ClientErrorException((String)response.get(0));
	else //if (status.equals(strWARNING))
	  throw new ClientWarningException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Add user to group " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Add user to group " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Set user(s) for a group
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @exception ClientWarningException   The operation can be completed,
   * the warning message from the server will be thrown.
   * @param request  a vector includes an group name and a vector of users.
   * @return   The messages returned from server.
   */
  public static Vector setUsersForGroup(Vector request)
    throws ClientErrorException, ClientWarningException
  {
    try 
      {
	out.reset();
	out.writeObject("SETUSRSFORGRP");
	//System.out.println("setUsersForGroup: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("setUsersForGroup: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	if (status.equals(strERROR))
	  throw new ClientErrorException((String)response.get(0));
	else //if (status.equals(strWARNING))
	  throw new ClientWarningException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Set users for group " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Set users for group " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  /**
   * Remove an user from a group
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @exception ClientWarningException   The operation can be completed,
   * the warning message from the server will be thrown.
   * @param request  a vector includes an user name and a vector of groups.
   * @return   The messages returned from server.
   */
  public static Vector deleteUserFromGroup(Vector request)
    throws ClientErrorException, ClientWarningException
  {
    try 
      {
	out.reset();
	out.writeObject("DELUSRFROMGRPS");
	//System.out.println("deleteUserFromGroup: request: " + request.toString());
	out.writeObject(request);
	out.flush();

	status = (String)in.readObject();
	//System.out.println("deleteUserFromGroup: status: " + status);

	if (status.equals(strOK))
	  return null;

	response = (Vector)in.readObject();
	if (status.equals(strERROR))
	  throw new ClientErrorException((String)response.get(0));
	else //if (status.equals(strWARNING))
	  throw new ClientWarningException((String)response.get(0));
      }
    catch (IOException e)
      {
	//System.err.println("Client: Delete user from group " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: Delete user froms group " + e);
	throw new ClientErrorException(e.toString());
      }
  }

  public static Vector benchmark (Vector benchInput)
    throws ClientErrorException
  {
    Vector result;
    try 
      {
	out.reset();
	out.writeObject("BENCHMARK");
	out.writeObject(benchInput);
	out.flush();

	// Get the Server Response.
	String response = (String)in.readObject();
	result = new Vector();
	result.add(response);
	result.add((Vector)in.readObject());
      } 
    catch (IOException f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    catch (ClassNotFoundException f)
      {
	//System.err.println("Client: Benchmark " + f);
	throw new ClientErrorException(f.toString());
      }
    return result;
  }

  /**
   * Reset the DBConfig object in the class.
   *
   * @exception ClientException   All the Exception happened in client side
   */
  public static void setDBConfig() 
    throws ClientException
  {
    Vector feedback = getDBConfig();
    try
      {
	if (feedback.size() > 0)
	  {
	    String response = (String)feedback.get(0);
	    if (!response.equals(strOK))
	      {
		throw new ClientException("Cannot get DBConfig from server in Client.setDBConfig():\n " 
					  + feedback.get(1));
	      }
	    else
	      {
		DBConfig dbConfig = (DBConfig)feedback.get(1);
	      }
	  }
      }
    catch (Exception e)
      {
	throw new ClientException("Error in Client.setDBConfig():\n " + e);
      }
  }

  /**
   * Get Database object from server side.
   *
   */
  public static Vector getDBConfig()
  {
    Vector result;
    try
      {
	out.reset();
	out.writeObject("GETDBCONFIG");
	out.flush();

	//Get the server response.
	String response = (String)in.readObject();
	//System.err.println("got back: " + response);

	result = new Vector();
	result.add(response);

	if (response.equals(strOK))
	  result.add((DBConfig)in.readObject());
	else
	  result.add((Vector)in.readObject());
      }
    catch (Exception f)
      {
	//System.err.println(f);
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }

    return result;
  }

  /**
   * Get a recent DBConfig instance object
   * @exception ClientErrorException   The operation can not be completed,
   * the error message from the server will be thrown.
   * @return   Returns the unique instance of this class.
   */
  public static DBConfig getDBConfig1()
    throws ClientErrorException
  {
    try 
      {
	out.reset();
	out.writeObject("GETDBCONFIG");
	out.flush();

	status = (String)in.readObject();
	//System.out.println("getDBConfig1: status: " + status);
    
	if (!status.equals(strOK))
	  {
	    response = (Vector)in.readObject();
	    throw new ClientErrorException((String)response.get(0));
	  }
    
	dbConfig = (DBConfig)in.readObject();
	return dbConfig;
      }
    catch (IOException e)
      {
	//System.err.println("Client: getDBConfig1 " + e);
	throw new ClientErrorException(e.toString());
      }
    catch (ClassNotFoundException e)
      {
	//System.err.println("Client: getDBConfig1 " + e);
	throw new ClientErrorException(e.toString());
      }
  }


  /**
   * Get database column names from server side.
   *
   * @param db  Vector of database name
   */
  public static Vector getColumns(Vector db)
  {
    Vector result;
    try
      {
	out.reset();
	out.writeObject("GETCOLS");
	out.writeObject(db);
	out.flush();

	//Get the server response.
	String response = (String)in.readObject();

	result = new Vector();
	result.add(response);
	result.add((Vector)in.readObject());
      }
    catch (Exception f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    return result;
  }

  /**
   * send a mining request to the server
   *
   * @param action      String of mining type which includes 
   * "MINE" and "MINEADV"
   * @param mineInput   Vector of mining input.
   */
  public static Vector mine(String action, Vector mineInput)
  {
    Vector result;
    try
      {
	out.reset();
	out.writeObject(action);
	out.writeObject(mineInput);
	out.flush();

	String response = (String)in.readObject();

	result = new Vector();
	result.add(response);
	result.add((Vector)in.readObject());
      }
    catch (Exception f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    return result;
  }

  /**
   * Get next page of mining results.
   *
   */
  public static Vector getNext()
  {
    Vector result;
    try
      {
	out.reset();
	out.writeObject("GETNEXT");
	out.flush();

	String response = (String)in.readObject();

	result = new Vector();
	result.add(response);
	result.add((Vector)in.readObject());
      }
    catch (Exception f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    return result;
  }

  /**
   * request a sort of the mining results
   *
   * @param sortString
   */
  public static Vector sort(String action, String sortBy, String order)
  {
    Vector result;
    try
      {
	out.reset();
	out.writeObject(action);
	out.flush();
	out.writeObject(sortBy);
	out.flush();
	out.writeObject(order);
	out.flush();

	//Get the server response.
	String response = (String)in.readObject();

	result = new Vector();
	result.add(response);
	result.add((Vector)in.readObject());
      }
    catch (Exception f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    return result;
  }

  /**
   * add a database to server
   *
   * @param inFile    DBReader to read the file from file source
   * @param DBName    Database name
   * @param group    The group who is going to use this database
   */
  public static Vector addDB(DBReader inFile, String dbName, String group)
  {
    Vector result;
    try
      {
	Vector db = new Vector();
	db.add(dbName);
	db.add(group);
	db.add(inFile.getColumnNames());
	db.add(inFile.getDescription());

	out.writeObject("ADDDB");
	out.writeObject(db);
	out.flush();

	String response = (String)in.readObject();

	if (response.equals(strOK))
	  {
	    while (inFile.hasMoreRows())
	      {
		// we send 100 rows at a time
		// NOTE: the communication is not prefect here, we
		// should normally require an answer from the server for each 
		// batch sent
		Vector aVector = new Vector(100);
		for (int i = 0 ; i < 100 && inFile.hasMoreRows(); i++)
		  {
		    aVector.add(inFile.getNextRow());
		  }

		out.reset();
		out.writeObject(aVector);
		out.flush();
	      }

	    // we signal termination with an empty vector
	    out.writeObject(new Vector());
	    out.flush();

	    // get response from server to see if operation succeeded
	    response = (String)in.readObject();
	    result = new Vector();
	    result.add(response);
	    if (!response.equals(strOK))
	      result.add((Vector)in.readObject());
	  }
	else // response must have been ERROR
	  {
	    result = new Vector();
	    result.add(response);
	    result.add((Vector)in.readObject());
	  }
      }
    catch (Exception f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    return result;
  }

  /**
   * delete a database
   *
   * @param db    Vector of database name to be deleted
   */
  public static Vector delDB(Vector db)
  {
    Vector result;
    try
      {
	out.reset();
	out.writeObject("DELDB");
	out.writeObject(db);
	out.flush();

	//Get the server response.
	String response = (String)in.readObject();

	result = new Vector();
	result.add(response);
	if (!response.equals(strOK))
	  {
	    result.add((Vector)in.readObject());
	  }
      }
    catch (Exception f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    return result;
  }


  public static Vector  fsynthetic (Vector vsynthetic)
    throws ClientErrorException
  {
    Vector result;
    String response;
    try 
      {
	out.writeObject("GENDB");
	out.writeObject(vsynthetic);
	out.flush();

	response = (String)in.readObject();

	result = new Vector();
	result.add(response);
	if (!response.equals(strOK))
	  result.add((Vector)in.readObject()); 
      } 
    catch (Exception f)
      {   
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    return result;
  }


  /**
   * Change the database's user group.
   *
   * @param modificationInfo    Vector of input information for modifying a database,
   * it includes database name and new user group name
   */
  public static Vector modDB(Vector modificationInfo)
  {
    Vector result;
    try
      {
	out.reset();
	out.writeObject("CHGDBGRP");
	out.writeObject(modificationInfo);
	out.flush();

	//Get the server response.
	String response = (String)in.readObject();

	result = new Vector();
	result.add(response);
	if (!response.equals(strOK))
	  {
	    result.add((Vector)in.readObject());
	  }
      }
    catch (Exception f)
      {
	result = new Vector();
	result.add(strERROR);
	Vector msg = new Vector();
	msg.add(f.toString());
	result.add(msg);
      }
    return result;
  }
}


