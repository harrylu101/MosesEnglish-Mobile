package com.harry.domain.api;

public class ResponseApiMessage {

	public static final String RESPONSE_KEY = "repsonse";

	public static final String STATUS_OK = "OK";
	public static final String STATUS_ERROR = "ERROR";
	public static final String ERROR_NOT_RESULT_FOUND = "NO RESULT FOUND";
	public static final String ERROR_INVALID_PARAMETER = "INVALID PARAMETER";

	private String status;
	private String message;
	private int size;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String messge) {
		this.message = messge;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ResponseApiMessage [status=" + status + ", messge=" + message
				+ ", size=" + size + "]";
	}

}
