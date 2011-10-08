package com.harry.utils;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.harry.domain.api.ResponseApiMessage;

public class ModelAndViewUtils {

	private static final Logger logger = Logger
			.getLogger(ModelAndViewUtils.class);

	/**
	 * 
	 * @param model
	 * @param viewPage
	 * @return
	 */
	public static ModelAndView prepareModelAndView(final String viewName,
			final String modelName, final List<Map<String, Object>> model) {
		ResponseApiMessage response = new ResponseApiMessage();

		if (model == null) {
			response.setStatus(ResponseApiMessage.STATUS_ERROR);
			response.setMessage(ResponseApiMessage.ERROR_NOT_RESULT_FOUND);
		} else {
			response.setStatus(ResponseApiMessage.STATUS_OK);
			response.setSize(model.size());
		}

		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject(ResponseApiMessage.RESPONSE_KEY, response);
		modelAndView.addObject(modelName, model);

		if (logger.isDebugEnabled()) {
			logger.debug("prepare model and view : " + modelAndView.toString());
		}

		return modelAndView;
	}

	/**
	 * 
	 * @param viewPage
	 * @return
	 */
	public static ModelAndView inValidParameterResponse(String viewPage) {
		ModelAndView modelAndView = new ModelAndView(viewPage);
		ResponseApiMessage response = new ResponseApiMessage();
		response.setStatus(ResponseApiMessage.STATUS_ERROR);
		response.setMessage(ResponseApiMessage.ERROR_INVALID_PARAMETER);
		modelAndView.addObject(ResponseApiMessage.RESPONSE_KEY, response);
		return modelAndView;
	}

	private ModelAndViewUtils() {

	}
}
