package com.harry.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.harry.domain.DailyTopicWordsAndQuote;
import com.harry.domain.parser.DailyWordsParser;
import com.harry.utils.HttpConnectionUtil;
import com.harry.utils.StringUtil;

public class MosesEnglishController implements Controller {

	private static final String URL_MOSESENGLISH = "http://mosesenglish.com";
	private static final String URL_MOSESENGLISH_TODAY = "http://mosesenglish.com/today.asp";

	private String viewPage;

	@Override
	public ModelAndView handleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {

		
		Map<String, String> model = new HashMap<String, String>();
		
		
		System.out.println("hello");
		
//
//		// get HTML String from web
//		InputStream is = HttpConnectionUtil
//				.openHttpConnection(URL_MOSESENGLISH_TODAY);
//
//		String htmlString = StringUtil.convertStreamToString(is);
//		// populate data into java bean
//		DailyTopicWordsAndQuote words = new DailyTopicWordsAndQuote();
//		DailyWordsParser parser = new DailyWordsParser(htmlString, words);
//		parser.parse();

		// read data from java bean
		return new ModelAndView(this.getViewPage(), "model", model);
	}

	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}

}
