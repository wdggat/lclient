package com.liu.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.alibaba.fastjson.JSON;
import com.liu.helper.Config;
import com.liu.helper.Utils;

public class TimelineListItem implements Comparable<TimelineListItem>{
	private String associate;
	private long time;
	private String content;
	private long localTime;
	private int unread;
	
	public TimelineListItem(String associate, long time, String content, long localTime, int unread) {
		this.associate = associate;
		this.time = time;
		this.content = content;
		this.localTime = localTime;
		this.unread = unread;
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
	
	public void setTime(long time) {
		this.time = time;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getLocalTime() {
		return localTime;
	}

	public void setLocalTime(long localTime) {
		this.localTime = localTime;
	}

	public int getUnread() {
		return unread;
	}

	public void setUnread(int unread) {
		this.unread = unread;
	}

	public String getFormatedTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return sdf.format(new Date((long)time * 1000));
	}
	
	public static TimelineListItem fromMsg(Message msg) {
		return new TimelineListItem(Utils.getTheOtherGuy(msg, Config.getMe().getEmail()), msg.getTime(), msg.getContent(), msg.getLocalTime(), 0);
	}

	// NEVER return 0, or Treeset will reject equaled items.
	@Override
	public int compareTo(TimelineListItem another) {
		if (localTime < another.getLocalTime())
			return 1;
		return -1;
//		if(localTime >= another.getLocalTime())
//			return -1;
//		return 0;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
