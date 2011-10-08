package com.harry.service.api;

import java.util.List;
import java.util.Map;

import com.harry.core.CommonService;

public class WeekdayTopicApiService extends CommonService {

	/**
	 * 
	 * Get weekday topics with weekly topic id
	 * 
	 * @param weeklyTopicId
	 * @return
	 */
	public List<Map<String, Object>> getWeekDayTopicsByWeekTopicId(
			int weeklyTopicId) {
		final String SQL_GET_WEEKDAY_TOPIC_AND_WORDS = "SELECT * FROM daily_topics WHERE weekly_topic_id = ?";

		return super.jdbcTemplate.queryForList(SQL_GET_WEEKDAY_TOPIC_AND_WORDS,
				new Object[] { weeklyTopicId });
	}

	/**
	 * 
	 * Get weekday topics of a certain year and week
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public List<Map<String, Object>> getWeekdayTopicsByYearAndWeek(int year,
			int week) {
		final String SQL_GET_WEEKDAY_TOPICS_BY_YEAR_AND_WEEK = "SELECT * FROM daily_topics WHERE weekly_topic_id =(SELECT ID FROM weekly_topics WHERE topic_year = ? AND topic_week = ?)";
		return super.jdbcTemplate.queryForList(
				SQL_GET_WEEKDAY_TOPICS_BY_YEAR_AND_WEEK, new Object[] { year,
						week });
	}

	/**
	 * 
	 * Get week day topic of a certain date
	 * 
	 * @param date
	 * @return
	 */
	public Map<String, Object> getWeekdayTopicByDate(String date) {
		final String SQL_GET_WEEKDAY_TOPICS_BY_YEAR_AND_WEEK = "SELECT * FROM daily_topics WHERE topic_date= ?";
		return super.jdbcTemplate.queryForMap(
				SQL_GET_WEEKDAY_TOPICS_BY_YEAR_AND_WEEK, new Object[] { date });
	}

	/**
	 * 
	 * Get weekday topic by its id
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getWeekdayTopicById(int id) {
		final String SQL_GET_WEEKDAY_TOPICS_BY_YEAR_AND_WEEK = "SELECT * FROM daily_topics WHERE id= ?";
		return super.jdbcTemplate.queryForMap(
				SQL_GET_WEEKDAY_TOPICS_BY_YEAR_AND_WEEK, new Object[] { id });
	}

	/**
	 * 
	 * Get a array of id of random weekday topics
	 * 
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getWeekDayTopicRandomIds(int limit) {
		final String SQL_GET_WEEKDAY_TOPIC_RANDOM_IDS = "SELECT id FROM daily_topics ORDER BY RAND() LIMIT ? ";
		return super.jdbcTemplate.queryForList(
				SQL_GET_WEEKDAY_TOPIC_RANDOM_IDS, new Object[] { limit });
	}
}
