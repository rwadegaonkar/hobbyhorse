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
import java.io.*;

/**

   DBConfig.java<P>

   DBConfig will keep the information related to users, groups, 
   algorithms, and databases and will provide locking mechanisms 
   in view of being used in a multithreaded environment.<P>
   
*/
/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public class DBConfig implements Serializable
{
  public static final int ADD_NEW_GROUPS          = 1;
  public static final int ADD_NEW_ALGORITHMS      = 2;
  public static final int ADD_NEW_DATABASES       = 4;

  public static final int CACHE_NOT_CREATED       = 0;
  public static final int CACHE_INITIAL_CREATION  = 1;
  public static final int CACHE_CREATED           = 2;
  public static final int CACHE_IN_CREATION       = 3;

  private static final int ADMIN_GID      = 0;
  private static final int ALL_GID        = 1;
  private static final int ADMIN_UID      = 0;
  private static final int ANONYMOUS_UID  = 1;

  private static final int FIRST_FREE_UID  = 2;
  private static final int FIRST_FREE_GID  = 2;

  private static final int FIRST_FREE_AID  = 2;
  private static final int FIRST_FREE_DBID = 1;

  public static final String CONFIG_FILE          = "arminer.cfg";

  // inner classes for group, user, algorithm and database informations

  // group informations
  class Group implements Serializable
  {
    int    gid;
    String name;
    int    uid; //owner

    Group(int gid, String name, int uid)
    {
      this.gid  = gid;
      this.name = name;
      this.uid  = uid; //owner
    }

    public String toString()
    {
      return new StringBuffer().append('<').append(gid).append(" ").append(name).append(" ").append(uid).append('>').toString();
    }
  }

  // user informations
  class User implements Serializable, Cloneable
  {
    int     uid;
    String  name;
    String  password;
    long    permissions;
    int     countLogged;
    boolean toBeDeleted;

    User(int uid, String name, String password, long permissions)
    {
      this.uid         = uid;
      this.name        = name;
      this.password    = password;
      this.permissions = permissions;
      countLogged      = 0;
      toBeDeleted      = false;
    }

    public String toString()
    {
      return new StringBuffer().append('<').append(uid).append(" ").append(name).append(" ").append(password).append(" ").append(permissions).append(" ").append(countLogged).append(" ").append(toBeDeleted).append('>').toString();
    }

    public Object clone()
      throws CloneNotSupportedException
    {
      return super.clone();
    }
  }

  // user - group association
  class UserGroup implements Serializable
  {
    int uid;
    int gid;

    UserGroup(int uid, int gid)
    {
      this.uid = uid;
      this.gid = gid;
    }

    public String toString()
    {
      return new StringBuffer().append('<').append(uid).append(" ").append(gid).append('>').toString();
    }
  }

  // algorithm informations
  class Algorithm implements Serializable
  {
    int     aid;
    String  name;
    int     uid; // owner
    int     gid;
    int     countUsage;
    boolean toBeDeleted;

    Algorithm(int aid, String name, int uid, int gid)
    {
      this.aid    = aid;
      this.name   = name;
      this.uid    = uid;
      this.gid    = gid;
      countUsage  = 0;
      toBeDeleted = false;
    }

    public String toString()
    {
      return new StringBuffer().append('<').append(aid).append(" ").append(name).append(" ").append(uid).append(" ").append(gid).append(" ").append(countUsage).append(" ").append(toBeDeleted).append('>').toString();
    }
  }

  //database informations
  class Database implements Serializable
  {
    int     dbid;
    String  name;
    int     uid; // owner
    int     gid;
    int     countUsage;
    boolean toBeDeleted;
    int     cacheStatus;
    double  cacheSupport;

    Database(int dbid, String name, int uid, int gid)
    {
      this.dbid    = dbid;
      this.name    = name;
      this.uid     = uid;
      this.gid     = gid;
      countUsage   = 0;
      toBeDeleted  = false;
      cacheStatus  = CACHE_NOT_CREATED;
      cacheSupport = 0.0;
    }

    public String toString()
    {
      return new StringBuffer().append('<').append(dbid).append(" ").append(name).append(" ").append(uid).append(" ").append(gid).append(" ").append(countUsage).append(" ").append(toBeDeleted).append(" ").append(cacheStatus).append(" ").append(cacheSupport).append('>').toString();
    }
  }

  // global configuration informations
  class Global implements Serializable
  {
    int nextUID;
    int nextGID;
    int nextAID;
    int nextDBID;
  
    Global()
    {
      nextUID  = 0;
      nextGID  = 0;
      nextAID  = 0;
      nextDBID = 0;
    }
  }

  // used for returning more information by different methods
  static class ID
  {
    int id;
    int index; // the index in the Vector
    
    ID(int id, int index)
    {
      this.id    = id;
      this.index = index;
    }
  }

  // private system data
  /** 
   * @serial
   */
  private Vector groups;
  /** 
   * @serial
   */
  private Vector users;
  /** 
   * @serial
   */
  private Vector usersGroups;
  /** 
   * @serial
   */
  private Vector algorithms;
  /** 
   * @serial
   */
  private Vector databases;
  
  // private configuration data
  /** 
   * @serial
   */
  private Global globalInfo;

  // THE DBConfig !
  /** 
   * @serial
   */
  private static DBConfig dbconfig = null; 
  
  // private constructor
  private DBConfig()
  {
    // create the global info object
    globalInfo = new Global();

    // create the first two groups, "admin" and "all"
    groups = new Vector();
    
    // the "admin" group has gid equal to ADMIN_GID
    // and the owner is the ADMIN_UID
    groups.add(new Group(ADMIN_GID, "admin", ADMIN_UID));

    // the "all" group has gid equal to ALL_GID
    // and the owner is the ADMIN_UID
    groups.add(new Group(ALL_GID, "all", ADMIN_UID));

    // adjust the nextGID in the global info object
    globalInfo.nextGID = FIRST_FREE_GID;

    // create the first two users, the admin and anonymous
    users = new Vector();

    // the "admin" user has all the permissions, can create
    // new groups, algorithms and databases
    users.add(new User(ADMIN_UID, "admin", "renimra", 
		       ADD_NEW_GROUPS | ADD_NEW_ALGORITHMS 
		       | ADD_NEW_DATABASES));

    // the "anonymous" user has no permission to create
    // new resources on the system
    users.add(new User(ANONYMOUS_UID, "anonymous", "", 0));

    // adjust the nextUID in the global info object
    globalInfo.nextUID = FIRST_FREE_UID;

    // create the user - group associations
    usersGroups = new Vector();
    
    // the user "admin" belongs to the group "admin"
    usersGroups.add(new UserGroup(ADMIN_UID, ADMIN_GID));

    // the user "anonymous" belongs to the group "all"
    usersGroups.add(new UserGroup(ANONYMOUS_UID, ALL_GID));

    // create the list of algorithms
    algorithms = new Vector();

    // create the list of databases
    databases  = new Vector();

    // This part will be deleted from the final version

    // add the algorithm ''Apriori'', with aid 0, owner ADMIN_UID
    // and gid ALL_GID
    algorithms.add(new Algorithm(0, "Apriori", ADMIN_UID, ALL_GID));
    // add the algorithm ''Closure'', with aid 1, owner ADMIN_UID
    // and gid ALL_GID
    algorithms.add(new Algorithm(1, "Closure", ADMIN_UID, ALL_GID));

    // add the database "csc.db", with dbid 0, owner ADMIN_UID
    // and gid ALL_GID
    databases.add(new Database(0, "csc.db", ADMIN_UID, ALL_GID));
  }
  
  // private methods

  // returns the next available user id
  private int getNextUID()
  {
    int i;
    int id = globalInfo.nextUID;

    // check if it is in use
    while (true)
      {
	for (i = 0; i < users.size(); i++)
	  if (((User)users.get(i)).uid == id) 
	    break;
	
	// id is not in use
	if (i == users.size())
	  {
	    globalInfo.nextUID++;
	    if (globalInfo.nextUID < 0)
	      globalInfo.nextUID = FIRST_FREE_UID;
	    return id;
	  }
	// id is in use
	else
	  id = ++globalInfo.nextUID;
      }
  }

  // returns the next available group id
  private int getNextGID()
  {
    int i;
    int id = globalInfo.nextGID;

    // check if it is in use
    while (true)
      {
	for (i = 0; i < groups.size(); i++)
	  if (((Group)groups.get(i)).gid == id) 
	    break;

	// id is not in use
	if (i == groups.size())
	  {
	    globalInfo.nextGID++;
	    if( globalInfo.nextGID < 0 )
	      globalInfo.nextGID = FIRST_FREE_GID;
	    return id;
	  }
	// id is in use
	else
	  id = ++globalInfo.nextGID;
      }
  }

  // returns the next available algorithm id
  private int getNextAID()
  {
    int i;
    int id = globalInfo.nextAID;

    // check if it is in use
    while (true)
      {
	for (i = 0; i < algorithms.size(); i++)
	  if (((Algorithm)algorithms.get(i)).aid == id) 
	    break;
	
	// id is not in use
	if (i == algorithms.size())
	  {
	    globalInfo.nextAID++;
	    if ( globalInfo.nextAID < 0 )
	      globalInfo.nextAID = FIRST_FREE_AID;
	    return id;
	  }
	// id is in use
	else
	  id = ++globalInfo.nextAID;
      }
  }

  // returns the next available database id
  private int getNextDBID()
  {
    int i;
    int id = globalInfo.nextDBID;

    // check if it is in use
    while (true)
      {
	for (i = 0; i < databases.size(); i++)
	  if (((Database)databases.get(i)).dbid == id) 
	    break;

	// id is not in use
	if (i == databases.size())
	  {
	    globalInfo.nextDBID++;
	    if ( globalInfo.nextDBID < 0 )
	      globalInfo.nextDBID = FIRST_FREE_DBID;
	    return id;
	  }
	// id is in use
	else
	  id = ++globalInfo.nextDBID;
      }
  }

  // returns the user uid for the user with the name specified as
  // argument and the index in the users list, 
  // throws a DBConfigNonexistentUser exception if the user with that name
  // does not exist
  private ID getUid(String user) throws DBConfigNonexistentUser
  {
    for (int i = 0; i < users.size(); i++)
      if (((User)users.get(i)).name.equals(user)) 
	return new ID(((User)users.get(i)).uid, i);

    throw new DBConfigNonexistentUser("Nonexistent user (" + user + ").");
  }

  // returns the group gid associated with the group whose name is
  // specified as argument and the index in the groups list, 
  // throws a DBConfigNonexistentGroup exception if the group
  // with that name does not exist
  private ID getGid(String group) throws DBConfigNonexistentGroup
  {
    for (int i = 0; i < groups.size(); i++)
      if (((Group)groups.get(i)).name.equals(group)) 
	return new ID(((Group)groups.get(i)).gid, i);

    throw new DBConfigNonexistentGroup("Nonexistent group ( " + group + " ).");
  }

  // returns the algorithm aid of the algorithm whose name is 
  // specified as argument and the index in the algorithms list, 
  // throws a DBConfigNonexistentAlgorithm exception if the
  // algorithm with that name does not exist
  private ID getAid(String algorithm) throws DBConfigNonexistentAlgorithm
  {
    for (int i = 0; i < algorithms.size(); i++)
      if (((Algorithm)algorithms.get(i)).name.equals(algorithm)) 
	return new ID(((Algorithm)algorithms.get(i)).aid, i);

    throw new DBConfigNonexistentAlgorithm("Nonexistent algorithm ( " + algorithm + " ).");
  }

  // returns the database dbid of the database whose name is 
  // specified as argument and the index in the databases list, 
  // throws a DBConfigNonexistentDatabase exception if the
  // database with that name does not exist
  private ID getDbid(String database) throws DBConfigNonexistentDatabase
  {
    for (int i = 0; i < databases.size(); i++)
      if (((Database)databases.get(i)).name.equals(database)) 
	return new ID(((Database)databases.get(i)).dbid, i);

    throw new DBConfigNonexistentDatabase("Nonexistent database ( " + database+ " ).");
  }

  // checks if the user having uid uID.id is the owner of the group
  // having gID.id, throws a DBConfigException if it is not
  private void check_owner_group(ID uID, ID gID) throws DBConfigException
  {
    // check if uID.id is the owner of gID.id  
    if (((Group)groups.get(gID.index)).uid != uID.id)
      throw new DBConfigException("User is not the owner of the group.");
  }

  // checks if the user having uid uID.id is the owner of the algorithm
  // having aid aID.id, throws a DBConfigException if it is not
  private void check_owner_algorithm(ID uID, ID aID) throws DBConfigException
  {
    // check if uid is the owner of aid  
    if (((Algorithm)algorithms.get(aID.index)).uid != uID.id)
      throw new DBConfigException("User is not the owner of the algorithm.");
  }

  // checks if the user having uid uID.id is the owner of the database
  // having dbid dbID.id, throws a DBConfigException if it is not
  private void check_owner_database(ID uID, ID dbID) throws DBConfigException
  {
    // check if uid is the owner of dbid  
    if (((Database)databases.get(dbID.index)).uid != uID.id)
      throw new DBConfigException("User is not the owner of the database");
  }

  // checks if uid uID.id belongs to the "admin" group
  // throws a DBConfigException if not
  private void check_belong_admin(ID uID) throws DBConfigException
  {
    for (int i = 0; i < usersGroups.size(); i++)
      if (((UserGroup)usersGroups.get(i)).uid == uID.id &&
	  ((UserGroup)usersGroups.get(i)).gid == ADMIN_GID) 
	return;

    throw new DBConfigException("User does not belong to the admin group.");
  }

  // checks if uid uID.id has permissions to add new groups
  // throws a DBConfigException if not
  private void check_perm_groups(ID uID) throws DBConfigException
  {
    // if the uid has permissions to add new groups return
    if ((((User)users.get(uID.index)).permissions & ADD_NEW_GROUPS) ==
	ADD_NEW_GROUPS)
      return;
	
    throw new DBConfigException("User has no permission to add new groups.");
  }  

  // checks if uid uID.id has permissions to add new algorithms
  // throws a DBConfigException if not
  private void check_perm_algorithms(ID uID) throws DBConfigException
  {
    // if the uid has permissions to add new algorithms return
    if ((((User)users.get(uID.index)).permissions 
	 & ADD_NEW_ALGORITHMS) == ADD_NEW_ALGORITHMS)
      return;

    throw new DBConfigException("User has no permission to add new algorithms.");
  }  
  
  // checks if uid uID.id has permissions to add new databases
  // throws a DBConfigException if not
  private void check_perm_databases(ID uID) throws DBConfigException
  {
    // if the uid has permissions to add new databases return
    if ((((User)users.get(uID.index)).permissions & ADD_NEW_DATABASES) ==
	ADD_NEW_DATABASES)
      return;

    throw new DBConfigException("User has no permission to add new databases.");
  }  

  // delete the user with the ID uID
  // deletes the user from the users list
  // deletes all the user - group associations
  // all the resources with the owner user will be
  // inherited by the user ADMIN_UID
  private synchronized void deleteUser(ID uID)
  {
    int i;

    // remove user from users list
    users.remove(uID.index);
    
    // remove all uid - gid associations from usersGroups list
    for (i = 0; i < usersGroups.size(); i++)
      if (((UserGroup)usersGroups.get(i)).uid == uID.id)
	{
	  usersGroups.remove(i);

	  //stay on the same position
	  i--;
	}
    
    // all the groups with the owner uid will have the owner ADMIN_UID
    for (i = 0; i < groups.size(); i++)
      if ( ((Group)groups.get(i)).uid == uID.id)
	((Group)groups.get(i)).uid = ADMIN_UID;

    // all the algorithms with the owner uid will have the owner ADMIN_UID
    for (i = 0; i < algorithms.size(); i++)
      if(((Algorithm)algorithms.get(i)).uid == uID.id)
	((Algorithm)algorithms.get(i)).uid = ADMIN_UID;

    // all the databases with the owner uid will have the owner ADMIN_UID
    for (i = 0; i < databases.size(); i++)
      if(((Database)databases.get(i)).uid == uID.id)
	((Database)databases.get(i)).uid = ADMIN_UID;
  }

  // returns the gid of the database with dbid dbID.id
  // dbID should be a valid id
  private int getGidOfDatabase(ID dbID)
  {
    return ((Database)databases.get(dbID.index)).gid; 
  }

  // returns the gid of the algorithm with aid aID.id
  // aid should be a valid id
  private int getGidOfAlgorithm(ID aID)
  {
    return ((Algorithm)algorithms.get(aID.index)).gid; 
  }

  // eliminates duplicates from the Vector v
  // leaving only distinct values in v
  private void eliminateDuplicates(Vector v)
  {
    Hashtable ht = new Hashtable();

    for(int i = 0; i < v.size(); i++)
      ht.put(v.get(i), "");

    v.clear();
    Enumeration keys = ht.keys();

    while (keys.hasMoreElements())
      v.add(keys.nextElement());
  }

  /** 
   * DBConfig is a Singleton, and this is the method that
   * allows you to get a reference to it.
   *
   * @return   Returns the unique instance of this class.
   * If a serialized version of the instance exists in the file
   * DBConfig.CONFIG_FILE this version is returned, otherwise
   * a new instance is created and returned.
   * @exception DBConfigException   The operation can not be completed.
   */
  public static synchronized DBConfig getDBConfig()
    throws DBConfigException
  {
    // if the dbconfig reference is not null, return it
    if (dbconfig != null)
      return dbconfig;
    
    try
      {
	File file = new File(CONFIG_FILE);

	if (file.exists())
	  {
	    // a serialized version of this object exists
	    // deserialize the object
	    ObjectInputStream input = 
	      new ObjectInputStream(new FileInputStream(file));
	    dbconfig = (DBConfig)input.readObject();
	    input.close();
	  }
	else
	  dbconfig = new DBConfig();

	// return the reference
	return dbconfig;
      }
    catch (Exception e)
      {
	throw new DBConfigException("File corrupted " + CONFIG_FILE + ".");
      }
  }

  /** 
   * Serializes the unique instance of this class in the file
   * DBConfig.CONFIG_FILE
   *
   * @exception DBConfigException   The operation can not be completed.
   */
  public static synchronized void saveDBConfig()
    throws DBConfigException
  {
    // the dbconfig should not be null
    // since this function should be called after getDBConfig
    if (dbconfig == null)
      throw new DBConfigException("Internal error - nothing to serialize.");

    try
      {
	ObjectOutputStream output = 
	  new ObjectOutputStream(new FileOutputStream(CONFIG_FILE));
	output.writeObject(dbconfig);
	output.close();
      }
    catch (Exception e)
      {
	System.out.println(e);
	throw new DBConfigException("Error writing " + CONFIG_FILE + ".");
      }
  }

  /**
   * Sends a DBConfig object to the stream received as
   * argument.
   *
   * @param out  The stream on which the serialization of the DBConfig will
   * be put.
   * @exception DBConfigException   The operation can not be completed.
   * @exception IOException
   */
  public static synchronized void sendUpdate(ObjectOutputStream out)
    throws DBConfigException, IOException
  {
    // create a brand new DBConfig object that will be filled with
    // the values of THE DBconfig object, with the exception of the
    // passwords (we don't want to send them to clients)
    DBConfig db_to_send = new DBConfig();

    if (dbconfig == null)
      throw new DBConfigException("Internal error, null DBConfig reference.");
    
    // set the GlobalInfo reference
    db_to_send.globalInfo = dbconfig.globalInfo;

    // remove all users from db_to_send
    db_to_send.users.clear();
    // add clones of all the users from dbconfig
    for (int i = 0; i < dbconfig.users.size(); i++)
      try
	{
	  db_to_send.users.add(((User)dbconfig.users.get(i)).clone());
	}
      catch (CloneNotSupportedException e)
	{
	  throw new DBConfigException("Internal error, cannot clone a User!");
	}
    // delete all the passwords
    for (int i = 0; i < db_to_send.users.size(); i++)
      ((User)(db_to_send.users.get(i))).password = "";

    // set all the groups from dbconfig
    db_to_send.groups = dbconfig.groups;
    
    // set all the algorithms from dbconfig
    db_to_send.algorithms = dbconfig.algorithms;

    // set all the databases from dbconfig
    db_to_send.databases = dbconfig.databases;

    // set all the usersGroups from dbconfig
    db_to_send.usersGroups = dbconfig.usersGroups;

    // first reset the stream since it likes to cache references
    // and we don't want that to happen
    out.reset();
        
    // write the new dbconfig object on the output stream
    out.writeObject(db_to_send);
    out.flush();
  }

  /**
   * Reads the DBConfig from the input stream received as argument.
   *
   * @param  in   The stream from which the DBConfig is read.
   * @exception OptionalDataException
   * @exception ClassNotFoundException
   * @exception IOException
   */
  public static synchronized void updateFrom(ObjectInputStream in)
    throws OptionalDataException, ClassNotFoundException, IOException
  {
    dbconfig = (DBConfig)in.readObject();
  }

  // the first parameter of all the following methods identifies the
  // user that makes the call such that the system can check what is
  // he actually allowed to do.

  // in the following group related methods, variable name refers to
  // group name.
  
  /**
   * Adds a new group and specifies what users belong to it.
   * The <code>user</code> will be the owner of the group. 
   * The owner of the group belongs also to the group. The owner can be
   * included or not in the list of users specified as the third argument,
   * it will be added anyway to the group.
   *
   * @exception DBConfigException The operation can not be completed.
   * @param user   The owner of the new group. This user should have
   * permissions to add new groups in the system.
   * @param name   The name of the new group.
   * @param users   The list of users that belong to the new group.
   */
  public synchronized void addGroup(String user, String name, Vector users)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null 
	|| users == null)
      throw new DBConfigException("Invalid addGroup() parameters!");

    try
      {
	for (int i = 0; i < users.size(); i++)
	  {
	    String u = (String)users.get(i);
	    if (u == null)
	      throw new DBConfigException("Invalid addGroup() parameters!");
	  }
      }
    catch (ClassCastException e)
      {
	throw new DBConfigException("Invalid addGroup() parameters!");
      }

    // if the user does not exist
    // a DBConfigNonexistentUser is thrown
    ID uID = getUid(user);

    // check if uid has permissions to add new groups
    // throws a DBConfigException if not
    check_perm_groups(uID);

    // check if there is already a group with same name
    try
      {
	getGid(name);
      }
    catch (DBConfigNonexistentGroup e)
      {
	// the name of this group is not already in use
	int gid;
	// add the new group to the groups list
	groups.add(new Group(gid = getNextGID(), name, uID.id));

	// check if owner is in the list of users
	boolean in_list = false;
	for (int i = 0; i < users.size(); i++)
	  if (users.get(i).equals(user))
	    in_list = true;

	// add the owner - group association
	// if owner is not in the list of users
	if (!in_list)
	  usersGroups.add(new UserGroup(uID.id, gid));
	
	// eliminate possible duplicates of users
	eliminateDuplicates(users);

	// add the user - group associations
	// for all users in the list
	boolean all = true;
	for (int i = 0; i < users.size(); i++)
	  {
	    try
	      {
		// throws a DBConfigNonexistentUser if the user does not exist
		uID = getUid((String)users.get(i));
		usersGroups.add(new UserGroup(uID.id, gid));
	      }
	    catch(DBConfigNonexistentUser e1)
	      {
		all = false;
	      }
	  }
	
	// save DBConfig object to the disk
	saveDBConfig();

	if (all)
	  return;
	else
	  throw new DBConfigWarning("Not all specified users are valid users.");
      }

    // if this point is reached, no exception was thrown in the try above
    // this name of the group is already in use
    // can not complete The operation
    throw new DBConfigException("The group name " + name + " is already in use.");
  }
  
  /**
   * Deletes a group. 
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should be the owner of the group or he should belong to the
   * admin group.
   * @param name   The name of the group to be deleted.
   */
  public synchronized void deleteGroup(String user, String name) 
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null)
      throw new DBConfigException("Invalid deleteGroup() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID gID = getGid(name);

    if (gID.id < FIRST_FREE_GID)
      throw new DBConfigException("Cannot remove " + name);

    for (int i = 0; i < algorithms.size(); i++)
      {
	Algorithm alg = (Algorithm)algorithms.get(i);
	if (alg.gid == gID.id)
	  throw new DBConfigException("Cannot delete this group since it is used by algorithm " + alg.name);
      }

    for (int i = 0; i < databases.size(); i++)
      {
	Database db = (Database)databases.get(i);
	if (db.gid == gID.id)
	  throw new DBConfigException("Cannot delete this group since it is used by database " + db.name);
      }

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner

	// look for gid in the list of groups 
	// if uid is not the owner of gid a DBConfigException 
	// is thrown
	check_owner_group(uID, gID);
      }
	
    // if this point is reached uid is in admin or
    // uid is the owner of the group gid

    // remove the group from the list
    groups.remove(gID.index);
    
    // remove all user - group associations
    for (int j = 0; j < usersGroups.size(); j++)
      if (((UserGroup)usersGroups.get(j)).gid == gID.id)
	{
	  // remove this user - group association
	  usersGroups.remove(j);
	  // stay on the same position
	  j--;
	}
    
    // save DBConfig object to the disk
    saveDBConfig();
  }

  /**
   * Changes the name of a group.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should be the owner of the group or he should belong to the
   * admin group.
   * @param name   The old name of the group.
   * @param newName   The new name of the group.
   */
  public synchronized void changeGroupName(String user, String name, 
					   String newName)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null
	|| newName == null)
      throw new DBConfigException("Invalid changeGroupName() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID gID = getGid(name);

    if (gID.id < FIRST_FREE_GID)
      throw new DBConfigException("Cannot change the name of this group.");

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner

	// look for gid in the list of groups 
	// if uid is not the owner of gid a DBConfigException 
	// is thrown
	check_owner_group(uID, gID);
      }

    // if this point is reached uid is in admin or
    // uid is the owner of the group gid

    // check if the new name is not in use
    try 
      {
	getGid(newName);
      }
    catch (DBConfigException e)
      {
	// the new name is not already in use
	// change the old name to the new name
	((Group)groups.get(gID.index)).name = newName;

	// save DBConfig object to the disk
	saveDBConfig();
	return;
      }
    
    // if this point is reached, no exception was thrown in the try above    
    // newName is already in use, can not change the old name
    // to the newName
    throw new DBConfigException(newName + " is already in use.");
  }

  /**
   * Add a user to a group.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should be the owner of the group or he should belong to the
   * admin group.
   * @param name   The name of the group.
   * @param userNameh   The name of the user to be added to the group.
   */
  public synchronized void addUserToGroup(String user, String name, 
					  String userName)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null
	|| userName == null)
      throw new DBConfigException("Invalid addUserToGroup() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID  = getUid(user);
    ID gID  = getGid(name);
    ID uID1 = getUid(userName); // user to be added to the group

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner

	// look for gid in the list of groups 
	// if uid is not the owner of gid a DBConfigException 
	// is thrown
	check_owner_group(uID, gID);
      }

    // if this point is reached uid is in admin or
    // uid is the owner of the group gid

    // check if the pair uid1 - gid is already in the usersGroups list
    for (int i = 0; i < usersGroups.size(); i++)
      if (((UserGroup)usersGroups.get(i)).gid == gID.id && 
	  ((UserGroup)usersGroups.get(i)).uid == uID1.id )
	// the pair uid1 - gid is already in the usersGroups list
	// no need to add it again
	return;

    // add user - group association
    usersGroups.add(new UserGroup(uID1.id, gID.id));

    // save DBConfig object to the disk
    saveDBConfig();
  }
  
  /**
   * Removes a user from a group.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should be the owner of the group or he should belong to the
   * admin group. The owner of the group can not be deleted from the group. 
   * @param name   The name of the group.
   * @param userName   The name of the user to be removed from the group.
   */
  public synchronized void removeUserFromGroup(String user, String name, 
					       String userName)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null
	|| userName == null)
      throw new DBConfigException("Invalid removeUserFromGroup() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID  = getUid(user);
    ID gID  = getGid(name);
    ID uID1 = getUid(userName); // user to be removed from the group

    if (uID1.id == ADMIN_UID && gID.id == ADMIN_GID)
      throw new DBConfigException("Cannot remove user admin from group admin.");
    if (gID.id == ALL_GID)
      throw new DBConfigException("Cannot remove a user from group all.");

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner

	// look for gid in the list of groups 
	// if uid is not the owner of gid a DBConfigException 
	// is thrown
	check_owner_group(uID, gID);

	// if the owner of the group is also the user
	// to be deleted, just return
	if (user.equals(userName))
	  throw new DBConfigException("Cannot remove group owner from group.");
      }

    // if this point is reached uid is in admin or
    // uid is the owner of the group gid
    // and uid is not equal to uid1

    for (int i = 0; i < usersGroups.size(); i++)
      if (((UserGroup)usersGroups.get(i)).gid == gID.id && 
	  ((UserGroup)usersGroups.get(i)).uid == uID1.id )
	{
	  // remove the uid1 - gid pair from the list
	  // of user - group associations
	  usersGroups.remove(i);

	  // uid1 - gid should be unique
	  // save DBConfig object to the disk
	  saveDBConfig();
	  return;
	}
  }

  /**
   * Sets for an user the new list of groups to which the user belongs.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should belong to the admin group. 
   * @param userName   The name of the user whose groups will be changed.
   * @param newGroups   The list of the new groups.
   */
  public synchronized void setGroupsForUser(String user, String userName, 
					    Vector newGroups)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| userName == null
	|| newGroups == null)
      throw new DBConfigException("Invalid setGroupsForUser() parameters!");

    try
      {
	for (int i = 0; i < newGroups.size(); i++)
	  {
	    String group = (String)newGroups.get(i);
	    if (group == null)
	      throw new DBConfigException("Invalid setGroupsForUser() parameters!");
	  }
      }
    catch (ClassCastException e)
      {
	throw new DBConfigException("Invalid setGroupsForUser() parameters!");
      }

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID uID1 = getUid(userName); // user whose groups will be modified

    // check if uid is the admin
    check_belong_admin(uID);

    // get a list of old groups to which user belongs
    Vector oldGroups = listGroupsForUser(userName);

    // delete all the groups from the old_groups
    // except the ones owned by userName or group 'all'
    for (int i = 0; i < oldGroups.size(); i++)
      if (!userName.equals(getGroupOwner((String)oldGroups.get(i)))
	  && !oldGroups.get(i).equals("all"))
	removeUserFromGroup(user, (String)oldGroups.get(i), userName);

    // make sure 'all' is among the groups we set
    newGroups.add("all");
    eliminateDuplicates(newGroups);

    // add the new groups
    for (int i = 0; i < newGroups.size(); i++)
      addUserToGroup(user, (String)newGroups.get(i), userName);
  }

  /**
   * Sets for a group the new list of users that belong to this group.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should belong to the admin group or should be the owner of the group. 
   * @param groupName   The name of the group whose users will be changed.
   * @param newUsers   The list of the new users.
   */
  public synchronized void setUsersForGroup(String user, String groupName, 
					    Vector newUsers)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| groupName == null
	|| newUsers == null)
      throw new DBConfigException("Invalid setUsersForGroup() parameters!");

    try
      {
	for (int i = 0; i < newUsers.size(); i++)
	  {
	    String u = (String)newUsers.get(i);
	    if (u == null)
	      throw new DBConfigException("Invalid setUsersForGroup() parameters!");
	  }
      }
    catch (ClassCastException e)
      {
	throw new DBConfigException("Invalid setUsersForGroup() parameters!");
      }

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID gID  = getGid(groupName);

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner
	
	// look for gid in the list of groups 
	// if uid is not the owner of gid a DBConfigException 
	// is thrown
	check_owner_group(uID, gID);
      }

    // if this point is reached uid is in admin or
    // uid is the owner of the group gid

    // get a list of old users belonging to the group
    Vector oldUsers = listUsersForGroup(groupName);

    String groupOwner = getGroupOwner(groupName);

    // delete all the users from the old_users
    for (int i = 0; i < oldUsers.size(); i++)
      if (!groupOwner.equals((String)oldUsers.get(i)))
	removeUserFromGroup(user, groupName, (String)oldUsers.get(i));

    eliminateDuplicates(newUsers);

    // add the new users
    for (int i = 0; i < newUsers.size(); i++)
      addUserToGroup(user, groupName, (String)newUsers.get(i));
  }

  // in the following user related methods, variable name refers to
  // user name.
  
  /**
   * Adds a new user and specify to what groups it belongs.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code>
   * should belong to the admin group.
   * @param name   The name of the user to be added.
   * @param password   The password of the user to be added.
   * @param permissions   The permissions of the user to be added.
   * @param groups   The list of groups to which the user to be added belongs. 
   */
  public synchronized void addUser(String user, String name, String password, 
				   long permissions, Vector groups)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null
	|| password == null
	|| groups == null)
      throw new DBConfigException("Invalid addUser() parameters!");

    try
      {
	for (int i = 0; i < groups.size(); i++)
	  {
	    String group = (String)groups.get(i);
	    if (group == null)
	      throw new DBConfigException("Invalid addUser() parameters!");
	  }
      }
    catch (ClassCastException e)
      {
	throw new DBConfigException("Invalid addUser() parameters!");
      }

    // throws a DBConfigException if the user does not exist
    ID uID  = getUid(user); // initiator of the operation
    int uid1;
    ID gID;

    // check if uid belongs to admin
    check_belong_admin(uID);

    // check if the user is already installed on the system
    try 
      {
	getUid(name);
      }
    catch (DBConfigException e)
      {
	// if user belongs to admin then he must have full permissions
	for (int i = 0; i < groups.size(); i++)
	  if (groups.get(i).equals("admin"))
	    {
	      permissions = ADD_NEW_GROUPS | ADD_NEW_ALGORITHMS 
		| ADD_NEW_DATABASES;
	      break;
	    }

	// the name is not already in use
	// add the new user to the users list
	users.add(new User(uid1 = getNextUID(), name, password, permissions));

	// add the group "all" to the rest of groups
	groups.add("all");
	eliminateDuplicates(groups);

	boolean all = true;
	// add all user - group associations
	for (int i = 0; i < groups.size(); i++)
	  {
	    try
	      {
		// throws a DBConfigException if the group does not exist
		gID = getGid((String)groups.get(i));

		// add the pair uid1 - gid to the usersGroups list
		usersGroups.add(new UserGroup(uid1, gID.id));
	      }
	    catch (DBConfigException e1)
	      {
		// one of the groups is not valid
		all = false;
	      }
	  }

	// save DBConfig object to the disk
	saveDBConfig();
	    
	if (all) 
	  return;
	else
	  throw new DBConfigWarning("Not all specified groups are valid groups.");
      }

    // if this point is reached, no exception was thrown in the try above
    // newName is already in use, can not add a user with this name
    throw new DBConfigException("The user name " + name + " is already in use.");
  }

  /**
   * Deletes a user. All the resources owned by the user will be inherited
   * by the admin user.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code>
   * should belong to the admin group.
   * @param name   The name of the user to be deleted.
   */
  public synchronized void deleteUser(String user, String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null)
      throw new DBConfigException("Invalid deleteUser() parameters!");

    // throws a DBConfigException if the user does not exist
    ID uID  = getUid(user); // initiator of the operation
    ID uID1 = getUid(name); // user to be deleted

    if (uID1.id < FIRST_FREE_UID)
      throw new DBConfigException("Cannot remove " + name);

    // check if uid belong to the group admin
    // throws a DBConfigException if not
    check_belong_admin(uID);

    // if the user uID1 is logged on
    if (((User)users.get(uID1.index)).countLogged > 0)
      // schedule the user to be deleted
      ((User)users.get(uID1.index)).toBeDeleted = true;
    else
      // else, remove user
      deleteUser(uID1);
    
    // save DBConfig object to the disk
    saveDBConfig();
  }

  /**
   * Changes the name of a user.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code>
   * should belong to the admin group.
   * @param name   The name of the user.
   * @param newName   The new name of the user.
   */
  public synchronized void changeUserName(String user, String name, 
					  String newName)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null
	|| newName == null)
      throw new DBConfigException("Invalid changeUserName() parameters!");

    // throws a DBConfigException if the user does not exist
    ID uID  = getUid(user); // initiator of the operation
    ID uID1 = getUid(name); // user whose name will be changed

    if (uID1.id == ADMIN_UID)
      throw new DBConfigException("Cannot change name for admin.");
    if (uID1.id == ANONYMOUS_UID)
      throw new DBConfigException("Cannot change name for anonymous.");

    // if the user uID1 is logged on
    if (((User)users.get(uID1.index)).countLogged > 0)
      throw new DBConfigException("Cannot change name for a logged user.");

    // check if uid belong to the group admin
    // throws a DBConfigException if not
    check_belong_admin(uID);

    try 
      {
	getUid(newName);
      }
    catch (DBConfigException e)
      {
	// the new name is not already in use
	// change the old name to the new name
	((User)users.get(uID1.index)).name = newName;

	// save DBConfig object to the disk
	saveDBConfig();
	return;
      }

    // if this point is reached, no exception was thrown in the try above
    // newName is already in use, can not change the old name
    // to the newName
    throw new DBConfigException("User name " + newName + " is already in use.");
  }

  /**
   * Changes the password of a user.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code>
   * should belong to the admin group or should be the same as 
   * <code>name</code>.
   * @param name   The name of the user whose password will be changed.
   * @param newPassword   The new password.
   */
  public synchronized void changePassword(String user, String name, 
					  String newPassword)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null
	|| newPassword == null)
      throw new DBConfigException("Invalid changePassword() parameters!");

    // throws a DBConfigException if the user does not exist
    ID uID  = getUid(user); // initiator of the operation
    ID uID1 = getUid(name); // user whose password will be changed
    
    // if we are not in the case that a user wants to change its
    // own password, check if the user belongs to the admin group
    // throws a DBConfigException if the user does not belong
    if (!(user.equals(name)))
      check_belong_admin(uID);

    // change password
    ((User)users.get(uID1.index)).password = newPassword;

    // save DBConfig object to the disk
    saveDBConfig();
  }

  /**
   * Changes the permissions of a user.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The initiator of the operation. The <code>user</code>
   * should belong to the admin group.
   * @param name   The name of the user whose permissions will be changed.
   * @param newPermissions   The new permissions.
   */
  public synchronized void changePermissions(String user, String name, 
					     long newPermissions)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null 
	|| name == null)
      throw new DBConfigException("Invalid changePermissions() parameters!");

    // throws a DBConfigException if the user does not exist
    ID uID  = getUid(user); // initiator of the operation
    ID uID1 = getUid(name); // user whose permissions will be changed

    if (uID1.id == ADMIN_UID)
      throw new DBConfigException("Cannot change permissions for admin.");
    if (uID1.id == ANONYMOUS_UID)
      throw new DBConfigException("Cannot change permissions for anonymous.");

    // check if uid belong to the group admin
    // throws a DBConfigException if not
    check_belong_admin(uID);

    try
      {
	check_belong_admin(uID1);
      }
    catch (DBConfigException e)
      {
	// change the permissions
	((User)users.get(uID1.index)).permissions = newPermissions;
	
	// save DBConfig object to the disk
	saveDBConfig();
	return;
      }

    // we get here if the name was an administrator
    throw new DBConfigException("Cannot change permissions for administrators.");
  }

  /**
   * Increment a user's connection count.
   *
   * @exception DBConfigException   The operation can not be completed.
   * @param user   The name of the user.
   */
  public synchronized void markLogged(String user)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null)
      throw new DBConfigException("Invalid markLogged() parameters!");

    // throws a DBConfigException if the user does not exist
    ID uID = getUid(user);

    ((User)users.get(uID.index)).countLogged++;

    // save DBConfig object to the disk
    saveDBConfig();
  }

  /**
   * Decrement a user's connection count.
   *
   * @exception DBConfigException   the operation can not be completed.
   * @param user   The name of the user.
   */
  public synchronized void unmarkLogged(String user)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null)
      throw new DBConfigException("Invalid unmarkLogged() parameters!");

    // throws a DBConfigException if the user does not exist
    ID uID = getUid(user);

    // if the user is scheduled to be deleted, delete the user
    if (((User)users.get(uID.index)).toBeDeleted)
      deleteUser(uID);
    else
      ((User)users.get(uID.index)).countLogged--;

    // save DBConfig object to the disk
    saveDBConfig();
  }

  // in the following algorithm related methods, variable name refers to
  // algorithm name.

  /**
   * Adds a new algorithm. The <code>user</code> will be the owner
   * of the algorithm.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * a group with the name <code>group</code> does not exist.
   * @param user   The owner of the new algorithm. This user should have
   * permissions to add new algorithms in the system.
   * @param name   The name of the algorithm.
   * @param group   The group to which the algorithm belongs.
   */
  public synchronized void addAlgorithm(String user, String name, String group)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| name == null
	|| group == null)
      throw new DBConfigException("Invalid addAlgorithm() parameters!");

    // throws a DBConfigException if the user or group do not exist
    ID uID = getUid(user);  // initiator of the operation
    ID gID = getGid(group); // the group to which the algorithm belongs
    
    // checks if the user has permissions to add a new algorithm
    // throws a DBConfigException if not
    check_perm_algorithms(uID);

    // check if the an algorithm with name already exists
    try 
      {
	getAid(name);
      }
    catch (DBConfigException e)
      {
	// the name for this algorithm is not already in use
	// add the new algorithm and return
	algorithms.add(new Algorithm(getNextAID(), name, uID.id, gID.id));

	// save DBConfig object to the disk
	saveDBConfig();
	return;
      }
    
    // if this point is reached, no exception was thrown in the try above    
    // this name of the algorithm is already in use
    // can not complete the operation
    throw new DBConfigException("The algorithm name " + name + " is already in use.");
  }

  /**
   * Deletes an algorithm.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * an algorithm with the name <code>name</code> does not exist.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should be the owner of the algorithm or he should belong to the
   * admin group.
   * @param name   The name of the algorithm to be deleted.
   */
  public synchronized void deleteAlgorithm(String user, String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| name == null)
      throw new DBConfigException("Invalid deleteAlgorithm() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID aID = getAid(name);

    if (aID.id < FIRST_FREE_AID)
      throw new DBConfigException("Cannot remove " + name);

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner of the algorithm

	// look for aid in the list of algorithms 
	// if uid is not the owner of aid a DBConfigException 
	// is thrown
	check_owner_algorithm(uID, aID);
      }
	
    // if this point is reached uid is in admin or
    // uid is the owner of the algorithm aid

    // if algorithm is used just mark it for deletion
    if (((Algorithm)algorithms.get(aID.index)).countUsage > 0)
      {
	((Algorithm)algorithms.get(aID.index)).toBeDeleted = true;
	// save DBConfig object to the disk
	saveDBConfig();
      }
    else
      {
	// otherwise remove the algorithm from the list
	algorithms.remove(aID.index);

	// save DBConfig object to the disk
	saveDBConfig();

	try
	  {
	    File file_to_delete = new File(name + ".jar");
	    
	    if(file_to_delete.delete() ==  false)
	      System.err.println("Could not delete " + name + ".jar");
	  }
	catch (Throwable e)
	  {
	    throw new DBConfigException("Some errors occured: " + e);
	  }
      }
  }

  /**
   * Changes the group of the algorithm.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * an algorithm with the name <code>name</code> does not exist or
   * the algorithm is scheduled to be deleted.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should be the owner of the algorithm or he should belong to the
   * admin group.
   * @param name   The name of the algorithm.
   * @param newGroup   The name of the new group.
   */
  public synchronized void changeAlgorithmGroup(String user, String name, 
						String newGroup)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| name == null
	|| newGroup == null)
      throw new DBConfigException("Invalid changeAlgorithmGroup() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID aID = getAid(name);

    // if algorithm is scheduled to be deleted, throw an exception
    if (((Algorithm)algorithms.get(aID.index)).toBeDeleted)
      throw new DBConfigException("This algorithm is scheduled to be deleted, it can no longer be used or modified.");

    ID gID = getGid(newGroup);
    
    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner of the algorithm

	// look for aid in the list of algorithms 
	// if uid is not the owner of aid a DBConfigException 
	// is thrown
	check_owner_algorithm(uID, aID);
      }
	
    // if this point is reached uid is in admin or
    // uid is the owner of the algorithm aid
    
    // change the group of the algorithm
    ((Algorithm)algorithms.get(aID.index)).gid = gID.id; 

    // save DBConfig object to the disk
    saveDBConfig();
  }

  /**
   * Increment an algorithm's usage count.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * an algorithm with the name <code>name</code> does not exist or
   * the algorithm is scheduled to be deleted.
   * @param user   The initiator of the operation. The <code>user</code>
   * should belong to the admin group, or he should be the owner of 
   * the algorithm or he should belong to the group of the algorithm.
   * @param name   The name of the algorithm.
   */
  public synchronized void markAlgorithmInUse(String user, String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| name == null)
      throw new DBConfigException("Invalid markAlgorithmInUse() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID aID = getAid(name);

    // if algorithm is scheduled to be deleted, throw an exception
    if (((Algorithm)algorithms.get(aID.index)).toBeDeleted)
      throw new DBConfigException("The algorithm is scheduled to be deleted and cannot be used.");
    
    // get the gid of the algorithm
    // aid is guaranteed to exist therefore gid is
    // guaranteed to exist
    int gid = getGidOfAlgorithm(aID);

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch(DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner of the algorithm

	// look for aid in the list of algorithms 
	// if uid is not the owner of aid a DBConfigException 
	// is thrown
	try
	  {
	    check_owner_algorithm(uID, aID);
	  }
	catch (DBConfigException e1)
	  {
	    // uid is not the owner of aid
	    // check if it belongs to gid
	    int j;
	    for (j = 0; j < usersGroups.size(); j++)
	      if (((UserGroup)usersGroups.get(j)).uid == uID.id &&
		  ((UserGroup)usersGroups.get(j)).gid == gid)
		{
		  // uid belongs to gid
		  // mark algorithm in use
		  // aid is guaranteed to exist !
		  ((Algorithm)algorithms.get(aID.index)).countUsage++;

		  // save DBConfig object to the disk
		  saveDBConfig();
		  return;
		}
	    
	    // uid does not belong to gid
	    if (j == usersGroups.size())
	      throw new DBConfigException("No permission to use the algorithm.");
	  }
      }
	
    // if this point is reached uid is in admin or
    // uid is the owner of the algorithm aid
    // mark algorithm in use
    // aid is guaranteed to exist !
    ((Algorithm)algorithms.get(aID.index)).countUsage++;

    // save DBConfig object to the disk
    saveDBConfig();
  }

  /**
   * Decrement an algorithm's usage count.
   *
   * @exception DBConfigException   The operation can not be completed,
   * an algorithm with the name <code>name</code> does not exist. 
   * @param name   The name of the algorithm.
   */
  public synchronized void unmarkAlgorithmInUse(String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (name == null)
      throw new DBConfigException("Invalid unmarkAlgorithmInUse() parameters!");

    // if the algorithm does not exist
    // a DBConfigException is thrown
    ID aID = getAid(name);
    int count;

    count = --((Algorithm)algorithms.get(aID.index)).countUsage;

    // save DBConfig object to the disk
    saveDBConfig();

    if (count < 0)
      throw new DBConfigException("DBConfig internal error (unmarkAlgorithmInUse).");

    // if the algorithm was scheduled to be deleted
    // and the countUsage reaches 0, delete the algorithm
    if (count == 0 && 
	((Algorithm)algorithms.get(aID.index)).toBeDeleted == true)
      {
	// delete the algorithm
	algorithms.remove(aID.index);

      	// save DBConfig object to the disk
	saveDBConfig();
	
	try
	  {
	    File file_to_delete = new File(name + ".jar");
	    
	    if(file_to_delete.delete() ==  false)
	      System.err.println("Could not delete " + name + ".jar");
	  }
	catch (Throwable e)
	  {
	    throw new DBConfigException("Some errors occured: " + e);
	  }

      }
  }

  /**
   * Returns the use count for an algorithm.
   *
   * @exception DBConfigException   The operation can not be completed,
   * an algorithm with the name <code>name</code> does not exist. 
   * @param name   The name of the algorithm.
   * @return   The use count for the algorithm.
   */
  public synchronized int getAlgorithmUseCount(String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (name == null)
      throw new DBConfigException("Invalid getAlgorithmUseCount() parameters!");

    // if the algorithm does not exist
    // a DBConfigException is thrown
    ID aID = getAid(name);

    return ((Algorithm)algorithms.get(aID.index)).countUsage;
  }

  // in the following database related methods, variable name refers to
  // database name.

  /**
   * Adds a new database. The <code>user</code> will be the owner
   * of the database.
   *
   * @exception DBConfigException   the operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * a group with the name <code>group</code> does not exist or
   * the databse name <code>name</code> is already in use.
   * @param user   The owner of the new database. This user should have
   * permissions to add new databases in the system.
   * @param name   The name of the database.
   * @param group   The group to which the database belongs.
   */
  public synchronized void addDatabase(String user, String name, String group)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| name == null
	|| group == null)
      throw new DBConfigException("Invalid addDatabase() parameters!");

    // throws a DBConfigException if the user or group do not exist
    ID uID = getUid(user);  // initiator of the operation
    ID gID = getGid(group); // the group to which the database belongs
    
    // checks if the user has permissions to add a new database
    // throws a DBConfigException if not
    check_perm_databases(uID);

    // check if a database with name already exists
    try 
      {
	getDbid(name);
      }
    catch (DBConfigException e)
      {
	// the name for this database is not already in use
	// add the new database and return
	databases.add(new Database(getNextDBID(), name, uID.id, gID.id));

	// save DBConfig object to the disk
	saveDBConfig();
	return;
      }
    
    // if this point is reached, no exception was thrown in the try above    
    // this name of the database is already in use
    // can not complete the operation
    throw new DBConfigException("The database name " + name + " is already in use.");
  }

  /**
   * Deletes a database.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * a database with the name <code>name</code> does not exist.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should be the owner of the database or he should belong to the
   * admin group.
   * @param name   The name of the database to be deleted.
   */
  public synchronized void deleteDatabase(String user, String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| name == null)
      throw new DBConfigException("Invalid deleteDatabase() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID  = getUid(user);
    ID dbID = getDbid(name);

    if (dbID.id < FIRST_FREE_DBID)
      throw new DBConfigException("Cannot remove " + name);

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner of the database

	// look for aid in the list of databases 
	// if uid is not the owner of dbid a DBConfigException 
	// is thrown
	check_owner_database(uID, dbID);
      }
	
    // if this point is reached uid is in admin or
    // uid is the owner of the database dbid

    // if database is used just mark it for deletion
    if (((Database)databases.get(dbID.index)).countUsage > 0)
      ((Database)databases.get(dbID.index)).toBeDeleted = true;
    // otherwise remove the database from the list
    else
      {
	// find the directory of the database
	String dirName = getDirDatabase(name);
	
	// then remove the database!
	databases.remove(dbID.index);
	
	// save DBConfig object to the disk
	saveDBConfig();
	
	try
	  {
	    File dir = new File(dirName);

	    String dbdataName  = dirName + "/dbdata" + dbID.id;
	    String dbcacheName = dirName + "/dbcache" + dbID.id;

	    File dbdata = new File(dbdataName);
	    File dbcache = new File(dbcacheName);
	    
	    if (dbdata.exists())
	      {
		if (dbdata.delete() == false)
		  System.err.println("Could not delete " + dbdataName);
	      }
	    else
	      System.err.println(dbdataName + "does not exist!");

	    if (dbcache.exists())
	      {
		if(dbcache.delete() == false)
		  System.err.println("Could not delete " + dbcacheName); 
	      }
	    else
	      System.err.println(dbcacheName +  " does not exist!");

	    if(dir.delete() == false)
	      System.err.println("Could not delete " + dirName);
	  }
	catch (Throwable e)
	  {
	    throw new DBConfigException("Some errors occured: " + e);
	  }
      }
  }

  /**
   * Changes the group of the database.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * a database with the name <code>name</code> does not exist or
   * the database is scheduled to be deleted.
   * @param user   The initiator of the operation. The <code>user</code> 
   * should be the owner of the database or he should belong to the
   * admin group.
   * @param name   The name of the database.
   * @param newGroup   The name of the new group.
   */
  public synchronized void changeDatabaseGroup(String user, String name, 
					       String newGroup)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| name == null
	|| newGroup == null)
      throw new DBConfigException("Invalid changeDatabaseGroup() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID dbID = getDbid(name);

    // if database is scheduled to be deleted
    // throw an exaception
    if (((Database)databases.get(dbID.index)).toBeDeleted)
      throw new DBConfigException("This database is scheduled to be deleted, it can no longer be used or modified.");

    ID gID = getGid(newGroup);
    
    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner of the database

	// look for dbid in the list of databases 
	// if uid is not the owner of dbid a DBConfigException 
	// is thrown
	check_owner_database(uID, dbID);
      }
	
    // if this point is reached uid is in admin or
    // uid is the owner of the database dbid
    
    // change the group of the database
    ((Database)databases.get(dbID.index)).gid = gID.id; 

    // save DBConfig object to the disk
    saveDBConfig();
  }
  
  /**
   * Sets the database cache status.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a database with the name <code>name</code> does not exist.
   * @param name   The name of the database.
   * @param status   The new status. Status can have the following values
   * CACHE_NOT_CREATED = cache not created
   * CACHE_INITIAL_CREATION = cache is being created for the first time
   * CACHE_CREATED = cache has been created, field CacheSupport 
   * contains the minimum  support for which the cache has been generated.
   * CACHE_IN_CREATION = cache is being created, will replace previous cache. 
   */
  public synchronized void setCacheStatus(String name, int status)
    throws DBConfigException
  {
    // check arguments for validity
    if (name == null)
      throw new DBConfigException("Invalid setCacheStatus() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID dbID = getDbid(name);

    // dbid is guaranteed to exist !
    // change the status of the cache
    ((Database)databases.get(dbID.index)).cacheStatus = status;

    // save DBConfig object to the disk
    saveDBConfig();
  }
  
  /**
   * Returns the database cache status.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a database with the name <code>name</code> does not exist.
   * @param name   The name of the database.
   * @return  The status. Status can have the following values
   * CACHE_NOT_CREATED = cache not created
   * CACHE_INITIAL_CREATION = cache is being created for the first time
   * CACHE_CREATED = cache has been created, field CacheSupport 
   * contains the minimum  support for which the cache has been generated.
   * CACHE_IN_CREATION = cache is being created, will replace previous cache. 
   */
  public synchronized int getCacheStatus(String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (name == null)
      throw new DBConfigException("Invalid getCacheStatus() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID dbID = getDbid(name);

    // dbid is guaranteed to exist !
    // return the status of the cache
    return ((Database)databases.get(dbID.index)).cacheStatus;
  }
  
  /**
   * Sets the database cache support.
   *
   * @exception DBConfigException   the operation can not be completed
   * a database with the name <code>name</code> does not exist.
   * @param name   The name of the database.
   * @param support   The new support.
   */
  public synchronized void setCacheSupport(String name, float support)
    throws DBConfigException
  {
    // check arguments for validity
    if (name == null)
      throw new DBConfigException("Invalid setCacheSupport() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID dbID = getDbid(name);

    // dbid is guaranteed to exist !
    // change the support of the cache
    ((Database)databases.get(dbID.index)).cacheSupport = support;

    // save DBConfig object to the disk
    saveDBConfig();
  }

  /**
   * Returns the database cache support.
   *
   * @exception DBConfigException   the operation can not be completed
   * a database with the name <code>name</code> does not exist.
   * @param name   The name of the database.
   * @return   The support.
   */
  public synchronized float getCacheSupport(String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (name == null)
      throw new DBConfigException("Invalid getCacheSupport() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID dbID = getDbid(name);

    // dbid is guaranteed to exist !
    // return the support of the cache
    return (float)((Database)databases.get(dbID.index)).cacheSupport;
  }

  /**
   * Increment a database's usage count.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * a database with the name <code>name</code> does not exist or
   * the database is scheduled to be deleted.
   * @param user   The initiator of the operation. The <code>user</code>
   * should belong to the admin group, or he should be the owner of 
   * the database or he should belong to the group of the database.
   * @param name   The name of the database.
   */
  public synchronized void markDatabaseInUse(String user, String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| name == null)
      throw new DBConfigException("Invalid markDatabaseInUse() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    ID dbID = getDbid(name);

    // if database is scheduled to be deleted
    // throw an exaception
    if (((Database)databases.get(dbID.index)).toBeDeleted)
      throw new DBConfigException("The database is scheduled to be deleted and cannot be used.");

    // get the gid of the database
    // dbid is guaranteed to exist therefore gid is
    // guaranteed to exist
    int gid = getGidOfDatabase(dbID);

    // check if uid is the admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// check if it is the owner of the database

	// look for dbid in the list of databases 
	// if uid is not the owner of dbid a DBConfigException 
	// is thrown
	try
	  {
	    check_owner_database(uID, dbID);
	  }
	catch (DBConfigException e1)
	  {
	    // uid is not the owner of dbid
	    // check if it belongs to gid
	    int j;
	    for (j = 0; j < usersGroups.size(); j++)
	      if (((UserGroup)usersGroups.get(j)).uid == uID.id &&
		  ((UserGroup)usersGroups.get(j)).gid == gid)
		{
		  // uid belongs to gid
		  // mark database in use
		  // dbid is guaranteed to exist !
		  ((Database)databases.get(dbID.index)).countUsage++;

		  // save DBConfig object to the disk
		  saveDBConfig();
		  return;
		}
	    
	    // uid does not belong to gid
	    if (j == usersGroups.size())
	      throw 
		new DBConfigException("No permission to use the database.");
	  }
      }
	
    // if this point is reached uid is in admin or
    // uid is the owner of the database dbid
    // mark database in use
    // dbid is guaranteed to exist !
    ((Database)databases.get(dbID.index)).countUsage++;

    // save DBConfig object to the disk
    saveDBConfig();
  }


  /**
   * Decrement a database's usage count.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a database with the name <code>name</code> does not exist.
   * @param name   The name of the database.
   */
  public synchronized void unmarkDatabaseInUse(String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (name == null)
      throw new DBConfigException("Invalid unmarkDatabaseInUse() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID dbID = getDbid(name);
    int count;

    count = --((Database)databases.get(dbID.index)).countUsage;
    
    // save DBConfig object to the disk
    saveDBConfig();

    if (count < 0)
      throw new DBConfigException("DBConfig internal error (unmarkDatabaseInUse).");

    // if the database was scheduled to be deleted
    // and the countUsage reaches 0, delete the database
    if (count == 0 && 
	((Database)databases.get(dbID.index)).toBeDeleted == true)
      {
	// find the directory of the database
	String dirName = getDirDatabase(name);
	
	// then remove the database!
	databases.remove(dbID.index);
	
	// save DBConfig object to the disk
	saveDBConfig();
	
	try
	  {
	    File dir = new File(dirName);

	    String dbdataName  = dirName + "/dbdata" + dbID.id;
	    String dbcacheName = dirName + "/dbcache" + dbID.id;

	    File dbdata = new File(dbdataName);
	    File dbcache = new File(dbcacheName);
	    
	    if (dbdata.exists())
	      {
		if (dbdata.delete() == false)
		  System.err.println("Could not delete " + dbdataName);
	      }
	    else
	      System.err.println(dbdataName + "does not exist!");

	    if (dbcache.exists())
	      {
		if(dbcache.delete() == false)
		  System.err.println("Could not delete " + dbcacheName); 
	      }
	    else
	      System.err.println(dbcacheName +  " does not exist!");

	    if(dir.delete() == false)
	      System.err.println("Could not delete " + dirName);
	  }
	catch (Throwable e)
	  {
	    throw new DBConfigException("Some errors occured: " + e);
	  }
      }
  }

  /**
   * Returns the use count for a database.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a database with the name <code>name</code> does not exist.
   * @param name   The name of the database.
   * @return   The use count for the database.
   */
  public synchronized int getDatabaseUseCount(String name)
    throws DBConfigException
  {
    // check arguments for validity
    if (name == null)
      throw new DBConfigException("Invalid getDatabaseCount() parameters!");

    // if one of the following does not exist
    // a DBConfigException is thrown
    ID dbID = getDbid(name);

    return ((Database)databases.get(dbID.index)).countUsage;
  }

  // general methods

  /**
   * Return the groups to which a user belongs.
   *
   * @return   Returns a list of groups to which the <code>user</code> belongs.
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * the user is scheduled to be deleted.
   * @param user   The user whose groups will be returned.
   */
  public synchronized Vector listGroupsForUser(String user)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null)
      throw new DBConfigException("Invalid listGroupsForUser() parameters!");

    // if the user does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);

    // if the user is scheduled to be deleted
    // throw an exception
    if (((User)users.get(uID.index)).toBeDeleted)
      throw new DBConfigException("User is scheduled to be deleted.");
      
    Hashtable gids = new Hashtable(); // gids associated with uid
    Vector v = new Vector();    // Strings associated with gids

    for (int i = 0; i < usersGroups.size(); i++)
      if (((UserGroup)usersGroups.get(i)).uid == uID.id)
	// find a gid, add it to the collection
	gids.put(new Integer(((UserGroup)usersGroups.get(i)).gid), "");
	  
    // look for the strings associated with the gids
    for (int i = 0; i < groups.size(); i++)
      if (gids.containsKey(new Integer(((Group)groups.get(i)).gid)))
	v.add(((Group)groups.get(i)).name);
	  
    return v;
  }

  /**
   * Return the users that are members of a group.
   *
   * @return   a list of users belonging to a group.
   * @exception DBConfigException   The operation can not be completed,
   * a group with the name <code>group</code> does not exist.
   * @param group   The group to which the users belong.
   */
  public synchronized Vector listUsersForGroup(String group)
    throws DBConfigException
  {
    // check arguments for validity
    if (group == null)
      throw new DBConfigException("Invalid listUsersForGroup() parameters!");

    // if the group does not exist
    // a DBConfigException is thrown
    ID gID = getGid(group);
    Hashtable uids = new Hashtable(); // uids associated with gid
    Vector v = new Vector();    // Strings associated with uids

    for (int i = 0; i < usersGroups.size(); i++)
      if (((UserGroup)usersGroups.get(i)).gid == gID.id)
	// find an uid, add it to the collection
	uids.put(new Integer(((UserGroup)usersGroups.get(i)).uid), "");
    
    // look for the strings associated with the uids
    for (int i = 0; i < users.size(); i++)
      {
	// skip the users scheduled to be deleted
	if (((User)users.get(i)).toBeDeleted)
	  continue;

	if (uids.containsKey(new Integer(((User)users.get(i)).uid)))
	  v.add(((User)users.get(i)).name);
      }

    return v;
  }

  /**
   * Returns a list of groups to which the user belongs, or if the
   * user is an administrator, it returns the list of all groups.
   *
   * @return   a list of groups to which the <code>user</code> belongs
   * or a list of all available groups if the <code>user</code> belong
   * to the admin group.
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * the user is scheduled to be deleted.
   * @param user   Initiator of the operation.
   */
  public synchronized Vector listGroups(String user)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null)
      throw new DBConfigException("Invalid listGroups() parameters!");

    // if the user does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
   
    // if the user is scheduled to be deleted
    // throw an exception
    if (((User)users.get(uID.index)).toBeDeleted)
      throw new DBConfigException("User is scheduled to be deleted.");
   
    Vector v = new Vector();

    // check if the user belong to admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// return the list of groups to which it belongs

	return listGroupsForUser(user);
      }
    
    // if this point is reached uid is in admin
    // return a list of all available groups

    for (int i = 0; i < groups.size(); i++)
      v.add(((Group)groups.get(i)).name);
    
    return v;
  }

  /**
   * Returns a list of all users.
   *
   * @return   a list of users in the system
   */
  public synchronized Vector listUsers()
  {
    Vector v = new Vector();

    for (int i = 0; i < users.size(); i++)
      {
	// skip the users scheduled to be deleted
	if (((User)users.get(i)).toBeDeleted)
	  continue;
	
	v.add(((User)users.get(i)).name);
      }
    
    return v;
  }
  
  /**
   * Returns a list of all algorithms to which a user has access.
   *
   * @return   a list of algorithms to which the <code>user</code> 
   * has access or all the algorithms in the system if the 
   * <code>user</code> belongs to the admin group.
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * the user is scheduled to be deleted.
   * @param user   Initiator of the operation.
   */
  public synchronized Vector listAlgorithms(String user)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null)
      throw new DBConfigException("Invalid listAlgorithms() parameters!");

    // if the user does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);
    
    // if the user is scheduled to be deleted
    // throw an exception
    if (((User)users.get(uID.index)).toBeDeleted)
      throw new DBConfigException("User is scheduled to be deleted.");

    Vector v = new Vector();
    ID gID;

    // check if the user belongs to admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// find the list of groups to which it belongs

	Vector uid_groups = listGroupsForUser(user);

	// find the algorithms that belong to these groups
	for (int i = 0; i < uid_groups.size(); i++)
	  {
	    // gid is guaranteed to exist
	    gID = getGid((String)uid_groups.get(i));

	    // find the name of algorithms that have gid
	    // add them to the collection
	    for (int j = 0; j < algorithms.size(); j++)
	      {
		Algorithm alg = (Algorithm)algorithms.get(j);

		// skip the algorithms scheduled to be deleted
		if (alg.toBeDeleted)
		  continue;

		if (alg.gid == gID.id)
		  v.add(alg.name);
	      }
	  }
	
	return v;
      }

    // if this point is reached uid is in admin
    // return a list of all available algorithms

    for (int i = 0; i < algorithms.size(); i++)
      {
	// skip the algorithms scheduled to be deleted
	if (((Algorithm)algorithms.get(i)).toBeDeleted)
	  continue;
	
	v.add(((Algorithm)algorithms.get(i)).name);
      }
  
    return v;
  }

  /**
   * Returns a list of all databases to which a user has access.
   *
   * @return   a list of databases to which the <code>user</code> 
   * has access or all the databases in the system if the <code>user</code>
   * belongs to the admin group.
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * the user is scheduled to be deleted.
   * @param user   Initiator of the operation.
   */
  public synchronized Vector listDatabases(String user)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null)
      throw new DBConfigException("Invalid listDatabases() parameters!");

    // if the user does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);

    // if the user is scheduled to be deleted
    // throw an exception
    if (((User)users.get(uID.index)).toBeDeleted)
      throw new DBConfigException("User is scheduled to be deleted.");

    Vector v = new Vector();
    ID gID;

    // check if the user belongs to admin
    try 
      {
	check_belong_admin(uID);
      }
    catch (DBConfigException e)
      {
	// uid is not in admin group
	// find the list of groups to which it belongs
	
	Vector uid_groups = listGroupsForUser(user);

	// find the databases that belong to these groups
	for (int i = 0; i < uid_groups.size(); i++)
	  {
	    // gid is guaranteed to exist
	    gID = getGid((String)uid_groups.get(i));

	    // find the name of databases that have gid
	    // add them to the collection
	    for (int j = 0; j < databases.size(); j++)
	      {
		Database db = (Database)databases.get(j);

		// skip the databases scheduled to be deleted
		if (db.toBeDeleted)
		  continue;
		
		if (db.gid == gID.id)
		  v.add(db.name);
	      }
	  }
	
	return v;
      }
    
    // if this point is reached uid is in admin
    // return a list of all available databases

    for (int i = 0; i < databases.size(); i++)
      {
	// skip the databases scheduled to be deleted
	if (((Database)databases.get(i)).toBeDeleted)
	  continue;
		
	v.add(((Database)databases.get(i)).name);
      }

    return v;
  }

  /**
   * Returns the permissions of a user.
   *
   * @return   the permissions for <code>user</code>.
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * the user is scheduled to be deleted.
   * @param user   The user whose permissions will be returned.
   */
  public synchronized long getPermissionsForUser(String user)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null)
      throw new DBConfigException("Invalid getPermissionsForUser() parameters!");

    // if the user does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);

    // if the user is scheduled to be deleted
    // throw an exception
    if (((User)users.get(uID.index)).toBeDeleted)
      throw new DBConfigException("User is scheduled to be deleted.");
    
    return ((User)users.get(uID.index)).permissions;
  }

  /**
   * Verifies if the password <code>password</code>
   * for user <code>user</code> is the right password.
   *
   * @exception DBConfigException   The operation can not be completed,
   * a user with the name <code>user</code> does not exist or
   * the user is scheduled to be deleted.
   * @param user The user whose permissions will be returned.
   */
  public synchronized void verifyPasswordForUser(String user, String password)
    throws DBConfigException
  {
    // check arguments for validity
    if (user == null
	|| password == null)
      throw new DBConfigException("Invalid verifyPasswordForUser() parameters!");

    // if the user does not exist
    // a DBConfigException is thrown
    ID uID = getUid(user);

    // if the user is scheduled to be deleted
    // throw an exception
    if (((User)users.get(uID.index)).toBeDeleted)
      throw new DBConfigException("User is scheduled to be deleted.");
    
    if (!(((User)users.get(uID.index)).password.equals(password)))
      throw new DBConfigException("Incorrect password.");
  }
  
  /**
   * Returns a group's owner.
   *
   * @return   the owner of the group.
   * @exception DBConfigException   The operation can not be completed,
   * a group with the name <code>group</code> does not exist.
   * @param group   The group whose owner will be returned.
   */
  public synchronized String getGroupOwner(String group)
    throws DBConfigException
  {
    // check arguments for validity
    if (group == null)
      throw new DBConfigException("Invalid getGroupOwner() parameters!");

    // if the group does not exist
    // a DBConfigException is thrown
    ID gID = getGid(group);

    // get the uid of the owner
    int uid = ((Group)groups.get(gID.index)).uid;

    // get the name of uid
    for (int i = 0; i < users.size(); i++)
      if (((User)users.get(i)).uid == uid)
	return ((User)users.get(i)).name;
    
    // if this point is reached something is wrong
    throw new DBConfigException("DBConfig internal error (getGroupOwner).");
  }

  /**
   * Returns an algorithm's owner.
   *
   * @return   The owner of the algorithm.
   * @exception DBConfigException   The operation can not be completed.
   * an algorithm with the name <code>algorithm</code> does not exit or
   * the algorithm is scheduled to be deleted.
   * @param algorithm   The algorithm whose owner will be returned.
   */
  public synchronized String getAlgorithmOwner(String algorithm)
    throws DBConfigException
  {
    // check arguments for validity
    if (algorithm == null)
      throw new DBConfigException("Invalid getAlgorithmOwner() parameters!");

    // if the algorithm does not exist
    // a DBConfigException is thrown
    ID aID = getAid(algorithm);

    // if the algorithm is scheduled to be deleted
    // throw an exception
    if (((Algorithm)algorithms.get(aID.index)).toBeDeleted)
      throw new DBConfigException("Algorithm is scheduled to be deleted.");

    // get the uid of the owner
    int uid = ((Algorithm)algorithms.get(aID.index)).uid;

    // get the name of uid
    for (int i = 0; i < users.size(); i++)
      if (((User)users.get(i)).uid == uid)
	return ((User)users.get(i)).name;
    
    // if this point is reached something is wrong
    throw new DBConfigException("DBConfig internal error (getAlgorithmOwner).");
  }

  /**
   * Returns a database's owner.
   *
   * @return   The owner of the database.
   * @exception DBConfigException   The operation can not be completed,
   * a database with the name <code>database</code> does not exist
   * or the database is scheduled to be deleted.
   * @param database   The database whose owner will be returned.
   */
  public synchronized String getDatabaseOwner(String database)
    throws DBConfigException
  {
    // check arguments for validity
    if (database == null)
      throw new DBConfigException("Invalid getDatabaseOwner() parameters!");

    // if the database does not exist
    // a DBConfigException is thrown
    ID dbID = getDbid(database);

    // if the database is scheduled to be deleted
    // throw an exception
    if (((Database)databases.get(dbID.index)).toBeDeleted)
      throw new DBConfigException("Database is scheduled to be deleted.");

    // get the uid of the owner
    int uid = ((Database)databases.get(dbID.index)).uid;

    // get the name of uid
    for (int i = 0; i < users.size(); i++)
      if (((User)users.get(i)).uid == uid)
	return ((User)users.get(i)).name;
    
    // if this point is reached something is wrong
    throw new DBConfigException("DBConfig internal error (getDatabaseOwner).");
  }

  /**
   * Returns a database's group.
   *
   * @return   The group of the database.
   * @exception DBConfigException   The operation can not be completed,
   * a database with the name <code>database</code> does not exist
   * or the database is scheduled to be deleted.
   * @param database   The database whose group will be returned.
   */
  public synchronized String getDatabaseGroup(String database)
    throws DBConfigException
  {
    // check arguments for validity
    if (database == null)
      throw new DBConfigException("Invalid getDatabaseGroup() parameters!");

    // if the database does not exist
    // a DBConfigException is thrown
    ID dbID = getDbid(database);

    // if the database is scheduled to be deleted
    // throw an exception
    if (((Database)databases.get(dbID.index)).toBeDeleted)
      throw new DBConfigException("Database is scheduled to be deleted.");

    // get the gid of the database
    int gid = ((Database)databases.get(dbID.index)).gid;

    // get the name of gid
    for (int i = 0; i < groups.size(); i++)
      if (((Group)groups.get(i)).gid == gid)
	return ((Group)groups.get(i)).name;
    
    // if this point is reached something is wrong
    throw new DBConfigException("DBConfig internal error (getDatabaseGroup).");
  }

  /**
   * Returns an algorithm's group.
   *
   * @return   The group of the algorithm.
   * @exception DBConfigException   The operation can not be completed,
   * an algorithm with the name <code>algorithm</code> does not exits or
   * the algorithm is scheduled to be deleted.
   * @param algorithm   The algorithm whose group will be returned.
   */
  public synchronized String getAlgorithmGroup(String algorithm)
    throws DBConfigException
  {
    // check arguments for validity
    if (algorithm == null)
      throw new DBConfigException("Invalid getAlgorithmGroup() parameters!");

    // if the algorithm does not exist
    // a DBConfigException is thrown
    ID aID = getAid(algorithm);
    
    // if the algorithm is scheduled to be deleted
    // throw an exception
    if (((Algorithm)algorithms.get(aID.index)).toBeDeleted)
      throw new DBConfigException("Algorithm is scheduled to be deleted.");

    // get the gid of the algorithm
    int gid = ((Algorithm)algorithms.get(aID.index)).gid;

    // get the name of gid
    for (int i = 0; i < groups.size(); i++)
      if (((Group)groups.get(i)).gid == gid)
	return ((Group)groups.get(i)).name;
    
    // if this point is reached something is wrong
    throw new DBConfigException("DBConfig internal error (getAlgorithmGroup).");
  }

  /**
   * Returns a database's directory.
   *
   * @return   The path to the directory where
   * database with name <code>db</code> will reside.
   * @exception DBConfigException  If the database does not exist.
   */
  public synchronized String getDirDatabase(String db)
    throws DBConfigException
  {
    // check arguments for validity
    if (db == null)
      throw new DBConfigException("Invalid getDirDatabase() parameters!");

    // throws an exception if db does not exist
    String id = new Integer(getDbid(db).id).toString();

    return "DB/DB" + id;
  }

  /**
   * Returns the full path to a database file.
   *
   * @return   The path to the database with name <code>db</code>.
   * @exception DBConfigException  If the database does not exist.
   */
  public synchronized String getPathDatabase(String db)
    throws DBConfigException
  {
    // check arguments for validity
    if (db == null)
      throw new DBConfigException("Invalid getPathDatabase() parameters!");

    // throws an exception if db does not exist
    String id = new Integer(getDbid(db).id).toString();

    return getDirDatabase(db) + "/dbdata" + id;
  }

  /**
   * Returns the full path to a database cache.
   *
   * @return   The path to the cache file for the database 
   * with name <code>db</code>.
   * @exception DBConfigException  If the database does not exist.
   */
  public synchronized String getPathCache(String db)
    throws DBConfigException
  {
    // check arguments for validity
    if (db == null)
      throw new DBConfigException("Invalid getPathCache() parameters!");

    // throws an exception if db does not exist
    String id = new Integer(getDbid(db).id).toString();

    return getDirDatabase(db) + "/dbcache" + id;
  }

  // prints to standard out the list of users
  private synchronized void printUsers()
  {
    System.out.println("\nuid name password permissions countLogged toBeDeleted");
    for(int i = 0; i < users.size(); i++)
      System.out.println((User)users.get(i));
  }

  // prints to standard out the list of user - group association
  private synchronized void printUsersGroups()
  {
    System.out.println("\nuid gid");
    for(int i = 0; i < usersGroups.size(); i++)
      System.out.println((UserGroup)usersGroups.get(i));
  }

  // prints to standard out the list of groups
  private synchronized void printGroups()
  {
    System.out.println("\ngid name uid");
    for(int i = 0; i < groups.size(); i++)
      System.out.println((Group)groups.get(i));
  }

  // prints to standard out the list of algorithms
  private synchronized void printAlgorithms()
  {
    System.out.println("\naid name uid gid countUsage toBeDeleted");
    for(int i = 0; i < algorithms.size(); i++)
      System.out.println((Algorithm)algorithms.get(i));
  }

  // prints to standard out the list of databases
  private synchronized void printDatabases()
  {
    System.out.println("\ndbid name uid gid countUsage toBeDeleted cacheStatus cacheSupport");
    for(int i = 0; i < databases.size(); i++)
      System.out.println((Database)databases.get(i));
  }

  /** 
   * Run DBConfig with no arguments to display the contents
   * of arminer.cfg. This is useful to find out who is logged on
   * and what is the status of the server.
   *
   * Run DBConfig with the -r or -repair switch if you want to
   * fix problems that may have appeared after a crash or abrupt
   * shutdown of the server. DBConfig will try to detect and correct
   * all problems we have thought of and, hopefully, we have
   * thought of all possible types of problems.
   */
  public static void main(String[] args)
  {
    try
      {
	DBConfig dbconf = DBConfig.getDBConfig();

	if (args.length == 1 
	    && (args[0].equals("-repair")
		|| args[0].equals("-r")))
	  {
	    // we need to repair the DBConfig
	    System.out.println("Repair initiated...\n");
	    int errorCount = 0;

	    // scan and repair users
	    System.out.println("Scanning users...");
	    for (int i = 0; i < dbconf.users.size(); i++)
	      {
		User u = (User)dbconf.users.get(i);
		if (u.toBeDeleted)
		  {
		    errorCount++;
		    System.out.println("\tuser " + u.name + 
				       " was scheduled to be deleted, we delete the user...");
		    ID id = new ID(u.uid, i);
		    dbconf.deleteUser(id);
		    i--;
		  }
		else 
		  {
		    if (u.countLogged > 0)
		      {
			errorCount++;
			System.out.println("\tuser " + u.name + 
					   " was marked as logged, we unmark the user...");
			u.countLogged = 0;
		      }
		    else if (u.countLogged < 0)
		      {
			errorCount++;
			System.out.println("\tuser " + u.name + 
					   " had an invalid logged count, we unmark the user...");
			u.countLogged = 0;
		      }
		  }
	      }
	    System.out.println("Scan of users completed.\n");
		   
	    // scan and repair algorithms
	    System.out.println("Scanning algorithms...");
	    for (int i = 0; i < dbconf.algorithms.size(); i++)
	      {
		Algorithm a = (Algorithm)dbconf.algorithms.get(i);
		if (a.toBeDeleted)
		  {
		    errorCount++;
		    System.out.println("\talgorithm " + a.name + 
				       " was scheduled to be deleted, we delete the algorithm...");
		    dbconf.algorithms.remove(i);
		    i--;

		    // physically delete the algorithm
		    try
		      {
			File file_to_delete = new File(a.name + ".jar");
			
			if(file_to_delete.delete() ==  false)
			  System.out.println("Could not delete " + a.name + ".jar");
		      }
		    catch (Throwable e)
		      {
			  System.out.println("Could not delete " + a.name + ".jar; reason: " + e);
		      }
		  }
		else
		  if (a.countUsage > 0)
		    {
		      errorCount++;
		      System.out.println("\talgorithm " + a.name + 
					 " was marked as used, we unmark the algorithm...");
		      a.countUsage = 0;
		    }
	      }
	    System.out.println("Scan of algorithms completed.\n");

	    // scan and repair databases
	    System.out.println("Scanning databases...");
	    for (int i = 0; i < dbconf.databases.size(); i++)
	      {
		Database d = (Database)dbconf.databases.get(i);
		if (d.toBeDeleted)
		  {
		    errorCount++;
		    // find now the directory of the database
		    String dirName = dbconf.getDirDatabase(d.name);

		    System.out.println("\tdatabase " + d.name + 
				       " was scheduled to be deleted, we delete the database...");
		    dbconf.databases.remove(i);
		    i--;

		    // physically delete the database
		    try
		      {
			File dir = new File(dirName);
			
			String dbdataName  = dirName + "/dbdata" + d.dbid;
			String dbcacheName = dirName + "/dbcache" + d.dbid;
			
			File dbdata = new File(dbdataName);
			File dbcache = new File(dbcacheName);
			
			if (dbdata.exists())
			  {
			    if (dbdata.delete() == false)
			      System.out.println("Could not delete " + dbdataName);
			  }
			else
			  System.out.println(dbdataName + "does not exist!");
			
			if (dbcache.exists())
			  {
			    if(dbcache.delete() == false)
			      System.out.println("Could not delete " + dbcacheName); 
			  }
			else
			  System.out.println(dbcacheName +  " does not exist!");
			
			if(dir.delete() == false)
			  System.out.println("Could not delete " + dirName);
		      }
		    catch (Throwable e)
		      {
			System.out.println("An error occured: " + e);
		      }
		  }
		else
		  {
		    if (d.countUsage > 0)
		      {
			errorCount++;
			System.out.println("\tdatabase " + d.name + 
					   " was marked as used, we unmark the database...");
			d.countUsage = 0;
		      }

		    if (d.cacheStatus == CACHE_INITIAL_CREATION)
		      {
			errorCount++;
			System.out.println("\tdatabase cache for " + d.name + 
					   " was marked as being initially created, we reset it to not created...");
			d.cacheStatus = CACHE_NOT_CREATED;
		      }
		    else if (d.cacheStatus == CACHE_IN_CREATION)
		      {
			errorCount++;
			System.out.println("\tdatabase cache for " + d.name + 
					   " was marked as being created, we reset it to created...");
			d.cacheStatus = CACHE_CREATED;
		      }
		  }
	      }
	    System.out.println("Scan of databases completed.\n");

	    System.out.println("Repair completed !\n");
	    System.out.println("" + errorCount 
			       + " error(s) were detected and fixed.\n");

	    if (errorCount > 0)
	      {
		System.out.println("Saving repairs...");
		dbconf.saveDBConfig();
		System.out.println("Repairs were saved.\n");
	      }
	  }

	System.out.println("DBConfig contents: ");
	dbconf.printUsers();
	dbconf.printGroups();
	dbconf.printUsersGroups();
	dbconf.printDatabases();
	dbconf.printAlgorithms();
      }
    catch(DBConfigException e)
      {
	System.out.println(e);
      }
  }  
}
