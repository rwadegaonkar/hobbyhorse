package com.spring.controller;

import java.util.ArrayList;

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

import com.spring.datasource.Lesson;
import com.spring.datasource.Participants;
import com.spring.manager.BadgeManager;
import com.spring.manager.LessonManager;
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
		ArrayList<Participants> allparticipants = participants.getAllParticipants();
		ModelAndView mav = new ModelAndView(PARTICIPANTS_VIEW_KEY);
		mav.addObject("participants", allparticipants);
		return mav;
	}
	//Method is called when a participant joins a lesson
	@RequestMapping(method = RequestMethod.POST, value = "/join", headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView addParticipants(@RequestBody Participants participant) {
		ParticipantsManager participants = new ParticipantsManager();
		participants.saveParticipant(participant);
		ModelAndView mav = new ModelAndView(PARTICIPANTS_VIEW_KEY);
		mav.addObject("participants", participants);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/checkjoined/{lessonid}/{username}")
	public ModelAndView checkParticipants(@PathVariable("lessonid") String lessonid, @PathVariable("username") String username) {
		ParticipantsManager participants = new ParticipantsManager();
		ArrayList<Participants> allparticipants = participants.checkIfLessonJoined(lessonid,username);
		ModelAndView mav = new ModelAndView(PARTICIPANTS_VIEW_KEY);
		mav.addObject("participants", allparticipants);
		return mav;
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "/updatewasattended", headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView updateWasAttended(@RequestBody Participants participant) {
		ParticipantsManager participantUpdate = new ParticipantsManager();
		participantUpdate.updateParticipantAttendance(participant);
		ModelAndView mav = new ModelAndView(PARTICIPANTS_VIEW_KEY);
		mav.addObject("participants", participantUpdate);
		return mav;
	}	
}
