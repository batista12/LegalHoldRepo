package com.legal.hold.entity;

public class UserDetail {
	private String ssoid;
	private String name;
	private String business;
	private String email;
	private int roleId;
	private String loggedinSso;

	public UserDetail(String ssoid, String name, String email, String business, int roleId, String loggedinSso) {
		this.ssoid = ssoid;
		this.name = name;
		this.business = business;
		this.email = email;
		this.roleId = roleId;
		this.loggedinSso = loggedinSso;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

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

	public String getLoggedinSso() {
		return loggedinSso;
	}

	public void setLoggedinSso(String loggedinSso) {
		this.loggedinSso = loggedinSso;
	}
}
