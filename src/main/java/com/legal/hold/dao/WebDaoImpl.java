package com.legal.hold.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.legal.hold.entity.AppAlert;
import com.legal.hold.entity.LegalAssistantsModel;
import com.legal.hold.entity.LegalHoldModel;
import com.legal.hold.entity.UserDetail;
import com.legal.hold.entity.UserInListUserResponse;

@Service
public class WebDaoImpl implements WebDao {

	final static String ACT_STATUS = "active";
	final static String INACT_STATUS = "inactive";

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	@SuppressWarnings("rawtypes")
	@Override
	public List getSso(String sso) throws SQLException {

		// Query to get queried SSO. If null, return as null in Resultset. TableName for
		// stage LEGAL_HOLD_LIST.
		String query1 = "select ssoid from legal_hold_list where ssoid=?";
		List<String> resultList = jdbcTemplate.queryForList(query1, String.class, new Object[] { sso });
		return resultList;

	}

	@Override
	public List<LegalAssistantsModel> getAssistants(String sso) throws SQLException {
		// Query to get assistants details for queried SSO. TableName for stage
		// LEGAL_HOLD_LIST
		String query2 = "select distinct legal_assistant_contact,legal_assistant_email from legal_hold_list where ssoid=?";

		return jdbcTemplate.query(query2, new RowMapper<LegalAssistantsModel>() {
			@Override
			public LegalAssistantsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new LegalAssistantsModel(rs.getString(1), rs.getString(2));
			}

		}, new Object[] { sso });

	}

	public List<LegalHoldModel> getLegalHoldDetails(List<String> sso) {
		String query = "select distinct ssoid,email,name,matter_security_group,legal_assistant_contact,attorney,matter_name,matter_identifier,matter_key,silent_custodian from legal_hold_list where ssoid in (:sso) ";
		MapSqlParameterSource inQueryParams = new MapSqlParameterSource();
		inQueryParams.addValue("sso", sso);
		return namedJdbcTemplate.query(query, inQueryParams, new LegalHoldModel());
	}

	@Override
	public boolean addUser(UserDetail user) {
		String query1 = "select ssoid from admin_user_details where ssoid=?";
		List<String> resultList = jdbcTemplate.queryForList(query1, String.class, new Object[] { user.getSsoid() });
		if (resultList.isEmpty()) {
			String query = "insert into admin_user_details(ssoid,name,email,business,roleid,status,created_on,created_by) values(?,?,?,?,?,?,sysdate,?)";
			return jdbcTemplate.update(query, new Object[] { user.getSsoid(), user.getName(), user.getEmail(),
					user.getBusiness(), user.getRoleId(), ACT_STATUS, user.getLoggedinSso() }) > 0;
		} else {
			String query = "update admin_user_details set status=?, updated_on=sysdate, updated_by=?, roleid=? where ssoid = ?";
			return jdbcTemplate.update(query,
					new Object[] { ACT_STATUS, user.getLoggedinSso(), user.getRoleId(), user.getSsoid() }) > 0;
		}
	}

	@Override
	public boolean removeUser(String sso, String loggedinSso) {
		String query = "update admin_user_details set status=?, updated_on=sysdate, updated_by=? where ssoid =?";
		int i = jdbcTemplate.update(query, new Object[] { INACT_STATUS, loggedinSso, sso });
		return i > 0;
	}

	@Override
	public List<UserInListUserResponse> listUser() {
		String query = "select a.ssoid, a.name, r.rolename from admin_user_details a, admin_role_desc r where a.roleid = r.roleid and a.status='active'";
		return jdbcTemplate.query(query, new UserInListUserResponse());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getRoleName(String sso) throws SQLException {
		String query = "select r.rolename from admin_user_details a, admin_role_desc r where a.roleid = r.roleid and a.status=? and a.ssoid=?";
		List<String> resultList = jdbcTemplate.queryForList(query, String.class, new Object[] { ACT_STATUS, sso });
		if (resultList.isEmpty()) {
			resultList.add("User");
		}
		return resultList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getRoleId(String sso) throws SQLException {
		String query = "select roleid from admin_user_details where status=? and ssoid=?";
		List<String> resultList = jdbcTemplate.queryForList(query, String.class, new Object[] { ACT_STATUS, sso });
		if (resultList.isEmpty()) {
			resultList.add("0");
		}
		return resultList;
	}

	@Override
	public AppAlert getAlertDetails(String alertType) {
		String query = "select ALERT_TITLE, ALERT_MESSAGE, IS_ACTIVE from app_alerts where ALERT_TYPE=?";
		AppAlert queryRes = null;
		try {
			queryRes = jdbcTemplate.queryForObject(query, new AppAlert(), alertType);
		} catch (Exception e) {
			queryRes = new AppAlert();
		}
		return queryRes;

	}
}
