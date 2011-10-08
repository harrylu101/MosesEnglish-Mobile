package com.harry.domain;

import java.util.ArrayList;
import java.util.List;

public class WeeklyTopic {

	private String topic;
	private int year;
	private int week;
	private String topicDescription;

	private List<DailyTopic> dailyTopics;

	public WeeklyTopic() {
		this.dailyTopics = new ArrayList<DailyTopic>();
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getTopicDescription() {
		return topicDescription;
	}

	public void setTopicDescription(String topicDescription) {
		this.topicDescription = topicDescription;
	}

	public List<DailyTopic> getDailyTopics() {
		return dailyTopics;
	}

	public void setDailyTopics(List<DailyTopic> dailyTopics) {
		this.dailyTopics = dailyTopics;
	}

	public void addDailyTopic(DailyTopic topic) {
		this.dailyTopics.add(topic);
	}

	@Override
	public String toString() {
		return "WeeklyTopic [topic=" + topic + ", year=" + year + ", week="
				+ week + ", topicDescription=" + topicDescription
				+ ", dailyTopics=" + dailyTopics.toString() + "]";
	}

	
	
}
