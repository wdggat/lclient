package com.liu.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.liu.activity.BaseActivity;
import com.liu.helper.Config;
import com.liu.helper.Utils;

public class TimelineListItem implements Comparable<TimelineListItem>{
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
	
	public void setTime(long time) {
		this.time = time;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFormatedTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return sdf.format(new Date((long)time * 1000));
	}
	
	public static TimelineListItem fromMsg(Message msg) {
		return new TimelineListItem(Utils.getTheOtherGuy(msg, Config.getMe().getEmail()), msg.getTime(), msg.getContent());
	}

	@Override
	public int compareTo(TimelineListItem another) {
		if (time < another.getTime())
			return 1;
		if(time > another.getTime())
			return -1;
		return 0;
	}
	
}
