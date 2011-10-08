package com.harry.service;

import java.util.List;
import java.util.Map;

import com.harry.core.CommonService;

public class DailyTopicWordsAndQuoteService extends CommonService {

	public int addDailyTopicWords(String word, String defintion, int topicId) {

		final String SQL_DAILY_WORDS_CREATE = "INSERT INTO daily_words (word,defintion,topic_id) VALUES (?,?,?)";

		return jdbcTemplate.update(SQL_DAILY_WORDS_CREATE, new Object[] { word,
				defintion, topicId });
	}

	/**
	 * 
	 * @param word
	 * @param topicId
	 * @return
	 */
	public boolean exists(String word, int topicId) {

		final String SQL_WORDS_EXISTS = "SELECT * FROM daily_words WHERE word=? AND topic_id = ?";

		List<Map<String, Object>> words = super.jdbcTemplate.queryForList(
				SQL_WORDS_EXISTS, new Object[] { word, topicId });

		return !words.isEmpty();
	}

}
