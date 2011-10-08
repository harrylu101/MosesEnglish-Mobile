package com.harry.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * BaseController that lets you group a couple of related request together.
 * 
 * @author harry
 * 
 */
public class CommonController extends MultiActionController {
	protected static final Logger logger = Logger
			.getLogger(CommonController.class);

	public static final String CREATE = "create"; // create
	public static final String REMOVE = "remove"; // remove
	public static final String UPDATE = "update"; // update
	public static final String VIEW = "view"; // view/read
	public static final String ADD = "add"; // add

	private String viewPage;

	// --------------------------
	// getters & setters
	// --------------------------

	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}

}
