package com.legal.hold.entity;

import java.util.List;

public class ListUserResponse {
	private List<UserInListUserResponse> listUsers;

	public List<UserInListUserResponse> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<UserInListUserResponse> listUsers) {
		this.listUsers = listUsers;
	}

	public ListUserResponse(List<UserInListUserResponse> listUsers) {
		this.listUsers = listUsers;
	}

}
