package com.harry.service.api;

import java.util.List;
import java.util.Map;

import com.harry.core.CommonService;

public class WordApiService extends CommonService {

	/**
	 * 
	 * Get words by weekday topic id
	 * 
	 * @param topicId
	 * @return
	 */
	public List<Map<String, Object>> getWordsByWeekdayTopicId(int topicId) {
		final String SQL_GET_WEEKLY_TOPIC_AFTER = "SELECT * FROM daily_words WHERE topic_id = ?";

		return super.jdbcTemplate.queryForList(SQL_GET_WEEKLY_TOPIC_AFTER,
				new Object[] { topicId });
	}

	/**
	 * Get random words
	 * 
	 * @param limit
	 *            size of data
	 * @return
	 */
	public List<Map<String, Object>> getRandomWords(int limit) {
		// old : a slow way
		// final String SQL_GET_RANDOM_WORDS =
		// "SELECT * FROM daily_words ORDER BY RAND() LIMIT ?";

		// now : faster way
		final String SQL_GET_RANDOM_WORDS = "SELECT * FROM daily_words WHERE id >= (SELECT FLOOR( MAX(id) * RAND()) FROM daily_words ) ORDER BY id LIMIT ?";

		return super.jdbcTemplate.queryForList(SQL_GET_RANDOM_WORDS,
				new Object[] { limit });
	}

}
