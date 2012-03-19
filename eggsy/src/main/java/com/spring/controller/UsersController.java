package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.ModelAndView;

import com.spring.datasource.Classroom;
import com.spring.datasource.User;

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

	@RequestMapping(method = RequestMethod.GET, value = "/{nonDel}")
	public ModelAndView showUsers(@PathVariable("nonDel") String nonDel) {
		Classroom classOfStudents = new Classroom();
		classOfStudents.getNonDeletedUsers(nonDel);
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("classRoom", classOfStudents);
		return mav;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
	public ModelAndView deleteUser(@PathVariable("id") String id) {
		Classroom classOfStudents = new Classroom();
		classOfStudents.deleteUser(id);
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("classRoom", classOfStudents);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView saveUser(@RequestBody User user) {
		Classroom classOfStudents = new Classroom();
		classOfStudents.saveUser(user);
		ModelAndView mav = new ModelAndView(USERS_VIEW_KEY);
		mav.addObject("userList", classOfStudents);
		return mav;
	}

}