package com.liu.other;

import com.alibaba.fastjson.JSON;

public class User {
	public static final int MALE = 0;
	public static final int FEMALE = 1;
	public static final int GENDER_UNSET = 2;
	
	private String emailAddr;
	private int gender;
	private String province;
	private long birthday;
	private String phone;
	private String password;
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public long getBirthday() {
		return birthday;
	}
	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toJson() {
		return JSON.toJSONString(this);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}
