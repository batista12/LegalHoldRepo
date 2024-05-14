package com.legal.hold.dao;

import org.springframework.stereotype.Repository;

import com.legal.hold.entity.AppAlert;
import com.legal.hold.entity.LegalAssistantsModel;
import com.legal.hold.entity.LegalHoldModel;
import com.legal.hold.entity.UserDetail;
import com.legal.hold.entity.UserInListUserResponse;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface WebDao {

	@SuppressWarnings("rawtypes")
	public List getSso(String sso) throws SQLException;

	public List<LegalAssistantsModel> getAssistants(String sso) throws SQLException;

	public List<LegalHoldModel> getLegalHoldDetails(List<String> sso) throws SQLException;

	public boolean addUser(UserDetail user);

	public boolean removeUser(String sso, String loggedinSso);

	public List<UserInListUserResponse> listUser();

	@SuppressWarnings("rawtypes")
	public List getRoleName(String sso) throws SQLException;

	@SuppressWarnings("rawtypes")
	public List getRoleId(String sso) throws SQLException;
	
	public AppAlert getAlertDetails(String alertType);
}
