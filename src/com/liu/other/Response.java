package com.liu.other;

public class Response {
	private static final int SUCCESS_CODE = 200;
	
	private int code;
	private String content;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean succeed() {
		return code == SUCCESS_CODE;
	}
}
