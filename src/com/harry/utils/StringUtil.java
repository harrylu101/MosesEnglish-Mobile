package com.harry.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class StringUtil {

	public static String convertStreamToString(InputStream is)
			throws IOException {

		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public static boolean isEmpty(String str) {

		return str == null || str.length() == 0;
	}

	public static String escapeHTMLTags(String htmlSourceString) {
		return htmlSourceString.replaceAll("\\<.*?\\>", "");
	}

	/**
	 * Convert the String number to Integer.
	 * 
	 * @param num
	 * @return number if success, -1 if not
	 */
	public static int convertStringToInt(String num) {

		for (int i = 0; i < num.length(); i++) {
			if (Character.isDigit(num.charAt(i))) {
				// do nothing
			} else {
				// string is not a number
				return -1;
			}
		}

		return Integer.parseInt(num);
	}

	public static boolean isStringDigits(String digitsString) {
		if (isEmpty(digitsString)) {
			return false;
		}
		for (int i = 0; i < digitsString.length(); i++) {
			if (!Character.isDigit(digitsString.charAt(i))) {
				// not a digit!
				return false;
			}
		}

		return true;
	}

	private StringUtil() {

	}
}
