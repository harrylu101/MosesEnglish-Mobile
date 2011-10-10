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

		Calendar now = Calendar.getInstance();

		now.set(2010, 05, 28);

		System.out.println(now.get(Calendar.YEAR));
		System.out.println(now.get(Calendar.MONTH));
		System.out.println(now.get(Calendar.WEEK_OF_YEAR)-1);
	}
}
