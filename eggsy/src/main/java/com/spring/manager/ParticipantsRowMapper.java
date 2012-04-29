package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.Participants;

public class ParticipantsRowMapper {
	public ArrayList<Participants> convertParticipantsBean(ResultSet rs)
			throws SQLException {
		ArrayList<Participants> participants = new ArrayList<Participants>();
		while (rs.next()) {
			Participants participantBean = new Participants();
			participantBean.setId(rs.getInt("id"));
			participantBean.setName(rs.getString("name"));
			participantBean.setDescription(rs.getString("description"));
			participantBean.setIsDeleted(rs.getInt("isDeleted"));
			participantBean.setCreatedBy(rs.getString("createdBy"));
			participantBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			participantBean.setCreateDate(rs.getDate("createDate"));
			participantBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			participantBean.setUserId(rs.getInt("userId"));
			participantBean.setLessonId(rs.getInt("lessonId"));
			participantBean.setWasAttended(rs.getInt("wasAttended"));
			participants.add(participantBean);
		}
		return participants;
	}
}
