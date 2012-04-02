package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.manager.BadgeManager;
import com.spring.manager.ParticipantsManager;

@Controller
@RequestMapping("/participants")
public class ParticipantsController {
	public static final String PARTICIPANTS_VIEW_KEY = "participantsView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showParticipants() {
		ParticipantsManager participants = new ParticipantsManager();
		participants.getAllParticipants();
		ModelAndView mav = new ModelAndView(PARTICIPANTS_VIEW_KEY);
		mav.addObject("participants", participants);
		return mav;
	}
}
