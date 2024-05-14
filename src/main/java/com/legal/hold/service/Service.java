package com.legal.hold.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legal.hold.entity.AppAlert;
import com.legal.hold.entity.LegalAssistantsModel;
import com.legal.hold.entity.LegalHoldModel;
import com.legal.hold.entity.UserDetail;
import com.legal.hold.entity.UserInListUserResponse;

@Component
public interface Service {
	public String getSso(String sso) throws SQLException;

	public List<LegalAssistantsModel> getAssistants(String sso) throws SQLException;

	public List<LegalHoldModel> getLegalHoldDetails(List<String> sso) throws SQLException;

	public boolean addUser(UserDetail user);

	public boolean removeUser(String sso, String loggedinSso);

	public List<UserInListUserResponse> listUser();

	public String getRoleName(String sso) throws SQLException;

	public int getRoleId(String sso) throws SQLException;
	
	public AppAlert getAlertDetails(String alertType);
}
