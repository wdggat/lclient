package com.liu.helper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

public class Cache {
	private static final String TAG = Cache.class.getName();
	
	private static Editor sp;
	private static SharedPreferences reader;
	private static boolean inited = false;
	private static final String unreadMsgPrefix = "u:";
	public static void init(Context context, String name) {
		if(sp == null)
		    sp = context.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).edit();
		if(reader == null)
		    reader = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		dumpSecondCache();
		SecondCache.reset();
		inited = true;
		Log.i(TAG, "Cache inited.");
	}
	
	public static void init(Context context, String name, OnSharedPreferenceChangeListener listener) {
		init(context, name);
		registListener(listener);
	}
	
	private static void dumpSecondCache() {
		Map<String, Integer> allUnread = SecondCache.getAllUnread();
		for(String associate : allUnread.keySet())
			increby(unreadMsgPrefix + associate, allUnread.get(associate));
	}
	
	public static void registListener(OnSharedPreferenceChangeListener listener) {
		reader.registerOnSharedPreferenceChangeListener(listener);
	}
	
	public static int increUnread(String associate) {
		if(!inited) {
			SecondCache.increUnread(associate);
		}
		return putInt(unreadMsgPrefix + associate, getUnread(associate) + 1);
	}
	
	public static int resetUnread(String associate) {
		return putInt(unreadMsgPrefix + associate, 0);
	}
	
	public static int getUnread(String associate) {
		return reader.getInt(unreadMsgPrefix + associate, 0);
	}
	
	private static int increby(String realKey, int value){
		return putInt(realKey, reader.getInt(realKey, 0) + value);
	}
	
	private static int putInt(String realKey, int value) {
		sp.putInt(realKey, value);
		commit();
		return value;
	}
	
	public static boolean isUnReadMsgKey(String key) {
		if(StringUtils.isBlank(key))
			return false;
		return key.startsWith(unreadMsgPrefix) && key.length() > unreadMsgPrefix.length();
	}
	
	public static String getAssociateFromUnreadMsgKey(String key) {
		if(!isUnReadMsgKey(key))
			return "";
		return key.substring(2);
	}
	
	private static void commit() {
		sp.commit();
	}
	
	private static class SecondCache {
		private static Map<String, Integer> unReadCache = new HashMap<String, Integer>();
		public static void increUnread(String associate) {
			if(unReadCache.containsKey(associate))
			    unReadCache.put(associate, unReadCache.get(associate) + 1);
			else
				unReadCache.put(associate, 1);
		}
		
		public static Map<String, Integer> getAllUnread() {
			return unReadCache;
		}
		
		public static void reset() {
			unReadCache.clear();
		}
	}
}
