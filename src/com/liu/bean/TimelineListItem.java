package com.liu.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public static TimelineListItem fromMsg(Message msg) {
		return new TimelineListItem(msg.getAssociate(), msg.getTime(), msg.getContent());
	}
	
	public String toString() {
		return toShow();
	}
	
//	public static final TimelineListItem EMPTY = new TimelineListItem("no messages yet", 0, "..."); 
	
	private String toShow() {
		String msgGlimpse = content.length() <= MSG_PREVIEW ? content : content.substring(0, MSG_PREVIEW) + "..."; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return associate + "    " + sdf.format(new Date(time)) + "\n" +  msgGlimpse;
	}
}
