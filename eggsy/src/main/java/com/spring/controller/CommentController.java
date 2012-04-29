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

import com.spring.datasource.Comment;
import com.spring.datasource.Lesson;
import com.spring.datasource.Participants;
import com.spring.manager.BadgeManager;
import com.spring.manager.CommentManager;
import com.spring.manager.LessonManager;
import com.spring.manager.ParticipantsManager;

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
		ArrayList<Comment> allComments = comments.getAllComments();
		ModelAndView mav = new ModelAndView(COMMENT_VIEW_KEY);
		mav.addObject("comments", allComments);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView saveComment(@RequestBody Comment comment) {
		CommentManager commentSave = new CommentManager();
		ArrayList<Comment> savedComment = commentSave.saveComment(comment);
		ModelAndView mav = new ModelAndView(COMMENT_VIEW_KEY);
		mav.addObject("comments", savedComment);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/lessonid/{lessonid}")
	public ModelAndView getCommentsByLessonId(
			@PathVariable("lessonid") String lessonid) {
		CommentManager commentCheck = new CommentManager();
		ArrayList<Comment> commentsByLesson = commentCheck
				.getCommentsByLessonId(lessonid);
		ModelAndView mav = new ModelAndView(COMMENT_VIEW_KEY);
		mav.addObject("comments", commentsByLesson);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/checkcommented/{lessonid}/{username}")
	public ModelAndView checkCommented(
			@PathVariable("lessonid") String lessonid,
			@PathVariable("username") String username) {
		CommentManager commentCheck = new CommentManager();
		ArrayList<Comment> checkComments = commentCheck.checkCommented(
				lessonid, username);
		ModelAndView mav = new ModelAndView(COMMENT_VIEW_KEY);
		mav.addObject("comments", checkComments);
		return mav;
	}
}
