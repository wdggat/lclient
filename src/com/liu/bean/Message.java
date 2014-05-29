package com.liu.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.alibaba.fastjson.JSON;

public class Message implements Comparable<Message>{
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
	
	public Message(){}

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
	
	public String getFormatedTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return sdf.format(new Date((long)time * 1000));
	}
	
/*	public String toShowInMsgInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return content + "\n" + sdf.format(new Date((long)time * 1000));
	}*/
	
	@Override
	public int compareTo(Message another) {
		long anotherTime = another.getTime();
		return time > anotherTime ? 1 : (time == anotherTime ? 0 : -1);
	}
}
