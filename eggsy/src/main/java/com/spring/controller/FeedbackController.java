package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.manager.FeedbackManager;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {
	public static final String FEEDBACK_VIEW_KEY = "feedbackView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showFeedbacks() {
		FeedbackManager feedbacks = new FeedbackManager();
		feedbacks.getAllFeedbacks();
		ModelAndView mav = new ModelAndView(FEEDBACK_VIEW_KEY);
		mav.addObject("feedbacks", feedbacks);
		return mav;
	}
}
