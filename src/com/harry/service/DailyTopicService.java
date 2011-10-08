package com.harry.service;

import java.util.List;
import java.util.Map;

import com.harry.core.CommonService;

public class DailyTopicService extends CommonService {

	/**
	 * Inserts an daily topic.
	 * 
	 * @param date
	 * @param word
	 * @param definition
	 * @param weeky_topicId
	 * @param linkUrl
	 * 
	 * @return
	 * 
	 */
	public int addDailyTopic(String date, String word, String definition,
			int weeky_topicId, String linkUrl) {

		final String SQL_DAILY_TOPIC_CREATE = "INSERT INTO daily_topics (topic_date,topic_word,topic_definition,topic_link,weekly_topic_id) VALUES (?,?,?,?,?)";

		return jdbcTemplate.update(SQL_DAILY_TOPIC_CREATE, new Object[] { date,
				word, definition, linkUrl, weeky_topicId });
	}

	public List<Map<String, Object>> getIdsAndUrls(int weeklyTopicId) {
		final String SQL_DAILY_TOPIC_GET_ID_AND_URL = "SELECT id, topic_link FROM daily_topics WHERE weekly_topic_id = ?";

		return jdbcTemplate.queryForList(SQL_DAILY_TOPIC_GET_ID_AND_URL,
				new Object[] { weeklyTopicId });
	}

	public int updateImageUrlandQuotes(int id, String imageUrl, String quoteEn,
			String quoteCn) {

		final String SQL_DAILY_TOPIC_UPDATE_IMAGE_AND_QUOTE = "UPDATE daily_topics SET topic_image_url = ?, quote_en=?, quote_cn =? WHERE id = ?";

		return jdbcTemplate.update(SQL_DAILY_TOPIC_UPDATE_IMAGE_AND_QUOTE,
				new Object[] { imageUrl, quoteEn, quoteCn, id });
	}
}
