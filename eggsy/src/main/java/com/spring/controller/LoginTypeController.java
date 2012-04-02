package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import com.spring.manager.LoginTypeManager;

@Controller
@RequestMapping("/logintypes")
public class LoginTypeController {
	public static final String LOGINTYPE_VIEW_KEY = "loginTypeView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showBadges() {
		LoginTypeManager loginTypes = new LoginTypeManager();
		loginTypes.getAllLoginTypes();
		ModelAndView mav = new ModelAndView(LOGINTYPE_VIEW_KEY);
		mav.addObject("loginTypes", loginTypes);
		return mav;
	}
}
