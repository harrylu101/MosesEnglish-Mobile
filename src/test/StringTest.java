package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;

import com.harry.domain.DailyTopicWordsAndQuote;
import com.harry.domain.parser.DailyWordsParser;
import com.harry.utils.HttpConnectionUtil;
import com.harry.utils.StringUtil;
import com.harry.web.WeeklyTopicController;

public class StringTest {

	public static void main(String[] args) throws ClientProtocolException,
			IOException {

		for (int i = 0; i < 5; i++) {
			
			if (i == 3) {
				break;
			}
			
			
			System.out.println(i);
		}
	}
}
