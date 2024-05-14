package com.legal.hold.model;

public class LoginModel {

	private String ssoid;
	private String fullName;
	private String firstName;
	private String lastName;
	private String email;
	private String business;
	private int roleId = 0;
	private String roleName;

	public String getSsoid() {
		return ssoid;
	}

	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "LoginModel [ssoid=" + ssoid + ", fullName=" + fullName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", business=" + business + ", roleId=" + roleId + ", roleName="
				+ roleName + "]";
	}

}
