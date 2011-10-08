package com.harry.web.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.harry.core.CommonController;
import com.harry.core.ServiceManager;
import com.harry.domain.api.ResponseApiMessage;
import com.harry.service.api.WeekdayTopicApiService;
import com.harry.service.api.WeeklyTopicApiService;
import com.harry.utils.ModelAndViewUtils;
import com.harry.utils.StringUtil;

public class WeekdayTopicApiController extends CommonController {

	public static final String MODEL_KEY_WEEKDAY_TOPICS = "week_day_topics";

	public static final String MODEL_KEY_WEEKDAY_TOPIC = "week_day_topic";

	public static final String MODEL_KEY_WEEKDAY_RANDOM_ENTRIES = "week_day_random_entries";

	private static final int DEFAULT_RANDOM_SIZE = 5;

	private static final String RANDOM_WEEKDAY_TOPIC_VIEW = "api/random_weekday_topic";

	/**
	 * 
	 * The method name is part of URL.
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView entries(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		// get parameter
		String weekTopicId = req.getParameter("weeklyTopicId");

		// invalidate parameter
		if (StringUtil.isEmpty(weekTopicId)) {
			// parameter invalid

			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// invalidate conversion
		int id = StringUtil.convertStringToInt(weekTopicId);
		if (id == -1) {
			// id is valid
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// get data and render to pages
		return this.getWeekDayTopicsByWeeklyTopicIdForApi(id);
	}

	/**
	 * 
	 * The method name is part of URL.
	 * 
	 * Generate random weekday topics
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView random(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		// get parameter
		String praramSize = req.getParameter("size");

		// invalidate parameter
		if (StringUtil.isEmpty(praramSize)) {
			// parameter invalid

			return this.getRandomWeekDayTopics(DEFAULT_RANDOM_SIZE);
		}

		// invalidate conversion
		int randomSize = StringUtil.convertStringToInt(praramSize);
		if (randomSize == -1) {
			// size is invalid parameter
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// get data and render to pages
		return this.getRandomWeekDayTopics(randomSize);
	}

	/**
	 * 
	 * The method name is part of URL.
	 * 
	 * 
	 * Get Weekly topics with parameters: year, week
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView by_year_week(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {

		// get parameter
		String paramYear = req.getParameter("year");
		String paramWeek = req.getParameter("week");

		// validate parameter
		if (StringUtil.isEmpty(paramYear) || StringUtil.isEmpty(paramWeek)) {
			// invalid
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// validate conversion
		int year = StringUtil.convertStringToInt(paramYear);
		int week = StringUtil.convertStringToInt(paramWeek);
		if (year == -1 || week == -1) {
			// invalid
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// get data and render to page
		return this.getWeekDayTopicsByWeeklyYearAndWeekForApi(year, week);
	}

	/**
	 * Get weekly Topics by weekly topic id
	 * 
	 * @param id
	 *            weeklyTopicId
	 * @return
	 */
	private ModelAndView getWeekDayTopicsByWeeklyTopicId(int id) {

		// get data from db
		WeekdayTopicApiService weekdayTopicApiService = this.getService();
		List<Map<String, Object>> weekdayTopicsInWeek = weekdayTopicApiService
				.getWeekDayTopicsByWeekTopicId(id);

		// put data to model
		return ModelAndViewUtils.prepareModelAndView(super.getViewPage(),
				MODEL_KEY_WEEKDAY_TOPICS, weekdayTopicsInWeek);
	}

	/**
	 * Get weekly topics by year and week
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	private ModelAndView getWeekDayTopicsByWeeklyYearAndWeek(int year, int week) {

		// get data from db
		WeekdayTopicApiService weekdayTopicApiService = this.getService();
		List<Map<String, Object>> weekdayTopicsInWeek = weekdayTopicApiService
				.getWeekdayTopicsByYearAndWeek(year, week);

		// put data to model
		return ModelAndViewUtils.prepareModelAndView(super.getViewPage(),
				MODEL_KEY_WEEKDAY_TOPICS, weekdayTopicsInWeek);
	}

	/**
	 * 
	 * Helper method to get WeekdayTopicApiService.
	 * 
	 * @return
	 */
	private WeekdayTopicApiService getService() {
		ServiceManager serviceMgr = ServiceManager
				.getInstance(getServletContext());

		return (WeekdayTopicApiService) serviceMgr
				.getService(WeekdayTopicApiService.class.getName());
	}

	/**
	 * Helper method to get WeeklyTopicApiService
	 * 
	 * @return
	 */
	private WeeklyTopicApiService getTopicApiService() {
		ServiceManager serviceMgr = ServiceManager
				.getInstance(getServletContext());

		return (WeeklyTopicApiService) serviceMgr
				.getService(WeeklyTopicApiService.class.getName());
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDateParamValid(String date) {

		if (StringUtil.isEmpty(date)) {
			return false;
		}

		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);

		boolean valid = StringUtil.isStringDigits(year)
				&& StringUtil.isStringDigits(month)
				&& StringUtil.isStringDigits(day)
				&& (date.charAt(4) == date.charAt(7)) && date.charAt(4) == '-';

		return valid;
	}

	// --------------------------------------
	// For API
	// --------------------------------------
	/**
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	private ModelAndView getWeekDayTopicsByWeeklyYearAndWeekForApi(int year,
			int week) {

		// get weely topic
		WeeklyTopicApiService topicApiService = this.getTopicApiService();
		Map<String, Object> weeklyTopic = topicApiService.getWeeklyTopic(year,
				week);
		// get week day topics
		ModelAndView modelAndView = this.getWeekDayTopicsByWeeklyYearAndWeek(
				year, week);

		// add weekly topic and week day topics to model
		modelAndView.addObject(WeeklyTopicApiController.MODEL_KEY_TOPIC,
				weeklyTopic);

		return modelAndView;
	}

	private ModelAndView getWeekDayTopicsByWeeklyTopicIdForApi(int id) {

		// get weekly topic
		WeeklyTopicApiService weeklyTopicApiService = this.getTopicApiService();
		Map<String, Object> weeklyTopic = weeklyTopicApiService
				.getWeeklyTopicById(id);

		// get week day topics
		ModelAndView modelandView = this.getWeekDayTopicsByWeeklyTopicId(id);

		// add weekly topic to model
		modelandView.addObject(WeeklyTopicApiController.MODEL_KEY_TOPIC,
				weeklyTopic);

		return modelandView;
	}

	/**
	 * 
	 * @param size
	 * @return
	 */
	private ModelAndView getRandomWeekDayTopics(int size) {

		// get random ids from database
		List<Map<String, Object>> randomWeeklyTopicIdsMap = this
				.getTopicApiService().getWeeklyTopicRandomIds(size);

		int randomWeeklyTopicIdsSize = randomWeeklyTopicIdsMap.size();
		// validate result
		if (randomWeeklyTopicIdsMap == null || randomWeeklyTopicIdsSize == 0) {
			// error result
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// get the ids
		Integer[] randomWeeklyTopicIds = new Integer[randomWeeklyTopicIdsSize];
		for (int i = 0; i < randomWeeklyTopicIdsMap.size(); i++) {
			randomWeeklyTopicIds[i] = (Integer) randomWeeklyTopicIdsMap.get(i)
					.get("id");
		}

		// log
		if (logger.isDebugEnabled()) {
			logger.debug("random id array from database "
					+ randomWeeklyTopicIds.toString());
		}

		List<Map<String, Object>> randomEntries = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < randomWeeklyTopicIds.length; i++) {
			Map<String, Object> weeklyTopic = this.getTopicApiService()
					.getWeeklyTopicById(randomWeeklyTopicIds[i]);
			List<Map<String, Object>> weekdayTopicsInWeek = this.getService()
					.getWeekDayTopicsByWeekTopicId(randomWeeklyTopicIds[i]);

			Map<String, Object> randomEntry = new HashMap<String, Object>();
			randomEntry.put(WeeklyTopicApiController.MODEL_KEY_TOPIC,
					weeklyTopic);
			randomEntry.put(MODEL_KEY_WEEKDAY_TOPICS, weekdayTopicsInWeek);

			randomEntries.add(randomEntry);
		}

		// put random entries in ModelAndView
		ModelAndView modelAndView = new ModelAndView(RANDOM_WEEKDAY_TOPIC_VIEW);
		modelAndView.addObject(MODEL_KEY_WEEKDAY_RANDOM_ENTRIES, randomEntries);

		// put response message in ModelAndView
		ResponseApiMessage response = new ResponseApiMessage();
		response.setStatus(ResponseApiMessage.STATUS_OK);
		response.setSize(randomEntries.size());

		modelAndView.addObject(ResponseApiMessage.RESPONSE_KEY, response);

		return modelAndView;
	}
}
