package com.spring.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import com.spring.datasource.Lesson;
import com.spring.datasource.Participants;
import com.spring.datasource.User;

public class Comibation_New {
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
			// ArrayList<String> reversedCombination = new ArrayList<String>();
			// for (int a = combination.size() - 1; a >= 0; a--) {
			// reversedCombination.add(combination.get(a));
			// }
			// combinations.put(reversedCombination.toString(),
			// reversedCombination);
		}
		return combinations;
	}

	public static void main(String args[]) {
		Comibation_New comb = new Comibation_New();
		LessonManager lessonManager = new LessonManager();
		UserManager userManager = new UserManager();
		ParticipantsManager participantManager = new ParticipantsManager();
		ArrayList<Lesson> lessonList = lessonManager.getAllLessons();
		ArrayList<String> lessonIds = new ArrayList<String>();
		HashSet<ArrayList<String>> freshSets = new HashSet<ArrayList<String>>();

		//
		// ArrayList<User> userList =
		// userManager.getUsersWithAtleastOneLesson();
		int j = 0;
		for (Lesson lesson : lessonList) {
			j++;
			lessonIds.add(lesson.getId() + "");
		}
		for (int i = 1; i < 3; i++) {
			if (lessonIds.size() == 0) {
				break;
			}
			LinkedHashMap<String, ArrayList<String>> combinations = comb
					.getCombinations(lessonIds, i);
			Iterator<String> itAllLessons = combinations.keySet().iterator();

			while (itAllLessons.hasNext()) {
				HashMap<String, Integer> userLessonCount = new HashMap<String, Integer>();
				String key = itAllLessons.next();
				ArrayList<User> userList = userManager
						.getUsersWithAtleastOneLesson();
				Iterator<String> itsingleLessonId = combinations.get(key)
						.iterator();
				while (itsingleLessonId.hasNext()) {
					String lessonid = itsingleLessonId.next();
					for (User user : userList) {
						ArrayList<Participants> usersAttended = participantManager
								.checkIfLessonJoined(lessonid,
										user.getUsername());
						if (usersAttended.size() > 0) {
							if (!userLessonCount
									.containsKey(user.getUsername())) {
								userLessonCount.put(user.getUsername(), 1);
								// System.out.println("******USERID: "
								// + usersAttended.get(0).getUserId()
								// + "..KEY.." + key + ".I.." + i
								// + ".LESSONID.." + lessonid);
							} else {

								int cnt = userLessonCount.get(user
										.getUsername());
								userLessonCount
										.put(user.getUsername(), cnt + 1);
							}
						}
					}
				}
				Iterator<String> hash = userLessonCount.keySet().iterator();
				while (hash.hasNext()) {
					String k = hash.next();
					int v = userLessonCount.get(k);
					if (v < i) {
						Iterator<String> itsingleLessonId2 = combinations.get(
								key).iterator();
						while (itsingleLessonId2.hasNext()) {
							String lessonid = itsingleLessonId2.next();
							if (lessonIds.contains(lessonid) && i == 1) {
								System.out.println("REMOVING for key" + key
										+ ": " + lessonid + " with count: " + v
										+ " and i=" + i);
								lessonIds.remove(lessonid);
							}
						}
					} else {
						freshSets.add(combinations.get(key));
						Iterator<String> itsingleLessonId2 = combinations.get(
								key).iterator();
						while (itsingleLessonId2.hasNext()) {
							String lessonid = itsingleLessonId2.next();
							if (!lessonIds.contains(lessonid)) {
								System.out.println("ADDING for key" + key
										+ ": " + lessonid + " with count: " + v
										+ " and i=" + i);
								lessonIds.add(lessonid);
							}
						}
					}
				}

			}
		}
		for (ArrayList<String> lesson : freshSets) {
			System.out.println(lesson.toString());
		}
	}
}
