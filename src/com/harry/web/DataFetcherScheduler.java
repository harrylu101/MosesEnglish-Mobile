package com.harry.web;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 
 * Use a timer task to get data from mosesenglish.com
 * 
 * @author harry
 * 
 */
public class DataFetcherScheduler implements ServletContextListener {

	static Logger logger = Logger.getLogger(DataFetcherScheduler.class);

	private static final String SCHEDULER_KEY = "scheduler";

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("scheduler stopped");
		}

		ServletContext context = event.getServletContext();

		// cancel scheduler
		Timer scheduler = (Timer) context.getAttribute(SCHEDULER_KEY);
		if (scheduler != null) {
			scheduler.cancel();
		}

		// remove scheduler from context

		context.removeAttribute(SCHEDULER_KEY);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("scheduler stated");
		}

		Timer schuler = new Timer();
		DataFetherTask scheduleTask = new DataFetherTask(new DataFetcher(
				event.getServletContext()));

		Calendar cal = Calendar.getInstance();
		Date startedTime = cal.getTime();

		// do the task every 2 hours
		schuler.scheduleAtFixedRate(scheduleTask, startedTime,
				1000 * 60 * 60 * 2);

		ServletContext context = event.getServletContext();
		context.setAttribute(SCHEDULER_KEY, schuler);
	}

}
