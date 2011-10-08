package com.harry.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeekDayCalculator {

	public static void main(String[] args) {
		System.out.println(getWeekDay(2011, 9, 19));
		System.out.println(getWeekOfYear(2011, 9, 19));
	}

	public static String getWeekDay(int year, int month, int dayOfMonth) {
		final String[] WEEK_DAY_CHINESE = new String[] { "星期日", "星期一", "星期二",
				"星期三", "星期四", "星期五", "星期六" };
		Calendar cal = new GregorianCalendar(year, month - 1, dayOfMonth);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return WEEK_DAY_CHINESE[dayOfWeek - 1];
	}

	public static int getWeekOfYear(int year, int month, int dayOfMonth) {
		Calendar cal = new GregorianCalendar(year, month - 1, dayOfMonth);
		int dayOfWeek = cal.get(Calendar.WEEK_OF_YEAR) - 1;
		return dayOfWeek;
	}
}
