package com.harry.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.harry.domain.DailyTopicWordsAndQuote;
import com.harry.domain.WeeklyTopic;

/**
 * 
 * A thread to get data from web.
 * 
 * @author harry
 * 
 */
public class DataFetherTask extends TimerTask {

	static Logger logger = Logger.getLogger(DataFetcher.class);

	private DataFetcher dataFetcher;

	public DataFetherTask(DataFetcher dataFetcher) {
		super();
		this.dataFetcher = dataFetcher;
	}

	@Override
	public void run() {

		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int week = now.get(Calendar.WEEK_OF_YEAR) - 1;
		String url = this.constuctUrl(year, week);
		
		try {
			this.dataFetcher.processsBusinessLogic(url);
			WeeklyTopic wt = dataFetcher.getWeeklyTopic();
			DailyTopicWordsAndQuote dtwq = dataFetcher.getWordsAndQuote();
			// log it
			if (logger.isDebugEnabled()) {
				logger.debug(wt + "\n" + dtwq);
			}
		} catch (ClientProtocolException e) {
			// do nothing
		} catch (IOException e) {
			// do nothing
		}

	}

	private String constuctUrl(int year, int week) {
		return "http://mosesenglish.com/theme.asp?year=" + year + "&weekNum="
				+ week;
	}

}
