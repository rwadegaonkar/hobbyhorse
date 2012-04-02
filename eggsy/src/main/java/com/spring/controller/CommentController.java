package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.manager.BadgeManager;
import com.spring.manager.CommentManager;

@Controller
@RequestMapping("/comments")
public class CommentController {
	public static final String COMMENT_VIEW_KEY = "commentsView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showComments() {
		CommentManager comments = new CommentManager();
		comments.getAllComments();
		ModelAndView mav = new ModelAndView(COMMENT_VIEW_KEY);
		mav.addObject("comments", comments);
		return mav;
	}
}
