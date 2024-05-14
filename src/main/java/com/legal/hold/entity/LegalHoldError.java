package com.legal.hold.entity;

public class LegalHoldError {

	private int errorCode;
//	private HttpStatus status;
//	private LocalDateTime timeStamp;
	private String message;
	private boolean success = true;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	// private String debugMsg;
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LegalHoldError(int errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	public LegalHoldError(int errorCode, String message, boolean success) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.success = success;
	}
}
