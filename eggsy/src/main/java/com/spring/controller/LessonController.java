package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.manager.LessonManager;

@Controller
@RequestMapping("/lessons")
public class LessonController {
	public static final String LESSON_VIEW_KEY = "lessonView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showLessons() {
		LessonManager lessons = new LessonManager();
		lessons.getAllLessons();
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessons);
		return mav;
	}
}
