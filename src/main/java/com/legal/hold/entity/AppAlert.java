package com.legal.hold.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AppAlert implements RowMapper<AppAlert> {

	String alertTitle;
	String alertMessage;
	String isActive;

	public AppAlert() {
		super();
	}

	public String getAlertTitle() {
		return alertTitle;
	}

	public void setAlertTitle(String alertTitle) {
		this.alertTitle = alertTitle;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public AppAlert mapRow(ResultSet rs, int rowNum) throws SQLException {
		AppAlert appAlert = new AppAlert();
		appAlert.setAlertTitle(rs.getString(1));
		appAlert.setAlertMessage(rs.getString(2));
		appAlert.setIsActive(rs.getString(3));
		return appAlert;
	}
}
