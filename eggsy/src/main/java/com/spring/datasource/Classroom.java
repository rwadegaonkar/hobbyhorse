package com.spring.datasource;

import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.manager.UserManager;

/**
 * @author Eggsy - eggsy_at_eggsylife.co.uk
 * 
 */
@XmlRootElement(name = "class")
public class Classroom {
	private static final Logger logger = Logger.getLogger(Classroom.class);
	
	@JsonIgnore
	private String classId = null;
	@JsonIgnore
	private ArrayList<Student> students = null;
	private ArrayList<User> users = new ArrayList<User>();
	private User user = new User();
	@JsonIgnore
	private UserManager userManager = new UserManager();

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> userBeanList) {
		this.users = userBeanList;
	}

	public Classroom() {

	}

	@JsonIgnore
	public Classroom(String id) {
		this.classId = id;
	}

	/**
	 * @return the classId
	 */
	public String getClassId() {
		return classId;
	}

	@JsonIgnore
	public UserManager getUserManager() {
		return userManager;
	}

	@JsonIgnore
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @param classId
	 *            the classId to set
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	public void getNonDeletedUsers(String del) {
		users = userManager.getNonDeletedUsers(del);
	}

	public User saveUser(User user) {
		User newuser = new User();
		try {
			 newuser = userManager.saveUser(user);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchemaDisagreementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newuser;
	}

	public void deleteUser(String id) {
		try {
			userManager.deleteUser(id);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchemaDisagreementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the students
	 */
	@XmlElement(name = "student")
	public ArrayList<Student> getStudents() {
		return students;
	}

	/**
	 * @param students
	 *            the students to set
	 */
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}