package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.UserDao;
import com.spring.datasource.User;

@XmlRootElement(name = "users")
public class UserManager {
	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	UserDao delegate = new UserDao();

	private ArrayList<User> users = new ArrayList<User>();

	public ArrayList<User> getUsers() {
		return users;
	}

	@XmlElement
	public void setUsers(ArrayList<User> userBeanList) {
		this.users = userBeanList;
	}

	public ArrayList<User> getAllUsers() {
		try {
			users = delegate.getAllUsers(conn);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<User> getUserByUsername(String username) {
		try {
			users = delegate.getUserByUsername(conn, username);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<User> getUserByUsernameAndPassword(String username,
			String password) {
		try {
			users = delegate.getUserByUsernameAndPassword(conn, username,
					password);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<User> getUserByUserId(long id) {
		try {
			users = delegate.getUserByUserId(conn, id);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<User> getUsersWithAtleastOneLesson() {
		try {
			users = delegate.getUsersWithAtleastOneLesson(conn);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteUser(String username) {
		delegate.deleteUser(conn, username);
	}

	public ArrayList<User> saveUser(User user) {
		users = delegate.saveUser(conn, user);
		return users;
	}

	public int getAverageRating(int userid) {
		int rating = 0;
		try {
			rating = delegate.getAverageRating(conn, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rating;
	}

	public ArrayList<User> getTotalUsersParticipatedByLessonId(int lessonid) {
		try {
			users = delegate
					.getTotalUsersParticipatedByLessonId(conn, lessonid);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<User> getTotalUsersParticipatedByLessonIds(ArrayList<Integer> lessonids) {
		try {
			users = delegate
					.getTotalUsersParticipatedByLessonIds(conn, lessonids);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}