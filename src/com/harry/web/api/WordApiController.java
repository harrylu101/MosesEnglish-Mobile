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
import com.harry.service.api.WordApiService;
import com.harry.utils.ModelAndViewUtils;
import com.harry.utils.StringUtil;

public class WordApiController extends CommonController {

	public static final String MODEL_KEY_WORDS = "words";

	private static final int DEFAULT_RAMDOM_SIZE = 5;

	private static final String VIEW_PAGE_RANDOM = "api/random_word";

	private static final String MODEL_KEY_WORD_RANDOM_ENTRIES = "random_words";

	/**
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
		String paramId = req.getParameter("weekdayTopicId");

		// validate parameter
		if (StringUtil.isEmpty(paramId)) {
			// invalid
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		int id = StringUtil.convertStringToInt(paramId);
		if (id == -1) {
			// invalid parameter
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		return this.getWordsByTopicIdForApi(id);
	}

	/**
	 * 
	 * The method name is part of URL.
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView random(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		// get parameter
		String size = req.getParameter("size");

		if (StringUtil.isEmpty(size)) {
			// if no size parameter, return default
			return this.getRandomWordsForApi(DEFAULT_RAMDOM_SIZE);
		}

		// convert parameter from string to integer
		int limit = StringUtil.convertStringToInt(size);
		if (limit == -1) {
			// invalid parameter
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		return this.getRandomWordsForApi(limit);
	}

	/**
	 * 
	 * Controller helper. To get word data from database
	 * 
	 * @param topicId
	 * @return
	 */
	private ModelAndView getWordsByTopicId(int topicId) {

		WordApiService wordApiService = this.getService();

		List<Map<String, Object>> wordsByTopic = wordApiService
				.getWordsByWeekdayTopicId(topicId);

		return ModelAndViewUtils.prepareModelAndView(super.getViewPage(),
				MODEL_KEY_WORDS, wordsByTopic);

	}

	// --------------------------------------
	// Helper methods
	// --------------------------------------

	/**
	 * return WordApiService
	 * 
	 * @return
	 */
	private WordApiService getService() {
		ServiceManager serviceMgr = ServiceManager
				.getInstance(getServletContext());

		return (WordApiService) serviceMgr.getService(WordApiService.class
				.getName());
	}

	/**
	 * return WeekdayTopicApiService
	 * 
	 * @return
	 */
	private WeekdayTopicApiService getWeekdayTopicApiService() {
		ServiceManager serviceMgr = ServiceManager
				.getInstance(getServletContext());

		return (WeekdayTopicApiService) serviceMgr
				.getService(WeekdayTopicApiService.class.getName());
	}

	// --------------------------------------
	// For API
	// --------------------------------------

	private ModelAndView getWordsByTopicIdForApi(int topicId) {
		// get week day topic
		WeekdayTopicApiService weekdayTopicApiSerive = this
				.getWeekdayTopicApiService();
		Map<String, Object> weekdayTopic = weekdayTopicApiSerive
				.getWeekdayTopicById(topicId);

		// get words
		ModelAndView modelandView = this.getWordsByTopicId(topicId);
		modelandView
				.addObject(WeekdayTopicApiController.MODEL_KEY_WEEKDAY_TOPIC,
						weekdayTopic);

		return modelandView;
	}

	private ModelAndView getRandomWordsForApi(int limit) {

		// get random ids from database
		List<Map<String, Object>> randomWeekdayTopicIdsMap = this
				.getWeekdayTopicApiService().getWeekDayTopicRandomIds(limit);

		int randomWeekdayTopicIdsSize = randomWeekdayTopicIdsMap.size();
		// validate result
		if (randomWeekdayTopicIdsMap == null || randomWeekdayTopicIdsSize == 0) {
			// error result
			return ModelAndViewUtils.inValidParameterResponse(super
					.getViewPage());
		}

		// get the ids
		Integer[] randomWeekdayTopicIds = new Integer[randomWeekdayTopicIdsSize];
		for (int i = 0; i < randomWeekdayTopicIdsMap.size(); i++) {
			randomWeekdayTopicIds[i] = (Integer) randomWeekdayTopicIdsMap
					.get(i).get("id");
		}

		// log
		if (logger.isDebugEnabled()) {
			logger.debug("random id array from database "
					+ randomWeekdayTopicIds.toString());
		}

		List<Map<String, Object>> randomEntries = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < randomWeekdayTopicIds.length; i++) {
			Map<String, Object> weekdayTopic = this.getWeekdayTopicApiService()
					.getWeekdayTopicById(randomWeekdayTopicIds[i]);
			List<Map<String, Object>> wordsInWeekdayTopic = this.getService()
					.getWordsByWeekdayTopicId(randomWeekdayTopicIds[i]);

			Map<String, Object> randomEntry = new HashMap<String, Object>();
			randomEntry.put(WeekdayTopicApiController.MODEL_KEY_WEEKDAY_TOPIC,
					weekdayTopic);
			randomEntry.put(MODEL_KEY_WORDS, wordsInWeekdayTopic);

			randomEntries.add(randomEntry);
		}

		// put random entries in ModelAndView
		ModelAndView modelAndView = new ModelAndView(VIEW_PAGE_RANDOM);
		modelAndView.addObject(MODEL_KEY_WORD_RANDOM_ENTRIES, randomEntries);

		// put response message in ModelAndView
		ResponseApiMessage response = new ResponseApiMessage();
		response.setStatus(ResponseApiMessage.STATUS_OK);
		response.setSize(randomEntries.size());
		modelAndView.addObject(ResponseApiMessage.RESPONSE_KEY, response);

		return modelAndView;

	}
}
