package com.legal.hold.entity;

public class StatusResponse {
	private String sso;
	private String hold;

	public String getSso() {
		return sso;
	}

	public void setSso(String sso) {
		this.sso = sso;
	}

	public String getHold() {
		return hold;
	}

	public void setHold(String hold) {
		this.hold = hold;
	}

	public StatusResponse(String sso, String hold) {
		this.sso = sso;
		this.hold = hold;
	}
}
