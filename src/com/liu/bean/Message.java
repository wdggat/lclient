package com.liu.bean;

import com.alibaba.fastjson.JSON;

public class Message {
	private String content;
	private long time;
	private String to;
	private DataType dataType;
	
	public String toJson() {
		return JSON.toJSONString(this);
	}
}
