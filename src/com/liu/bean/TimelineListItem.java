package com.liu.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimelineListItem {
	private static final int MSG_PREVIEW = 15;
	private String associate;
	private long time;
	private String content;
	
	public TimelineListItem(String associate, long time, String content) {
		this.associate = associate;
		this.time = time;
		this.content = content;
	}
	
	public String getAssociate() {
		return associate;
	}

	public long getTime() {
		return time;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getFormatedTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return sdf.format(new Date((long)time * 1000));
	}
	
	public static TimelineListItem fromMsg(Message msg) {
		return new TimelineListItem(msg.getAssociate(), msg.getTime(), msg.getContent());
	}
	
	public String toString() {
		return toShow();
	}
	
//	public static final TimelineListItem EMPTY = new TimelineListItem("no messages yet", 0, "..."); 
	
	private String toShow() {
		String msgGlimpse = content.length() <= MSG_PREVIEW ? content : content.substring(0, MSG_PREVIEW) + "..."; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return associate + "    " + sdf.format(new Date((long)time * 1000)) + "\n" +  msgGlimpse;
	}
}
