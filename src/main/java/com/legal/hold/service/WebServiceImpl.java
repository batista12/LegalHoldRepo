package com.legal.hold.service;

import static java.lang.String.valueOf;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.legal.hold.dao.WebDao;
import com.legal.hold.entity.AppAlert;
import com.legal.hold.entity.LegalAssistantsModel;
import com.legal.hold.entity.LegalHoldModel;
import com.legal.hold.entity.UserDetail;
import com.legal.hold.entity.UserInListUserResponse;

@org.springframework.stereotype.Service
public class WebServiceImpl implements Service {
	@Autowired
	WebDao webDao;

	@Override
	public String getSso(String sso) throws SQLException {
		return valueOf(webDao.getSso(sso));
	}

	@Override
	public List<LegalAssistantsModel> getAssistants(String sso) throws SQLException {
		return webDao.getAssistants(sso);
	}

	@Override
	public List<LegalHoldModel> getLegalHoldDetails(List<String> sso) throws SQLException {
		return (webDao.getLegalHoldDetails(sso));
	}

	@Override
	public boolean addUser(UserDetail user) {
		return webDao.addUser(user);
	}

	@Override
	public boolean removeUser(String sso, String loggedinSso) {
		return webDao.removeUser(sso, loggedinSso);
	}

	@Override
	public List<UserInListUserResponse> listUser() {
		return webDao.listUser();
	}

	@Override
	public String getRoleName(String sso) throws SQLException {
		return valueOf(webDao.getRoleName(sso).toString().replaceAll("(^\\[|\\]$)", ""));
	}

	@Override
	public int getRoleId(String sso) throws SQLException {
		@SuppressWarnings("unchecked")
		List<String> list = webDao.getRoleId(sso);
		List<Integer> list1 = list.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		int roleid = list1.get(0);
		return roleid;
	}
	
	@Override
	public AppAlert getAlertDetails(String alertType) {
		return webDao.getAlertDetails(alertType);
	}
}
