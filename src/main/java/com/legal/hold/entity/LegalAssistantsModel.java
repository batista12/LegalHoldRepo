package com.legal.hold.entity;

public class LegalAssistantsModel {
	private String email;
	private String name;

	public LegalAssistantsModel(String name, String email) {
		super();
		this.email = email;
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
