package com.harry.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.servlet.ModelAndView;

import com.harry.core.CommonController;
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

public class WeeklyTopicController extends CommonController {
	private String viewPage;

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String siteUrlPrefix = "http://mosesenglish.com/theme.asp?year=2011&weekNum=";

		for (int i = 37; i >= 1; i--) {
			this.processsBusinessLogic(siteUrlPrefix + i);
		}

		return new ModelAndView(getViewPage());
	}

	// -----------------------------------------
	// Getters & Setters
	// -----------------------------------------
	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}

	private DailyTopicWordsAndQuote extractAndPopulateDailyTopicWords(
			String url, DailyTopicWordsAndQuote wordsAndQuote) {

		String htmlString = "";

		try {
			// get HTML String from web
			htmlString = this.readHtmlStreamAsString(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// populate data into java bean
		DailyWordsParser parser = new DailyWordsParser(htmlString,
				wordsAndQuote);
		parser.parse();

		return wordsAndQuote;
	}

	private void processsBusinessLogic(String siteUrl)
			throws ClientProtocolException, IOException {

		/*
		 * Reads HTML page source code from URL through http. And Convert it as
		 * a String Object.
		 */
		String htmlString = this.readHtmlStreamAsString(siteUrl);

		/*
		 * Parses HTML page. and put the related data into model object.
		 */
		WeeklyTopic weeklyTopicModel = new WeeklyTopic();
		weeklyTopicModel = this.parseHtmlStringIntoModel(htmlString,
				weeklyTopicModel);

		if (StringUtil.isEmpty(weeklyTopicModel.getTopic())
				|| StringUtil.isEmpty(weeklyTopicModel.getTopicDescription())) {
			// if no topic this week
			return;
		}

		/*
		 * Save model object information into database
		 */
		// Preparation Process. Get a ServiceManager to retrieve Service.
		ServiceManager mgr = ServiceManager.getInstance(getServletContext());
		WeeklyTopicService weeklyTopicSerice = (WeeklyTopicService) mgr
				.getService(WeeklyTopicService.class.getName());

		boolean weeklyTopicExists = weeklyTopicSerice.exists(
				weeklyTopicModel.getYear(), weeklyTopicModel.getWeek());

		if (weeklyTopicExists) {
			// topic exists, skip it

			if (logger.isDebugEnabled()) {
				logger.debug("topic exists. info:  "
						+ weeklyTopicModel.toString());
			}

			return;
		}

		// Saving Process. Save model information to database,
		int weekNumber = weeklyTopicSerice.addTopic(weeklyTopicModel.getYear(),
				weeklyTopicModel.getWeek(), weeklyTopicModel.getTopic(),
				weeklyTopicModel.getTopicDescription());

		if (logger.isDebugEnabled()) {
			logger.debug(weekNumber);
		}

		/*
		 * Save daily topic information into database
		 */
		// Get a Service to talk to database.
		DailyTopicService dailyTopicService = (DailyTopicService) mgr
				.getService(DailyTopicService.class.getName());

		// Save a list of daily topics to database
		List<DailyTopic> dailyTopics = weeklyTopicModel.getDailyTopics();
		for (DailyTopic dt : dailyTopics) {

			// check if weekday topic already existed
			boolean weekDayTopicAlreadyExists = dailyTopicService.exists(dt
					.getIssueDate());

			if (weekDayTopicAlreadyExists) {
				// weekday topic exists. skip it
				if (logger.isDebugEnabled()) {
					logger.debug("weekday topic exists. info :" + dt.toString());
				}

				return;
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
			DailyTopicWordsAndQuote wordsAndQuote = new DailyTopicWordsAndQuote();
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

				// check if the word exists
				boolean wordExists = dailyTopicWordsQuoteService.exists(
						aWord.getWord(), id);

				if (wordExists) {
					// word exists
					if (logger.isDebugEnabled()) {
						logger.debug("word exists. word info:"
								+ aWord.toString());
					}

					return;
				}

				// save back to database with id
				dailyTopicWordsQuoteService.addDailyTopicWords(aWord.getWord(),
						aWord.getDefinition(), id);
			}

		}

	}

	private String readHtmlStreamAsString(String htmlUrl)
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
}
