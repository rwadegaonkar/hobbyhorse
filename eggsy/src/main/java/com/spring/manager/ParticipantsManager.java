package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.ParticipantsDao;
import com.spring.datasource.Lesson;
import com.spring.datasource.Participants;

@XmlRootElement(name = "participants")
public class ParticipantsManager {
	@JsonIgnore
	private static final Logger logger = Logger
			.getLogger(ParticipantsManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	ParticipantsDao delegate = new ParticipantsDao();
	private ArrayList<Participants> participants = new ArrayList<Participants>();

	public ArrayList<Participants> getAllParticipants() {
		try {
			participants = delegate.getAllParticipants(conn);
			return participants;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method invoked when participant is added in a lesson
	public void saveParticipant(Participants participant) {
		participants = delegate.saveParticipant(conn, participant);
	}

	public ArrayList<Participants> checkIfLessonJoined(String lessonid,
			String username) {
		participants = delegate.checkIfLessonJoined(conn, lessonid, username);
		return participants;
	}
}
