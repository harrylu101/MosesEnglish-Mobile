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
}
