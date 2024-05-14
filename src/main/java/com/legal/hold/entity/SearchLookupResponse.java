package com.legal.hold.entity;

import java.util.List;

public class SearchLookupResponse {

	private List<LegalHoldModel> data;
	private String message;
	private String error;

	public SearchLookupResponse(List<LegalHoldModel> data, String message, String error) {
		this.data = data;
		this.message = message;
		this.error = error;
	}

	public List<LegalHoldModel> getdata() {
		return data;
	}

	public void setdata(List<LegalHoldModel> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
