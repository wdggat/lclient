package com.liu.bean;

import com.liu.bean.DataType;

public enum DataType {
	NONE(-1), NEW_MSG(1), REPLY(2), QUICK_MSG(3), REGIST(100), LOGIN(101), PASSWORD_FORGET(102), PASSWORD_CHANGE(103);
	private int code;

	private DataType(int code) {
		this.code = code;
	}
	
	public int getValue() {
		return code;
	}

	public static DataType getByValue(int code) {
		switch (code) {
		case 100:
			return REGIST;
		case 101:
			return LOGIN;
		case 1:
			return NEW_MSG;
		case 2:
			return REPLY;
		case 3:
			return QUICK_MSG;
		case 102:
			return PASSWORD_FORGET;
		case 103:
			return PASSWORD_CHANGE;
		default:
			return NONE;
		}
	}
	
	public boolean isTypeEvent() {
		return code >= REGIST.code;
	}
	
	public boolean isTypeMessage() {
		return code < REGIST.code;
	}
}
