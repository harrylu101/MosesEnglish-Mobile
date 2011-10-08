package com.harry.domain.parser;

import org.apache.log4j.Logger;

import com.harry.constants.AppContants;
import com.harry.domain.DailyTopic;
import com.harry.domain.WeeklyTopic;
import com.harry.utils.StringUtil;
import com.mysql.jdbc.Constants;

public class WeeklyTopicParser {

	static final Logger logger = Logger.getLogger(WeeklyTopicParser.class);

	private static final String CONTENT_START_MARK = "<div id=\"lc\">";
	private static final String CONTENT_END_MARK = "<div id=\"rcWrapper\">";
	private static final String DESCRIPTION_END_MARK = "<h2 style=\"font-size:18px; color:#0066CC; font-weight:bold\"> 相关单词：</h2>";
	private static final String TITLE_START_MARK = "<h1 style=\"font-size:18px; color:#0066CC;\">";
	private static final String TITLE_END_MARK = "</h1></div>";

	private String htmlString;
	private int contentFromIndex;
	private int contentToIndex;
	private int lastReadIndex;

	private WeeklyTopic weeklyTopic;

	public WeeklyTopicParser(String htmlString, WeeklyTopic topic) {
		this.htmlString = htmlString;
		this.contentFromIndex = this.htmlString.indexOf(CONTENT_START_MARK);
		this.contentToIndex = this.htmlString.indexOf(CONTENT_END_MARK);

		this.weeklyTopic = topic;
	}

	public void parse() {
		this.parseTitle();
		this.parseDescription();
		this.parseWeekDay();
		// this.parseWeeklyWords();
	}

	private void parseTitle() {
		int titleFromIndex = htmlString.indexOf(TITLE_START_MARK,
				contentFromIndex);
		int titleToIndex = htmlString.indexOf(TITLE_END_MARK, titleFromIndex);

		this.lastReadIndex = titleToIndex;

		String title = htmlString.substring(
				titleFromIndex + TITLE_START_MARK.length(), titleToIndex);

		// extract out topic string, year, and week from title
		this.extractYearAndWeekFromTitle(title);

		if (logger.isDebugEnabled()) {
			logger.debug(title);
			logger.debug(this.weeklyTopic);
		}

	}

	private void parseDescription() {

		int descriptionFromIndex = this.lastReadIndex;
		int descriptionToIndex = this.htmlString.indexOf(DESCRIPTION_END_MARK,
				descriptionFromIndex);

		String description = htmlString.substring(descriptionFromIndex,
				descriptionToIndex);
		description = StringUtil.escapeHTMLTags(description).trim();

		this.lastReadIndex = descriptionToIndex;

		this.weeklyTopic.setTopicDescription(description);

		if (logger.isDebugEnabled()) {
			logger.debug(this.weeklyTopic);
		}
	}

	private void parseWeekDay() {

		int weekDayFromIndex = this.lastReadIndex
				+ DESCRIPTION_END_MARK.length();
		int weekDayToIndex = this.contentToIndex;

		// extract out related words
		String relatedWords = htmlString.substring(weekDayFromIndex,
				weekDayToIndex).trim();

		int lastIndex = 0;
		while (true) {

			// extract out word link
			String hrefFromMark = "<a href=\"";
			String hrefEndMark = "\">";
			int hrefFromIndex = relatedWords.indexOf(hrefFromMark, lastIndex);
			int hrefToIndex = relatedWords.indexOf("\">", hrefFromIndex);

			if (hrefFromIndex < 0) {
				break;
			}

			String topicWordLink = relatedWords.substring(hrefFromIndex
					+ hrefFromMark.length(), hrefToIndex);
			System.out.println(topicWordLink);

			// extract out word name
			int wordInfoFromIndex = hrefToIndex;
			int wordInfoToIndex = relatedWords.indexOf("</a>",
					wordInfoFromIndex);
			String wordInfo = relatedWords.substring(wordInfoFromIndex
					+ hrefEndMark.length(), wordInfoToIndex);
			wordInfo = wordInfo.replace("&nbsp;", " ").trim();

			int wordNameToIndex = wordInfo.indexOf(" ");
			int wordDateFromIndex = wordInfo.lastIndexOf(" ");

			String topicWord = wordInfo.substring(0, wordNameToIndex).trim();
			String topicWordDefintion = wordInfo.substring(wordNameToIndex,
					wordDateFromIndex).trim();
			String topicWordIssueDate = wordInfo.substring(wordDateFromIndex)
					.trim();

			// save daily word info to beans
			DailyTopic dt = new DailyTopic();
			dt.setWord(topicWord);
			dt.setDefinition(topicWordDefintion);
			dt.setIssueDate(topicWordIssueDate);
			dt.setTopicLink(AppContants.MOSES_URL + topicWordLink);
			this.weeklyTopic.addDailyTopic(dt);

			if (logger.isDebugEnabled()) {
				logger.debug(topicWord);
				logger.debug(topicWordDefintion);
				logger.debug(topicWordIssueDate);
				logger.debug(this.weeklyTopic);
			}

			lastIndex = wordInfoToIndex;
		}
	}

	private void extractYearAndWeekFromTitle(String str) {
		final String YEAR_MARK = "年";
		final String WEEK_END_MARK = "周";
		final String WEEK_START_MARK = "第";

		String topic = "";
		int year = 0;
		int week = 0;

		// year is four digit
		int yearToIndex = str.lastIndexOf(YEAR_MARK);
		int yearFromIndex = yearToIndex - 4;

		//
		int weekToIndex = str.lastIndexOf(WEEK_END_MARK);
		int weekFromIndex = str.lastIndexOf(WEEK_START_MARK);

		if (weekToIndex < 0 || weekFromIndex < 0 || yearFromIndex < 0
				|| yearToIndex < 0) {

			if (logger.isDebugEnabled()) {
				logger.debug(weekFromIndex);
				logger.debug(weekToIndex);
				logger.debug(yearFromIndex);
				logger.debug(yearToIndex);
			}

			return;
		}

		topic = str.substring(0, yearFromIndex);
		year = StringUtil.convertStringToInt(str.substring(yearFromIndex,
				yearToIndex));
		week = StringUtil.convertStringToInt(str.substring(weekFromIndex + 1,
				weekToIndex));

		// set to bean
		this.weeklyTopic.setTopic(topic);
		this.weeklyTopic.setYear(Math.max(year, 0));
		this.weeklyTopic.setWeek(Math.max(week, 0));

	}

	public static void main(String[] args) {
		String testStr = "见到袁新民老师了第一集2011年 第9周";

	}
}
