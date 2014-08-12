package com.liu.helper;

import com.liu.message.User;

public class Config {
	public static final String APP_NAME = "whoami";
	public static final String server = "http://121.40.98.72:9527";
	public static final String AES128_ECB_KEY = ".>zSpP#j[1>:Mx;[";
	public static final String SDK_VERSION = "0.1";
	public static final String DATABASE_NAME = APP_NAME + ".sqlite";
	public static final String SHAREDPREFERENCES_NAME = APP_NAME; 
	public static final String LOGINED_KEY = "LOGINED";
	public static final String NETWORK_UNREACHABLE = "网络不通!";
	
	public static final String BAIDU_PUSH_UINFO_UPLOADED = "baidu_push_uinfo_uploaded";
	
	private static User me;

	public static User getMe() {
		return me;
	}

	public static void setMe(User me) {
		Config.me = me;
	}
	
}
