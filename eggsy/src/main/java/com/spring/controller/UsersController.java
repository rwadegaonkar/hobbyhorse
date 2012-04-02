package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.datasource.User;
import com.spring.manager.UserManager;

/**
 * @author Eggsy - eggsy_at_eggsylife.co.uk
 * 
 */
@Controller
@RequestMapping("/users")
public class UsersController {

	public static final String USERS_VIEW_KEY = "usersView";

	// we need a special property-editor that knows how to bind the data
	// from the request to a byte[]
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showUsers() {
		UserManager users = new UserManager();
		users.getAllUsers();
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("users", users);
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
		userSave.saveUser(user);
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("newuser", userSave);
		return mav;
	}

}