package com.harry.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Priority;
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

//		String siteUrlPrefix = "http://mosesenglish.com/theme.asp?year=2011&weekNum=41";
//
//		DataFetcher fetcher = new DataFetcher(getServletContext());
//
//		fetcher.processsBusinessLogic(siteUrlPrefix);

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

}
