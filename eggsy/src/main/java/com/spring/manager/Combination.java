package com.spring.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.spring.datasource.AprioriLesson;
import com.spring.datasource.Lesson;
import com.spring.datasource.User;

public class Combination {
	public LinkedHashMap<String, ArrayList<String>> getCombinations(
			ArrayList<String> lessons, int size) {
		int[] indices;
		LinkedHashMap<String, ArrayList<String>> combinations = new LinkedHashMap<String, ArrayList<String>>();
		CombinationGenerator x = new CombinationGenerator(lessons.size(), size);
		ArrayList<String> combination;
		int count = 0;
		while (x.hasMore()) {
			count++;
			combination = new ArrayList<String>();
			indices = x.getNext();
			for (int i = 0; i < indices.length; i++) {
				combination.add(lessons.get(indices[i]));
			}
			combinations.put(combination.toString(), combination);
			ArrayList<String> reversedCombination = new ArrayList<String>();
			for (int a = combination.size() - 1; a >= 0; a--) {
				reversedCombination.add(combination.get(a));
			}
			combinations.put(reversedCombination.toString(),
					reversedCombination);
		}

		return combinations;
	}

	public static void main(String args[]) {
		Combination c = new Combination();
		UserManager userManager = new UserManager();
		LessonManager lessonManager = new LessonManager();
		AprioriLessonManager aprioriLessonManager = new AprioriLessonManager();
		ArrayList<User> userList = userManager.getAllUsers();
		ArrayList<Lesson> allAttendedLessons = null;
		HashMap<String, ArrayList<String>> attendLessonIdsSet = new HashMap<String, ArrayList<String>>();
		for (User user : userList) {
			allAttendedLessons = lessonManager.getLessonsByUsername(user
					.getUsername());
			ArrayList<String> attendedLessonIds = new ArrayList<String>();
			for (Lesson attendedLesson : allAttendedLessons) {
				attendedLessonIds.add(attendedLesson.getId() + "");
			}
			if (!attendedLessonIds.isEmpty()) {
				attendLessonIdsSet.put(attendedLessonIds.toString(),
						attendedLessonIds);
			}
		}
		ArrayList<String> lessonIds = new ArrayList<String>();
		ArrayList<Lesson> lessons = lessonManager.getAllLessons();
		int i = 0;
		for (Lesson lesson : lessons) {
			i++;
			lessonIds.add(lesson.getId() + "");
		}
		for (String lessonId : lessonIds) {
			ArrayList<User> k = userManager
					.getTotalUsersParticipatedByLessonId(Integer
							.parseInt(lessonId));
			LinkedHashMap<String, ArrayList<String>> combinations = c
					.getCombinations(lessonIds, 2);
			Iterator<String> itAllLessons = combinations.keySet().iterator();
			while (itAllLessons.hasNext()) {
				String key = itAllLessons.next();
				// System.out.println(key + "," + combinations.get(key));
				if (combinations.get(key).contains(lessonId)
						&& combinations.get(key).indexOf(lessonId) == 0) {
					int j = 0;
					Collection<ArrayList<String>> setAttendedLessons = attendLessonIdsSet
							.values();
					for (ArrayList<String> setAttendedLessonsArrayList : setAttendedLessons) {
						if (setAttendedLessonsArrayList
								.containsAll(combinations.get(key))) {
							j++;
						}
					}
					double sup = (Double.parseDouble(j + "") / Double
							.parseDouble(k.size() + ""));
					if (sup > 0) {
						AprioriLesson aprioriLesson = new AprioriLesson();
						aprioriLesson.setMain(combinations.get(key).get(0));
						aprioriLesson.setAssociation(combinations.get(key).get(
								1));
						aprioriLesson.setSupport((sup * 100) + "");
						aprioriLessonManager.saveAprioriLesson(aprioriLesson);
					}
				}
			}
		}
	}
}