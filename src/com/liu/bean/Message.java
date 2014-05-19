package com.liu.bean;

import com.alibaba.fastjson.JSON;

public class Message {
	private String associate;
	private String subject;
	private long time;
	private String content;
	private DataType dataType;
	private boolean sentByMe;

	public String getAssociate() {
		return associate;
	}

	public void setAssociate(String associate) {
		this.associate = associate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public boolean isSentByMe() {
		return sentByMe;
	}

	public void setSentByMe(boolean sentByMe) {
		this.sentByMe = sentByMe;
	}

	public Message(String associate, String subject, long time, String content,
			DataType dataType, boolean sentByMe) {
		super();
		this.associate = associate;
		this.subject = subject;
		this.time = time;
		this.content = content;
		this.dataType = dataType;
		this.sentByMe = sentByMe;
	}

	public String toJson() {
		return JSON.toJSONString(this);
	}
}
