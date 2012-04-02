package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import com.spring.manager.LoginManager;

@Controller
@RequestMapping("/logins")
public class LoginController {
	public static final String LOGIN_VIEW_KEY = "loginView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showLogins() {
		LoginManager logins = new LoginManager();
		logins.getAllLogins();
		ModelAndView mav = new ModelAndView(LOGIN_VIEW_KEY);
		mav.addObject("logins", logins);
		return mav;
	}
}
