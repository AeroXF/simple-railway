package com.fengyunjie.railway.model;

/**
 * 常用联系人表
 * @author fengyunjie
 * @date   2016-08-12
 */
//@Entity
//@Table(name="contacts")
public class Contacts extends BaseEntity {
	private int    userId;					//外键:用户表主键
	private String username;				//联系人姓名
	private int    gender;					//性别:0男, 1女
	private int    country;					//国家地区:0中国
	private int    credentialType;			//证件类型：1身份证
	private String credentialNumber;		//证件号码
	private String telephone;				//电话号码
	private int    custType;				//旅客类型:0成人
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getCountry() {
		return country;
	}
	public void setCountry(int country) {
		this.country = country;
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
	
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
}
