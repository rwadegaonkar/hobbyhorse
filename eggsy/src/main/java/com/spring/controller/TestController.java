package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.manager.UserManager;

@Controller
@RequestMapping("/radhika")
public class TestController {
	public static final String USERS_VIEW_KEY = "usersView";

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showUsers() {
		UserManager allUsers = new UserManager();
		allUsers.getAllUsers();
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("test", allUsers);
		return mav;
	}
}
