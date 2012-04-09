package com.spring.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.datasource.Lesson;
import com.spring.manager.LessonManager;

@Controller
@RequestMapping("/lessons")
public class LessonsController {
	public static final String LESSON_VIEW_KEY = "lessonsView";

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showLessons() {
		LessonManager allLessons = new LessonManager();
		ArrayList<Lesson> lessonList = allLessons.getAllLessons();
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView saveLesson(@RequestBody Lesson lesson) {
		LessonManager lessonSave = new LessonManager();
		lessonSave.saveLesson(lesson);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonSave);
		return mav;
	}	

	@RequestMapping(method = RequestMethod.GET, value = "/lessontype/{lessontypeid}")
	public ModelAndView getLessonsByLessonId(@PathVariable("lessontypeid") int lessontypeid) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType.getLessonsByLessonTypeId(lessontypeid);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}
}
