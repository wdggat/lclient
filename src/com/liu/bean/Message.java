package com.liu.bean;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liu.tool.Utils;

public class Message implements Comparable<Message> {
	private static final String EMPTY_SUBJECT = "";
	private String from;
	private String to;
	private String subject;
	private long time;
	private String content;
	private DataType dataType;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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

	/**
	 * @param time
	 *            unix epoch time, length_10
	 */
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

	public Message() {
	}

	/**
	 * @param associate
	 *            The uid or email that this message sent to
	 * @param time
	 *            Unix epoch time, length_10
	 */
	public Message(String from, String to, String subject, long time,
			String content, DataType dataType) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.time = time;
		this.content = content;
		this.dataType = dataType;
	}
	
	public String toJson() {
		return JSON.toJSONString(this);
	}

	public String getFormatedTime() {
		return Utils.showTime(time);
	}

	/*
	 * public String toShowInMsgInfo() { SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US); return content + "\n"
	 * + sdf.format(new Date((long)time * 1000)); }
	 */

	@Override
	public int compareTo(Message another) {
		long anotherTime = another.getTime();
		return time > anotherTime ? 1 : (time == anotherTime ? 0 : -1);
	}

	public static Message quickMessage(String from, String to, String content) {
		return new Message(from, to, EMPTY_SUBJECT,
				System.currentTimeMillis() / 1000, content, DataType.QUICK_MSG);
	}
	
	public boolean isSentBy(String uname) {
		if(StringUtils.isEmpty(uname))
			return false;
		return uname.equals(from);
	}

}
