package com.harry.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.harry.core.ServiceManager;
import com.harry.domain.DailyTopic;
import com.harry.domain.DailyTopicWordsAndQuote;
import com.harry.domain.DailyWord;
import com.harry.domain.WeeklyTopic;
import com.harry.domain.parser.DailyWordsParser;
import com.harry.domain.parser.WeeklyTopicParser;
import com.harry.service.DailyTopicService;
import com.harry.service.DailyTopicWordsAndQuoteService;
import com.harry.service.WeeklyTopicService;
import com.harry.utils.HttpConnectionUtil;
import com.harry.utils.StringUtil;

public class DataFetcher {

	static Logger logger = Logger.getLogger(DataFetcher.class);

	private ServletContext context;

	//

	private DailyTopicWordsAndQuote wordsAndQuote;
	private WeeklyTopic weeklyTopic;

	//
	public DataFetcher(ServletContext context) {
		this.context = context;
	}

	private DailyTopicWordsAndQuote extractAndPopulateDailyTopicWords(
			String url, DailyTopicWordsAndQuote wordsAndQuote) {

		String htmlString = "";

		try {
			// get HTML String from web
			htmlString = readHtmlStreamAsString(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (StringUtil.isEmpty(htmlString)) {
			logger.error("html string is empty");
			return new DailyTopicWordsAndQuote();
		}

		// populate data into java bean
		DailyWordsParser parser = new DailyWordsParser(htmlString,
				wordsAndQuote);
		parser.parse();

		return wordsAndQuote;
	}

	public void processsBusinessLogic(String siteUrl)
			throws ClientProtocolException, IOException {

		/*
		 * Reads HTML page source code from URL through http. And Convert it as
		 * a String Object.
		 */
		String htmlString = readHtmlStreamAsString(siteUrl);

		/*
		 * Parses HTML page. and put the related data into model object.
		 */
		weeklyTopic = new WeeklyTopic();
		weeklyTopic = this.parseHtmlStringIntoModel(htmlString, weeklyTopic);

		if (StringUtil.isEmpty(weeklyTopic.getTopic())
				|| StringUtil.isEmpty(weeklyTopic.getTopicDescription())) {
			// if no topic this week
			return;
		}

		/*
		 * Save model object information into database
		 */
		// Preparation Process. Get a ServiceManager to retrieve Service.
		ServiceManager mgr = ServiceManager.getInstance(this.context);
		WeeklyTopicService weeklyTopicSerice = (WeeklyTopicService) mgr
				.getService(WeeklyTopicService.class.getName());

		int weekNumber = -1;

		boolean weeklyTopicExists = weeklyTopicSerice.exists(
				weeklyTopic.getYear(), weeklyTopic.getWeek());

		// if weekly topic exists, get its id, if not, insert entry and get its
		// id
		if (weeklyTopicExists) {
			weekNumber = weeklyTopicSerice.getTopicIdByYearAndWeek(
					weeklyTopic.getYear(), weeklyTopic.getWeek());
		} else {
			weekNumber = weeklyTopicSerice.addTopic(weeklyTopic.getYear(),
					weeklyTopic.getWeek(), weeklyTopic.getTopic(),
					weeklyTopic.getTopicDescription());
		}

		if (weekNumber < 0) {
			// error
			logger.error("week number < 0. ");
			return;
		}

		/*
		 * Save daily topic information into database
		 */

		// Get a Service to talk to database.
		DailyTopicService dailyTopicService = (DailyTopicService) mgr
				.getService(DailyTopicService.class.getName());

		// Save a list of daily topics to database
		List<DailyTopic> dailyTopics = weeklyTopic.getDailyTopics();
		for (DailyTopic dt : dailyTopics) {

			// check if weekday topic already existed
			boolean weekDayTopicExists = dailyTopicService.exists(dt
					.getIssueDate());

			if (weekDayTopicExists) {
				// weekday topic exists. skip it
				if (logger.isDebugEnabled()) {
					logger.debug("weekday topic exists. info :" + dt.toString());
				}

				break;
			}

			dailyTopicService.addDailyTopic(dt.getIssueDate(), dt.getWord(),
					dt.getDefinition(), weekNumber, dt.getTopicLink());
		}

		/*
		 * Read each of the daily topic, and save its information to database.
		 */

		// Get a Service to talk to database
		DailyTopicWordsAndQuoteService dailyTopicWordsQuoteService = (DailyTopicWordsAndQuoteService) mgr
				.getService(DailyTopicWordsAndQuoteService.class.getName());

		// Get Ids and Urls
		List<Map<String, Object>> idsAndUrls = dailyTopicService
				.getIdsAndUrls(weekNumber);

		// Talk the list of Ids and Urls to Parse daily topic detail words and
		// quotes information;
		for (Map<String, Object> idAndUrl : idsAndUrls) {
			// request info from url
			int id = (Integer) idAndUrl.get("id");
			String url = (String) idAndUrl.get("topic_link");

			if (logger.isDebugEnabled()) {
				logger.debug(id);
				logger.debug(url);
			}

			// Read htmlString and populate data model
			wordsAndQuote = new DailyTopicWordsAndQuote();
			wordsAndQuote = extractAndPopulateDailyTopicWords(url,
					wordsAndQuote);

			// Save information in data model to database
			dailyTopicService.updateImageUrlandQuotes(id,
					wordsAndQuote.getImageUrl(),
					wordsAndQuote.getEnglishQuote(),
					wordsAndQuote.getChineseQuote());

			// Get the daily words list and walk through them.
			// Save the data while talking through
			List<DailyWord> dailyWords = wordsAndQuote.getWords();

			for (DailyWord aWord : dailyWords) {

				boolean wordExists = dailyTopicWordsQuoteService.exists(
						aWord.getWord(), id);

				if (wordExists) {
					// skip insertion
					break;
				}

				// save back to database with id
				dailyTopicWordsQuoteService.addDailyTopicWords(aWord.getWord(),
						aWord.getDefinition(), id);
			}

		}

	}

	public static String readHtmlStreamAsString(String htmlUrl)
			throws ClientProtocolException, IOException {
		InputStream is = HttpConnectionUtil.openHttpConnection(htmlUrl);
		String htmlString = StringUtil.convertStreamToString(is);
		return htmlString;
	}

	private WeeklyTopic parseHtmlStringIntoModel(String htmlString,
			WeeklyTopic model) {

		WeeklyTopicParser wwp = new WeeklyTopicParser(htmlString, model);
		wwp.parse();

		return model;
	}

	// -----------------------------------------
	// Getters & Setters
	// -----------------------------------------

	/**
	 * Should be called after processsBusinessLogic(String)
	 * 
	 * @return
	 */
	public DailyTopicWordsAndQuote getWordsAndQuote() {
		return wordsAndQuote;
	}

	/**
	 * Should be called after processsBusinessLogic(String)
	 * 
	 * @return
	 */
	public WeeklyTopic getWeeklyTopic() {
		return weeklyTopic;
	}

}
