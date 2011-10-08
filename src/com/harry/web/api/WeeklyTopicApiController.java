package com.harry.web.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.harry.core.CommonController;
import com.harry.core.ServiceManager;
import com.harry.service.api.WeeklyTopicApiService;
import com.harry.utils.ModelAndViewUtils;
import com.harry.utils.StringUtil;

/**
 * 
 * A controller that process the API request. It retrieve data from database and
 * response to API request.
 * 
 * @author harry
 * 
 */

public class WeeklyTopicApiController extends CommonController {

	/*
	 * model key for a collection of topics
	 */
	public static final String MODEL_KEY_TOPICS = "topics";
	/*
	 * model key for an item of topic
	 */
	public static final String MODEL_KEY_TOPIC = "topic";
	/*
	 * Max size of the topics the system allows users to request. This is just
	 * to prevent users to request lots of data at one time
	 */
	private static final int MAX_SIZE = 50;
	/*
	 * Set default size when users request for latest weekly topic
	 */
	private static final int LASTEST_DEFAULT_SIZE = 10;

	/**
	 * The method name is part of URL.
	 * 
	 * It retrieve any weekly topic. One time it can retrieve only one
	 * item/entry
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView any(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		// get parameters
		String paramFromYear = req.getParameter("year");
		String paramFromWeek = req.getParameter("week");

		// validate parameters
		if (StringUtil.isEmpty(paramFromYear)
				|| StringUtil.isEmpty(paramFromWeek)) {
			// invalid parameter
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// convert parameters to integer
		int year = StringUtil.convertStringToInt(paramFromYear);
		int week = StringUtil.convertStringToInt(paramFromWeek);

		// validate converted parameters
		if (year == -1 || week == -1) {
			// invalid parameter
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		return this.getWeeklyTopic(year, week);

	}

	/**
	 * The method name is part of URL.
	 * 
	 * Get a collection of weekly topic entries before a certain year and week
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView before(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		// get parameters
		String paramFromYear = req.getParameter("year");
		String paramFromWeek = req.getParameter("week");
		String paramSize = req.getParameter("size");

		// validate parameters
		if (StringUtil.isEmpty(paramFromYear)
				|| StringUtil.isEmpty(paramFromWeek)
				|| StringUtil.isEmpty(paramSize)) {

			// invalid parameter
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// convert parameter strings to ints
		int year = StringUtil.convertStringToInt(paramFromYear);
		int week = StringUtil.convertStringToInt(paramFromWeek);
		int size = StringUtil.convertStringToInt(paramSize);

		// validated converted parameters
		if (year == -1 || week == -1 || size == -1) {
			// invalid
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		return this.getWeeklyTopicsBefore(year, week, Math.min(size, MAX_SIZE));

	}

	/**
	 * The method name is part of URL.
	 * 
	 * 
	 * Get a collection of weekly topic entries before a certain year and week
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView after(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		// get parameter
		String paramToYear = req.getParameter("year");
		String paramToWeek = req.getParameter("week");
		String paramSize = req.getParameter("size");

		// validate parameters
		if (StringUtil.isEmpty(paramToYear) || StringUtil.isEmpty(paramToWeek)
				|| StringUtil.isEmpty(paramSize)) {
			// invalid parameter
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// convert string to integer
		int year = StringUtil.convertStringToInt(paramToYear);
		int week = StringUtil.convertStringToInt(paramToWeek);
		int size = StringUtil.convertStringToInt(paramSize);

		// validate converted string
		if (year == -1 || week == -1 || size == -1) {
			// invalid
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		return this.getWeeklyTopicsAfter(year, week, Math.min(size, MAX_SIZE));

	}

	/**
	 * The method name is part of URL.
	 * 
	 * 
	 * Get a collection of latest weekly topics entries
	 * 
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView latest(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		// get parameter
		String praramSize = req.getParameter("size");

		// validate parameter
		if (StringUtil.isEmpty(praramSize)) {
			// parameter invalid
			return this.getLastestWeekDayTopics(LASTEST_DEFAULT_SIZE);
		}

		// validate conversion
		int lastestSize = StringUtil.convertStringToInt(praramSize);
		if (lastestSize == -1) {
			// size is invalid parameter
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		return this.getLastestWeekDayTopics(lastestSize);
	}

	/**
	 * 
	 * Get latest weekly topics with specified size
	 * 
	 * @param lastestDefaultSize
	 * @return
	 */
	private ModelAndView getLastestWeekDayTopics(int lastestDefaultSize) {
		WeeklyTopicApiService weeklyApiService = this.getService();

		List<Map<String, Object>> lastedWeeklyTopics = weeklyApiService
				.getWeeklyTopicLatest(lastestDefaultSize);

		return ModelAndViewUtils.prepareModelAndView(super.getViewPage(),
				MODEL_KEY_TOPICS, lastedWeeklyTopics);
	}

	/**
	 * 
	 * Get weekly topics data from database.
	 * 
	 * (Takes care of getting data from database and put data into model).
	 * 
	 * 
	 * @param year
	 *            from which year
	 * @param week
	 *            from which week
	 * @param size
	 *            size of data you want
	 * @return
	 */
	private ModelAndView getWeeklyTopicsAfter(int year, int week, int size) {

		WeeklyTopicApiService topicApiService = this.getService();
		List<Map<String, Object>> weeklyTopics = topicApiService
				.getWeeklyTopicsAfter(year, week, size);

		return ModelAndViewUtils.prepareModelAndView(super.getViewPage(),
				MODEL_KEY_TOPICS, weeklyTopics);
	}

	/**
	 * 
	 * Get weekly topics data from database.
	 * 
	 * (Takes care of getting data from database and put data into model).
	 * 
	 * 
	 * @param year
	 *            from which year
	 * @param week
	 *            from which week
	 * @param size
	 *            size of data you want
	 * @return
	 */

	private ModelAndView getWeeklyTopicsBefore(int year, int week, int size) {

		WeeklyTopicApiService topicApiService = this.getService();
		List<Map<String, Object>> weeklyTopics = topicApiService
				.getWeeklyTopicsBefore(year, week, size);
		return ModelAndViewUtils.prepareModelAndView(super.getViewPage(),
				MODEL_KEY_TOPICS, weeklyTopics);
	}

	/**
	 * 
	 * Get weekly topic data from database.
	 * 
	 * (Takes care of getting data from database and put data into model).
	 * 
	 * 
	 * @param year
	 *            from which year
	 * @param week
	 *            from which week
	 * 
	 * @return
	 */

	private ModelAndView getWeeklyTopic(int year, int week) {

		WeeklyTopicApiService topicApiService = this.getService();
		Map<String, Object> weeklyTopic = topicApiService.getWeeklyTopic(year,
				week);

		return new ModelAndView(super.getViewPage(), MODEL_KEY_TOPIC,
				weeklyTopic);
	}

	/**
	 * 
	 * return WeeklyTopicApiService
	 * 
	 * @return
	 */
	private WeeklyTopicApiService getService() {
		// get service manager
		ServiceManager serviceMgr = ServiceManager
				.getInstance(getServletContext());

		// get service
		WeeklyTopicApiService topicApiService = (WeeklyTopicApiService) serviceMgr
				.getService(WeeklyTopicApiService.class.getName());

		return topicApiService;
	}

}
