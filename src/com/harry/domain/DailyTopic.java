package com.harry.domain;

public class DailyTopic {
	private String word;
	private String definition;
	private String issueDate;
	private String topicLink;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getTopicLink() {
		return topicLink;
	}

	public void setTopicLink(String topicLink) {
		this.topicLink = topicLink;
	}

	
	@Override
	public String toString() {
		return "DailyTopic [word=" + word + ", definition=" + definition
				+ ", issueDate=" + issueDate + ", topicLink=" + topicLink + "]";
	}

}
