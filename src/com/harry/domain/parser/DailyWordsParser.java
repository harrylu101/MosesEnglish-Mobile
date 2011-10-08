package com.harry.domain.parser;

import com.harry.domain.DailyTopicWordsAndQuote;
import com.harry.domain.DailyWord;
import com.harry.utils.StringUtil;

public class DailyWordsParser {

	private static final String URL_MOSESENGLISH = "http://mosesenglish.com/";

	// constants for extracting out topic image
	private static final String CONTENT_IMAGE_START = "<div class=\"topicImg\">";
	private static final String CONTENT_IMAGE_END = "</div>";
	private static final String CONTENT_IMAGE__START_SRC = "src=\"";
	private static final String CONTENT_IMAGE_END_SRC = "\"";

	// constants for extracting out words
	private static final String CONTENT_WORD_START = "<span style=\"color: #444444;font-size:16px;\"><strong>";
	private static final String CONTENT_WORD_END = "</strong></span>";

	// constants for extracting out definitions
	private static final String PARAGRAPH_START = "<p";
	private static final String PARAGRAPH_END = "</p>";

	// constants for extracting out quotes
	private static final String CONTENT_QUOTE_START = "<strong>今日名言</strong> <br>";
	private static final String CONTENT_QUOTE_BREAK_LINE = "<br>";
	private static final String CONTENT_QUOTE_DIV = "</div>";

	private StringBuilder htmlSourceString;
	private DailyTopicWordsAndQuote wordsToday;

	public DailyWordsParser(String htmlSourceString,
			DailyTopicWordsAndQuote wordsToday) {
		super();
		this.htmlSourceString = new StringBuilder(htmlSourceString);
		this.wordsToday = wordsToday;
	}

	/**
	 * parse the html string and set the data to java bean.
	 */
	public void parse() {
		this.parseTopicImage();
		this.parseWords();
		this.parseQuote();

	}

	/**
	 * extract topic image from html string
	 * 
	 * @return
	 */
	private void parseTopicImage() {

		// find string like this "<img src="..." alt="..." />"
		final int fromIndex = htmlSourceString.indexOf(CONTENT_IMAGE_START)
				+ CONTENT_IMAGE_START.length();
		final int toIndex = htmlSourceString.indexOf(CONTENT_IMAGE_END,
				fromIndex);

		if (fromIndex == -1 || toIndex == -1) {
			// did not find the string
			return;
		}

		// extract the string above
		StringBuilder imageStr = new StringBuilder(htmlSourceString.substring(
				fromIndex, toIndex));

		// mark where the " src=" " starts
		final int imageSrcMarkStart = imageStr
				.indexOf(CONTENT_IMAGE__START_SRC)
				+ CONTENT_IMAGE__START_SRC.length();

		// mark where the src="" ends
		final int ImageSrcMarkEnd = imageStr.indexOf(CONTENT_IMAGE_END_SRC,
				imageSrcMarkStart);

		// extract out the image url
		String imageURL = imageStr
				.substring(imageSrcMarkStart, ImageSrcMarkEnd);

		// set to java bean
		this.wordsToday.setImageUrl(URL_MOSESENGLISH + imageURL);

		System.out.println(this.wordsToday.getImageUrl());
	}

	/**
	 * extract words form html string
	 * 
	 * @return
	 */
	private void parseWords() {
		int fromIndex = 0;
		int toIndex = 0;

		// extract out words
		while (true) {
			fromIndex = htmlSourceString.indexOf(CONTENT_WORD_START, toIndex);
			toIndex = htmlSourceString.indexOf(CONTENT_WORD_END, fromIndex
					+ CONTENT_WORD_START.length());

			// System.out.println(fromIndex);
			// System.out.println(toIndex);

			if (fromIndex == -1 || toIndex == -1) {
				break;
			}

			String word = htmlSourceString.substring(fromIndex
					+ CONTENT_WORD_START.length(), toIndex);
			System.out.println(word);

			int definitionFromIndex = toIndex + CONTENT_WORD_END.length();
			int definitionToIndex = htmlSourceString.indexOf(PARAGRAPH_END,
					definitionFromIndex);

			String definition = htmlSourceString.substring(definitionFromIndex,
					definitionToIndex);
			// convert &nbsp; into space
			definition = definition.replace("&nbsp;", " ");
			System.out.println(definition);

			DailyWord dailyWord = new DailyWord();
			dailyWord.setWord(word);
			dailyWord.setDefinition(definition);

			// put the word in the word list
			this.wordsToday.add(dailyWord);

		}

	}

	private void parseQuote() {

		// find where the quote starts
		int quoteFromIndex = htmlSourceString.indexOf(CONTENT_QUOTE_START);

		// find out where English quote starts
		int englishQuoteFromIndex = quoteFromIndex
				+ CONTENT_QUOTE_START.length();
		// extract out English quote
		String englishQuote = htmlSourceString.substring(
				englishQuoteFromIndex,
				htmlSourceString.indexOf(CONTENT_QUOTE_BREAK_LINE,
						englishQuoteFromIndex)).trim();

		// extract out Chinese quote
		int chineseQuoteFromIndex = englishQuoteFromIndex
				+ englishQuote.length()+CONTENT_QUOTE_BREAK_LINE.length();
		int chineseQuoteToIndex = htmlSourceString.indexOf(CONTENT_QUOTE_DIV,
				chineseQuoteFromIndex);

		String chineseQuote = htmlSourceString.substring(chineseQuoteFromIndex
				+ CONTENT_QUOTE_DIV.length(), chineseQuoteToIndex);
		chineseQuote = StringUtil.escapeHTMLTags(chineseQuote);
		chineseQuote = chineseQuote.trim();

		this.wordsToday.setEnglishQuote(englishQuote);
		this.wordsToday.setChineseQuote(chineseQuote);

		System.out.println(englishQuote);
		System.out.println(chineseQuote);

	}
}
