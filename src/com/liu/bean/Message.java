package com.liu.bean;

import com.alibaba.fastjson.JSON;

public class Message {
	private String content;
	private long time;
	private String associate;
	private DataType dataType;
	private boolean sentByMe;
	
	public String toJson() {
		return JSON.toJSONString(this);
	}
}
