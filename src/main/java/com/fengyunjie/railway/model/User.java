package com.fengyunjie.railway.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * 用户表
 * @author fengyunjie
 * @date   2016-08-02
 */
//@Entity
//@Table(name="users")
public class User extends BaseEntity {
	private String 		nickname;							//用户名
	private String 		password;							//密码
	@NotNull
	private String 		username;							//姓名
	private int 		gender;								//性别
	private int			country;							//国家/地区
	private Date		birthday;							//生日
	private int    		credentialType;						//证件类型
	private String 		credentialNumber;					//证件号码
	private String 		email;								//邮箱
	private String 		telephone;							//手机号码
	private Date		createTime;							//创建时间
	private int			custType;							//旅客类型:0成人
	private String 		authority;							//用户角色, ROLE_ADMIN或ROLE_USER

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(int credentialType) {
		this.credentialType = credentialType;
	}
	
	public String getCredentialNumber() {
		return credentialNumber;
	}
	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public int getCustType() {
		return custType;
	}
	public void setCustType(int custType) {
		this.custType = custType;
	}
	
	public String getAuthority() {
		return authority;
	}
	
	public void setAuthority(String authority){
		this.authority = authority;
	}
	
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public int getCountry() {
		return country;
	}
	public void setCountry(int country) {
		this.country = country;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
