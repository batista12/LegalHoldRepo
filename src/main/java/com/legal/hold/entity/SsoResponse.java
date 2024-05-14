package com.legal.hold.entity;

public class SsoResponse {

	private String ssoid;
	private String name;
	private String business;
	private String email;

	public String getSsoid() {
		return ssoid;
	}

	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SsoResponse(String ssoid, String name, String business, String email) {
		this.ssoid = ssoid;
		this.name = name;
		this.business = business;
		this.email = email;
	}
}
