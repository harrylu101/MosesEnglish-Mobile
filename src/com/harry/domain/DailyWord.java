package com.harry.domain;

public class DailyWord {
	private String word;
	private String definition;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word.trim();
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition.trim();
	}

	@Override
	public String toString() {
		return "DailyWord [word=" + word + ", definition=" + definition + "]";
	}

}
