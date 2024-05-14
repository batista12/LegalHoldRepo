package com.legal.hold.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserInListUserResponse implements RowMapper<UserInListUserResponse> {
	private String ssoid;
	private String name;
	private String roleName;
	private boolean isEditable = true;

	public boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public UserInListUserResponse(String ssoid, String name, String roleName, boolean isEditable) {
		this.ssoid = ssoid;
		this.name = name;
		this.roleName = roleName;
		this.isEditable = isEditable;
	}

	public UserInListUserResponse() {
	}

	@Override
	public UserInListUserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserInListUserResponse userInListUserResponse = new UserInListUserResponse();
		userInListUserResponse.setSsoid(rs.getString(1));
		userInListUserResponse.setName(rs.getString(2));
		userInListUserResponse.setRoleName(rs.getString(3));
		return userInListUserResponse;
	}
}
