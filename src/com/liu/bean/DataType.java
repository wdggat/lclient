package com.liu.bean;

public enum DataType {
	NONE(-1), REGIST(1), LOGIN(2), NEW_MSG(3), REPLY(4);
	private int code;

	private DataType(int code) {
		this.code = code;
	}

	public static DataType getByValue(int code) {
		switch (code) {
		case 1:
			return REGIST;
		case 2:
			return LOGIN;
		case 3:
			return NEW_MSG;
		case 4:
			return REPLY;
		default:
			return NONE;
		}
	}
}
