package com.spring.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.datasource.Login;
import com.spring.datasource.User;
import com.spring.manager.UserManager;

@Controller
@RequestMapping("/users")
public class UsersController {

	public static final String USERS_VIEW_KEY = "usersView";

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showUsers() {
		UserManager allUsers = new UserManager();
		ArrayList<User> userList = allUsers.getAllUsers();
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("users", userList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public ModelAndView showUserByUsername(@PathVariable("username") String username) {
		UserManager users = new UserManager();
		ArrayList<User> userList = users.getUserByUsername(username);
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("users", userList);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
	public ModelAndView showUserByUserId(@PathVariable("id") long id) {
		UserManager users = new UserManager();
		ArrayList<User> userList = users.getUserByUserId(id);
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("users", userList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{username}")
	public ModelAndView deleteUser(@PathVariable("username") String username) {
		UserManager userDelete = new UserManager();
		userDelete.deleteUser(username);
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView saveUser(@RequestBody User user) {
		UserManager userSave = new UserManager();
		ArrayList<User> savedUser = userSave.saveUser(user);
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("users", savedUser);
		return mav;
	}
	//Method for login
	@RequestMapping(method = RequestMethod.POST, value = "/login", headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView loginUser(@RequestBody User user) {
		UserManager userLogin = new UserManager();
		ArrayList<User> userList = userLogin.getUserByUsernameAndPassword(user.getUsername(),user.getPassword());
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("users", userList);
		return mav;
	}
}