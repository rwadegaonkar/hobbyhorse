package com.spring.manager;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.Compression;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.CqlResult;
import org.apache.cassandra.thrift.CqlRow;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.Connection;
import com.spring.datasource.User;

public class UserManager {
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(UserManager.class);

	@JsonIgnore
	Connection conn = new Connection();

	@JsonIgnore
	public Connection getConn() {
		return conn;
	}

	@JsonIgnore
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	private ArrayList<User> users = new ArrayList<User>();

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> userBeanList) {
		this.users = userBeanList;
	}

	public ArrayList<User> getNonDeletedUsers(String del) {
		try {
			Client client = conn.connectToDB();
			String cql = "SELECT * FROM USER WHERE" + " isDeleted =" + del;
			CqlResult cR = client.execute_cql_query(
					ByteBuffer.wrap(cql.getBytes()), Compression.NONE);
			System.out.println(cR.getRowsSize());
			List<CqlRow> cr = cR.getRows();
			for (CqlRow c : cr) {
				User userBean = new User();
				userBean = convertUser(c);
				users.add(userBean);
			}
			conn.disconnectFromDB();

			return users;
		} catch (Exception e) {
		}
		return null;
	}

	public User saveUser(User user) throws InvalidRequestException,
			UnavailableException, TimedOutException,
			SchemaDisagreementException, TException, ParseException {
		Client client = conn.connectToDB();
		String KEY = "1222";
		String name = user.getName();
		User newuser = new User();
		newuser.setId(Long.parseLong(KEY));
		newuser.setName(name);
		newuser.setLocation(user.getLocation());
		newuser.setIsDeleted(0);
		// String KEY2 = "sfsdfsd33333333";
		// String cql = "UPDATE USER SET KEY='6', name='" + KEY1
		// + "', description='" + KEY2 + "', isDeleted='0'";
		// System.out.println(cql);
		// CqlResult cR = client.execute_cql_query(
		// ByteBuffer.wrap(cql.getBytes()), Compression.NONE);
		// System.out.println(cR.getRowsSize());
		// System.out.println("inserted...");
		//
		// define consistency level
		ConsistencyLevel consistencyLevel = ConsistencyLevel.ONE;
		// define column parent
		ColumnParent parent = new ColumnParent("USER");

		// define row id
		ByteBuffer rowid = ByteBuffer.wrap(KEY.getBytes());
		Column isDeletednew = new Column();
		isDeletednew.setName("name".getBytes());
		Column isDeletednew1 = new Column();
		isDeletednew1.setName("isDeleted".getBytes());
		try {
			isDeletednew1.setValue("0".getBytes("UTF8"));
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			isDeletednew.setValue(name.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isDeletednew.setTimestamp(System.currentTimeMillis());
		isDeletednew1.setTimestamp(System.currentTimeMillis());
		try {
			client.insert(rowid, parent, isDeletednew, consistencyLevel);
			client.insert(rowid, parent, isDeletednew1, consistencyLevel);
		} catch (InvalidRequestException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TimedOutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		conn.disconnectFromDB();

		return newuser;
	}

	public void deleteUser(String id) throws InvalidRequestException,
			UnavailableException, TimedOutException,
			SchemaDisagreementException, TException {
		Client client = conn.connectToDB();
		String cql = "UPDATE USER SET isDeleted=1 WHERE KEY='15'";
		// CqlResult cR = client.execute_cql_query(
		// ByteBuffer.wrap(cql.getBytes()), Compression.NONE);
		// System.out.println(cR.getRowsSize());
		System.out.println("deleted...");
		conn.disconnectFromDB();
	}

	private User convertUser(CqlRow c) throws ParseException {
		int i = 0;
		DateFormat formatter;
		User userBean = new User();
		for (Column cl : c.getColumns()) {
			System.out.println(new String(cl.getName()) + ": "
					+ new String(cl.getValue()));
			if (new String(cl.getName()).equalsIgnoreCase("KEY")) {
				userBean.setId(Long.parseLong(new String(cl.getValue())));
			}
			if (new String(cl.getName()).equalsIgnoreCase("name")) {
				userBean.setName(new String(cl.getValue()));
			}
			if (new String(cl.getName()).equalsIgnoreCase("description")) {
				userBean.setName(new String(cl.getValue()));
			}
			if (new String(cl.getName()).equalsIgnoreCase("isDeleted")) {
				userBean.setIsDeleted(Long.parseLong(new String(cl.getValue())));
			}
			if (new String(cl.getName()).equalsIgnoreCase("createdBy")) {
				userBean.setCreatedBy(new String(cl.getValue()));
			}
			if (new String(cl.getName()).equalsIgnoreCase("lastUpdatedBy")) {
				userBean.setLastUpdatedBy(new String(cl.getValue()));
			}

			if (new String(cl.getName()).equalsIgnoreCase("createDate")) {
				formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
				String[] newDateArray = new String(cl.getValue()).split("\\s");
				Date date = (Date) formatter.parse(new String(newDateArray[1]
						+ " " + newDateArray[2] + ", " + newDateArray[5] + " "
						+ newDateArray[3]));
				userBean.setCreateDate(date);
			}

			if (new String(cl.getName()).equalsIgnoreCase("lastUpdateDate")) {
				formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
				String[] newDateArray = new String(cl.getValue()).split("\\s");
				Date date = (Date) formatter.parse(new String(newDateArray[1]
						+ " " + newDateArray[2] + ", " + newDateArray[5] + " "
						+ newDateArray[3]));
				userBean.setLastUpdateDate(date);
			}
			if (new String(cl.getName()).equalsIgnoreCase("skills")) {
				userBean.setSkills(new String(cl.getValue()));
			}
			if (new String(cl.getName()).equalsIgnoreCase("hobbies")) {
				userBean.setHobbies(new String(cl.getValue()));
			}
			if (new String(cl.getName()).equalsIgnoreCase("username")) {
				userBean.setUsername(new String(cl.getValue()));
			}
			if (new String(cl.getName()).equalsIgnoreCase("email")) {
				userBean.setEmail(new String(cl.getValue()));
			}
			if (new String(cl.getName()).equalsIgnoreCase("location")) {
				userBean.setLocation(new String(cl.getValue()));
			}
			i++;
		}
		// TODO Auto-generated method stub
		return userBean;
	}

	// public static void main(String args[]) throws InvalidRequestException,
	// UnavailableException, TimedOutException,
	// SchemaDisagreementException, TException,
	// UnsupportedEncodingException {
	// UserManager u = new UserManager();
	// u.deleteUser();
	// u.saveUser();
	// }
}