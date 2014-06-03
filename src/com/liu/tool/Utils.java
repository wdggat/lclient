package com.liu.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.liu.bean.User;

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
    	return JSON.parseObject(uinfo, User.class);
    }
    
    public static String showTime(long unixtime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return sdf.format(new Date(unixtime * 1000));
    }
    
    public static String showDate(long unixtime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		return sdf.format(new Date(unixtime * 1000));
    }
    
/*    public static String getEditTextString(View activity, int rid) {
    	return ((EditText)activity.findViewById(R.id.rid)).getText().toString();
    }*/
}
