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

import java.io.*;
import java.net.*;
import java.util.*;

/**

   Server.java<P>

   Server implements the server part of the ARMiner application.<P>

*/

/*
  
   This file is a part of the ARMiner project.
   
   (P)1999-2000 by ARMiner Server Team:

   Dana Cristofor
   Laurentiu Cristofor

*/

public class Server 
{
  private static LinkedList clients = new LinkedList();

  /**
   * Usage: java Server [port_number]
   * Default port_number is 8072.
   *
   */
  public static void main(String[] args) 
    throws IOException 
  {
    int port = 8072;

    System.out.println("ARMiner Server version 1.0a");
    System.out.println("(C)2000-2001 UMass/Boston Computer Science Department\n");

    try 
      {
	if (args.length > 0)
	  port = Integer.parseInt(args[0]);
      }
    catch (Exception e)
      {
	System.out.println("usage: java Server [port_number]");
	System.exit(0);
      }

    try
      {
	ServerSocket listener = new ServerSocket(port);
	System.out.println("The Server is running on port " + port);

	Socket client;
	ServerChild child;

	while (true) 
	  {
	    client = listener.accept();
	    child = new ServerChild(client);
	    child.start();
	    clients.add(child);
	    System.out.println("Client <" + child.getID() 
			       + "> from " + listener.getInetAddress() 
			       + " on client's port " + client.getPort());
	  }
      }
    catch (Exception e)
      {
	System.out.println("An error occurred: " + e);
      }
  }

  static synchronized void remove(ServerChild child) 
  {
    for (ListIterator iterator = clients.listIterator(0); iterator.hasNext();) 
      if (((ServerChild)iterator.next()).equals(child)) 
	{
	  iterator.remove();
	  System.out.println("Client <" + child.getID() + "> disconnected!"); 
	  break;
	}
  }    
}
