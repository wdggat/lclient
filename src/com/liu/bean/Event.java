package com.liu.bean;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class Event {
	public static final String EMAIL = "EMAIL";
	public static final int ECODE_FORGET_PASSWORD = 1;

	public Event() {}

	public Event(int code) {
		this.ecode = code;
		entrys = new HashMap<String, String>();
	}

	private int ecode;
	private Map<String, String> entrys;

	public int getEcode() {
		return ecode;
	}

	public void setEcode(int ecode) {
		this.ecode = ecode;
	}

	public Map<String, String> getEntrys() {
		return entrys;
	}

	public void setEntrys(Map<String, String> entrys) {
		this.entrys = entrys;
	}

	public void putEntry(String key, String value) {
		entrys.put(key, value);
	}
	
	public String getEntry(String key) {
		return entrys.get(key);
	}
	
	public String toJson() {
		return JSON.toJSONString(this);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}
