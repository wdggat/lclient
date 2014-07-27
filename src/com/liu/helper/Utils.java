package com.liu.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.liu.message.Message;
import com.liu.message.User;

public class Utils {
	private static final String CACHED_ME_KEY = "ME";

	// <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />  
    public static boolean isNetWorkAvailable(Context context) {    
         ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
         NetworkInfo info = cm.getActiveNetworkInfo();    
        return info != null && info.isConnected();    
    }   
    
    public static boolean isSDEnabled() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
    
    public static boolean cacheUserInfo(Context context, User user) {
		Editor sp = context.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).edit();
		sp.putString(CACHED_ME_KEY, user.toJson());
		sp.putBoolean(Config.LOGINED_KEY, true);
		return sp.commit();
	}
    
    public static User getME(Context context) {
    	SharedPreferences sp = context.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    	String uinfo = sp.getString(CACHED_ME_KEY, null);
    	return User.fromJsonStr(uinfo);
    }
    
    public static String getSharedPreferences(Context context, String key, String defaultValue) {
    	SharedPreferences sp = context.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    	return sp.getString(key, defaultValue);
    }
    
    public static boolean putSharedPreferences(Context context, String key, String value) {
    	Editor editor = context.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    	editor.putString(key, value);
    	return editor.commit();
    }
    
    public static boolean getSharedPreferences(Context context, String key, boolean defaultValue) {
    	SharedPreferences sp = context.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    	return sp.getBoolean(key, defaultValue);
    }
    
    public static boolean putSharedPreferences(Context context, String key, boolean value) {
    	Editor editor = context.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    	editor.putBoolean(key, value);
    	return editor.commit();
    }
    
    public static String showTime(long unixtime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return sdf.format(new Date(unixtime * 1000));
    }
    
    public static String showDate(long unixtime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		return sdf.format(new Date(unixtime * 1000));
    }
    
    public static boolean anyEmpty(TextView ...textView) {
    	for(TextView tv : textView)
    	    if(StringUtils.isEmpty(tv.getText().toString()))
    	    	return true;
    	return false;
    }
    
    public static boolean checkPassword(String password) {
    	if(StringUtils.isEmpty(password))
    		return false;
    	return password.length() >= 6 && password.length() <= 20;
    }
    
    public static String getTheOtherGuy(Message message, String ME) {
    	return ME.equals(message.getFrom()) ? message.getTo() : message.getFrom();
    }
    
    // 获取ApiKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }

    // 用share preference来实现是否绑定的开关。在ionBind且成功时设置true，unBind且成功时设置false
    public static boolean hasBind(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        String flag = sp.getString("bind_flag", "");
        if ("ok".equalsIgnoreCase(flag)) {
            return true;
        }
        return false;
    }

    public static void setBind(Context context, boolean flag) {
        String flagStr = "not";
        if (flag) {
            flagStr = "ok";
        }
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString("bind_flag", flagStr);
        editor.commit();
    }
    
/*    public static ComponentName getRunningActivity() {
    	ActivityManager activityManager = (ActivityManager)new Activity().getSystemService(Context.ACTIVITY_SERVICE);
    	return activityManager.getRunningTasks(1).get(0).topActivity;
    }*/
        
/*    public static String getEditTextString(View activity, int rid) {
    	return ((EditText)activity.findViewById(R.id.rid)).getText().toString();
    }*/
}
