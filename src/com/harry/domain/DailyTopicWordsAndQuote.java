package com.harry.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.harry.utils.StringUtil;

public class DailyTopicWordsAndQuote {

	public static final String WORDS_KEY = "words";
	public static final String DIFINITION_KEY = "definition";

	private String imageUrl;
	private List<DailyWord> words;

	private String englishQuote;
	private String ChineseQuote;

	public DailyTopicWordsAndQuote() {
		words = new ArrayList<DailyWord>();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String image) {
		this.imageUrl = image;
	}

	public List<DailyWord> getWords() {
		return words;
	}

	public void setWords(List<DailyWord> words) {
		this.words = words;
	}

	public String getEnglishQuote() {
		return englishQuote;
	}

	public void setEnglishQuote(String englishQuote) {
		this.englishQuote = englishQuote;
	}

	public String getChineseQuote() {
		return ChineseQuote;
	}

	public void setChineseQuote(String chineseQuote) {
		ChineseQuote = chineseQuote;
	}

	/**
	 * Add a word to the word list. if the word to be added does exist in the
	 * list, then the operation is abandoned.
	 * 
	 * @param word
	 */
	public void add(DailyWord word) {
		this.words.add(word);
	}

	@Override
	public String toString() {
		return "WordsToday [image=" + imageUrl + ", words size :"
				+ this.words.toString() + "]";
	}

}
