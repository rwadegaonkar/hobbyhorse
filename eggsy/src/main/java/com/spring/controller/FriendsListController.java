package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.manager.BadgeManager;
import com.spring.manager.FriendsListManager;

@Controller
@RequestMapping("/friendslists")
public class FriendsListController {
	public static final String FRIENDSLIST_VIEW_KEY = "friendsListView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showFriendsLists() {
		FriendsListManager friendslists = new FriendsListManager();
		friendslists.getAllFriendsList();
		ModelAndView mav = new ModelAndView(FRIENDSLIST_VIEW_KEY);
		mav.addObject("friendslists", friendslists);
		return mav;
	}
}
