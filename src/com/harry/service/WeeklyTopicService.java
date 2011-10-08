package com.harry.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.harry.core.CommonService;

public class WeeklyTopicService extends CommonService {

	static final Logger logger = Logger.getLogger(WeeklyTopicService.class);

	/**
	 * 
	 * Inserts an weekly topic into database.
	 * 
	 * @param year
	 * @param week
	 * @param topicName
	 * @param topicDescription
	 * @return the id of inserted topic if successful, otherwise -1
	 */
	public int addTopic(int year, int week, String topicName,
			String topicDescription) {
		final String SQL_WEEKLY_TOPIC_CREATE = "INSERT INTO weekly_topics (topic_year,topic_week,topic_name,topic_description) VALUES (?,?,?,?)";

		int affectedRows = super.jdbcTemplate.update(SQL_WEEKLY_TOPIC_CREATE,
				new Object[] { year, week, topicName, topicDescription });

		if (logger.isDebugEnabled()) {
			logger.debug("affected rows :" + affectedRows);
		}

		if (affectedRows <= 0) {
			// failed to insert into database

			return -1;
		} else {
			return this.getTopicIdByYearAndWeek(year, week);
		}

	}

	/**
	 * get weekly topic id by specifying year and week
	 * 
	 * @param year
	 * @param week
	 * 
	 * @return topicId
	 */
	public int getTopicIdByYearAndWeek(int year, int week) {
		final String SQL_WEEKY_TOPIC_GET_ID_BY_YEAR_AND_WEEK = "SELECT id FROM weekly_topics WHERE topic_year = ? and topic_week = ?";
		return jdbcTemplate.queryForInt(
				SQL_WEEKY_TOPIC_GET_ID_BY_YEAR_AND_WEEK, new Object[] { year,
						week });
	}

	public int deleteAllTopics() {
		final String SQL_DELETE_ALL = "DELETE FROM weekly_topics";
		return jdbcTemplate.update(SQL_DELETE_ALL);
	}

	/**
	 * Get data from database. if data size is 0, then return false; otherwise
	 * true.
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public boolean exists(int year, int week) {
		final String SQL_WEEKLY_TOPIC_EXISTS = "SELECT * FROM weekly_topics WHERE topic_year = ? AND topic_week = ?";
		List<Map<String, Object>> weeklyTopics = super.jdbcTemplate
				.queryForList(SQL_WEEKLY_TOPIC_EXISTS, new Object[] { year,
						week });

		return !weeklyTopics.isEmpty();
	}
}
