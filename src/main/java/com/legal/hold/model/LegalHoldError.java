package com.legal.hold.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class LegalHoldError {

	private int errorCode;
	private HttpStatus status;
	private LocalDateTime timeStamp;
	private String message;
	private String debugMsg;

	public LegalHoldError() {
		timeStamp = LocalDateTime.now();
	}

	public LegalHoldError(HttpStatus status, String message, String debugMsg, int errorCode) {
		this();
		this.errorCode = errorCode;
		this.status = status;
		this.message = message;
		this.debugMsg = debugMsg;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMsg() {
		return debugMsg;
	}

	public void setDebugMsg(String debugMsg) {
		this.debugMsg = debugMsg;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
