package com.harry.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpConnectionUtil {


	private static final int TIMEOUT_CONNECTION = 5000;
	private static final int TIMEOUT_SOCKET = 15000;

	public static InputStream openHttpConnection(String url)
			throws ClientProtocolException, IOException {

		if (StringUtil.isEmpty(url)) {
			return null;
		}

		InputStream in = null;
		HttpGet httpGet = new HttpGet(url);

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = TIMEOUT_CONNECTION;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = TIMEOUT_SOCKET;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpResponse response = httpClient.execute(httpGet);

		if (response == null) {
			return null;
		}

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			in = response.getEntity().getContent();
		}

		return in;
	}

	private HttpConnectionUtil() {

	}

}
