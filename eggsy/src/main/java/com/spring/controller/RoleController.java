package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.manager.RoleManager;

@Controller
@RequestMapping("/roles")
public class RoleController {
	public static final String BADGE_VIEW_KEY = "badgeView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showRoles() {
		RoleManager roles = new RoleManager();
		roles.getAllRoles();
		ModelAndView mav = new ModelAndView(BADGE_VIEW_KEY);
		mav.addObject("roles", roles);
		return mav;
	}
}
