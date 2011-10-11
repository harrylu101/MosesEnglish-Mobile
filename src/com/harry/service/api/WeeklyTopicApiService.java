package com.harry.service.api;

import java.util.List;
import java.util.Map;

import com.harry.core.CommonService;

public class WeeklyTopicApiService extends CommonService {

	/**
	 * 
	 * Get weekly topics after a certain year and week
	 * 
	 * @param year
	 * @param week
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getWeeklyTopicsAfter(int year, int week,
			int size) {
		final String SQL_GET_WEEKLY_TOPIC_AFTER = "SELECT * FROM weekly_topics WHERE topic_year >=? AND topic_week >=?  ORDER BY topic_year, topic_week LIMIT ?";

		return super.jdbcTemplate.queryForList(SQL_GET_WEEKLY_TOPIC_AFTER,
				new Object[] { year, week, size });
	}

	/**
	 * 
	 * Get weekly topics before a certain year and week
	 * 
	 * @param year
	 * @param week
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getWeeklyTopicsBefore(int year, int week,
			int size) {
		final String SQL_GET_WEEKLY_TOPIC_AFTER = "SELECT * FROM weekly_topics WHERE topic_year <=? AND topic_week <=?  ORDER BY topic_year DESC, topic_week DESC LIMIT ?";

		return super.jdbcTemplate.queryForList(SQL_GET_WEEKLY_TOPIC_AFTER,
				new Object[] { year, week-1, size });
	}

	/**
	 * 
	 * Get a weekly topic of a certain year and week
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public Map<String, Object> getWeeklyTopic(int year, int week) {
		final String SQL_GET_WEEKLY_TOPIC_AFTER = "SELECT * FROM weekly_topics WHERE topic_year = ? AND topic_week = ?";

		return super.jdbcTemplate.queryForMap(SQL_GET_WEEKLY_TOPIC_AFTER,
				new Object[] { year, week });
	}

	/**
	 * 
	 * Get a weekly topic with its id
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getWeeklyTopicById(int id) {
		final String SQL_GET_WEEKLY_TOPIC_AFTER = "SELECT * FROM weekly_topics WHERE id = ?";

		return super.jdbcTemplate.queryForMap(SQL_GET_WEEKLY_TOPIC_AFTER,
				new Object[] { id });
	}

	/**
	 * 
	 * Get weekly topic random id(s)
	 * 
	 * @param size
	 *            size of data
	 * @return
	 */
	public List<Map<String, Object>> getWeeklyTopicRandomIds(int size) {
		final String SQL_GET_WEEKLY_TOPIC_RANDOM_IDS = "SELECT id FROM weekly_topics ORDER BY RAND() LIMIT ? ";
		return super.jdbcTemplate.queryForList(SQL_GET_WEEKLY_TOPIC_RANDOM_IDS,
				new Object[] { size });
	}

	/**
	 * Get latest weekly topics
	 * 
	 * @param size
	 *            size of data
	 * @return
	 */
	public List<Map<String, Object>> getWeeklyTopicLatest(int size) {
		final String SQL_GET_WEEKLY_TOPIC_LASTEST = "SELECT * FROM weekly_topics ORDER BY topic_year DESC,topic_week DESC LIMIT ? ";
		return super.jdbcTemplate.queryForList(SQL_GET_WEEKLY_TOPIC_LASTEST,
				new Object[] { size });
	}
}
