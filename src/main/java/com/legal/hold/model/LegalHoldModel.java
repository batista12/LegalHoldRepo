package com.legal.hold.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LegalHoldModel implements RowMapper<LegalHoldModel> {

	private String ssoid;
	private String email;
	private String name;
	private String mattersecuritygroup;
	private String assistant;
	private String attorney;
	private String mattername;
	private String matteridentifier;
	private String matterkey;
	private String silentcustodian;

	public String getSsoid() {
		return ssoid;
	}

	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
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

	public String getMattersecuritygroup() {
		return mattersecuritygroup;
	}

	public void setMattersecuritygroup(String mattersecuritygroup) {
		this.mattersecuritygroup = mattersecuritygroup;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getAttorney() {
		return attorney;
	}

	public void setAttorney(String attorney) {
		this.attorney = attorney;
	}

	public String getMattername() {
		return mattername;
	}

	public void setMattername(String mattername) {
		this.mattername = mattername;
	}

	public String getMatteridentifier() {
		return matteridentifier;
	}

	public void setMatteridentifier(String matteridentifier) {
		this.matteridentifier = matteridentifier;
	}

	public String getMatterkey() {
		return matterkey;
	}

	public void setMatterkey(String matterkey) {
		this.matterkey = matterkey;
	}

	public String getSilentcustodian() {
		return silentcustodian;
	}

	public void setSilentcustodian(String silentcustodian) {
		this.silentcustodian = silentcustodian;
	}

	@Override
	public LegalHoldModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		LegalHoldModel legalHoldModel = new LegalHoldModel();
		legalHoldModel.setSsoid(rs.getString(1));
		legalHoldModel.setEmail(rs.getString(2));
		legalHoldModel.setName(rs.getString(3));
		legalHoldModel.setMattersecuritygroup(rs.getString(4));
		legalHoldModel.setAssistant(rs.getString(5));
		legalHoldModel.setAttorney(rs.getString(6));
		legalHoldModel.setMattername("");
		legalHoldModel.setMatteridentifier("");
		legalHoldModel.setMatterkey("");
		legalHoldModel.setSilentcustodian("");
		return legalHoldModel;
	}

}
