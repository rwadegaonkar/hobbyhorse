package com.spring.conf;
//This is my comment Sulagna
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.TBinaryProtocol;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.spring.manager.UserManager;

public class Connection {	 
	private static final Logger logger = Logger.getLogger(Connection.class);
	public static TTransport tr = new TFramedTransport(new TSocket("localhost",
			9160));

	public Client connectToDB() {
		try {
			TProtocol proto = new TBinaryProtocol(tr);
			Client client = new Cassandra.Client(proto);
			tr.open();
			try {
				client.set_keyspace("HOBBYHORSE");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return client;
		} catch (Exception e) {
		}
		return null;
	}

	public void disconnectFromDB() {
		tr.close();
	}
}