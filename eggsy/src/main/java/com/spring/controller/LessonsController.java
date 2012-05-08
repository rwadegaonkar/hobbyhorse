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

	@RequestMapping(method = RequestMethod.POST, value = "/updateislive", headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView updateLessonIsLive(@RequestBody Lesson lesson) {
		LessonManager lessonSave = new LessonManager();
		lessonSave.updateLessonIsLive(lesson);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonSave);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/lessontype/{lessontypeid}")
	public ModelAndView getLessonsByLessonId(
			@PathVariable("lessontypeid") int lessontypeid) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType
				.getLessonsByLessonTypeId(lessontypeid);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/attended/{username}")
	public ModelAndView getLessonsAttendedByUser(
			@PathVariable("username") String username) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType
				.getLessonsAttendedByUser(username);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/{username}")
	public ModelAndView getLessonsByUsername(
			@PathVariable("username") String username) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType
				.getLessonsByUsername(username);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/lastlesson/{userId}")
	public ModelAndView getLastLessonByUser(@PathVariable("userId") int userId) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType
				.getLastLessonByUser(userId);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
	public ModelAndView getLessonByLessonId(@PathVariable("id") int id) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType.getLessonByLessonId(id);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/suggest/{name}/{category}/{username}")
	public ModelAndView getSuggestedLesson(@PathVariable("name") String name,
			@PathVariable("category") String category,
			@PathVariable("username") String username) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType.getSuggestedLesson(name,
				category, username);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/suggestapriori/{lessonid}")
	public ModelAndView getSuggestedLessonsApriori(
			@PathVariable("lessonid") String lessonid) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType
				.getSuggestedLessonsApriori(lessonid);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/expert/{userId}")
	public ModelAndView getLessonsByExpert(
			@PathVariable("userId") String userId) {
		LessonManager lessonsByType = new LessonManager();
		ArrayList<Lesson> lessonList = lessonsByType
				.getLessonsByExpert(userId);
		ModelAndView mav = new ModelAndView(LESSON_VIEW_KEY);
		mav.addObject("lessons", lessonList);
		return mav;
	}
}
