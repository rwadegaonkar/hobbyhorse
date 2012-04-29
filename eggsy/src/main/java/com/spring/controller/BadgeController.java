package com.spring.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.datasource.CommonBean;
import com.spring.manager.BadgeManager;

@Controller
@RequestMapping("/badges")
public class BadgeController {
	public static final String BADGE_VIEW_KEY = "badgeView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showBadges() {
		BadgeManager badges = new BadgeManager();
		ArrayList<CommonBean> allBadges = badges.getAllBadges();
		ModelAndView mav = new ModelAndView(BADGE_VIEW_KEY);
		mav.addObject("badges", allBadges);
		return mav;
	}
}
