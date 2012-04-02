package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import com.spring.manager.LessonTypeManager;

@Controller
@RequestMapping("/lessontypes")
public class LessonTypeController {
	public static final String LESSONTYPE_VIEW_KEY = "lessonTypeView";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showLessonTypes() {
		LessonTypeManager lessonTypes = new LessonTypeManager();
		lessonTypes.getAllLessonTypes();
		ModelAndView mav = new ModelAndView(LESSONTYPE_VIEW_KEY);
		mav.addObject("lessonTypes", lessonTypes);
		return mav;
	}
}
