package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.manager.ForgotPasswordManager;

@Controller
@RequestMapping("/forgotpasswords")
public class ForgotPasswordController {
	public static final String FORGOTPASSWORD_VIEW_KEY = "forgotPasswordView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showForgotPasswords() {
		ForgotPasswordManager forgotpasswords = new ForgotPasswordManager();
		forgotpasswords.getAllForgotPasswords();
		ModelAndView mav = new ModelAndView(FORGOTPASSWORD_VIEW_KEY);
		mav.addObject("forgotpasswords", forgotpasswords);
		return mav;
	}
}
