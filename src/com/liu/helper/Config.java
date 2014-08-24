package com.liu.helper;

import com.liu.message.User;

public class Config {
	public static final String APP_NAME = "whoami";
	public static final String server = "http://223.6.250.206:9527";
	public static final String AES128_ECB_KEY = ".>zSpP#j[1>:Mx;[";
	public static final String SDK_VERSION = "0.1";
	public static final String DATABASE_NAME = APP_NAME + ".sqlite";
	public static final String SHAREDPREFERENCES_NAME = APP_NAME; 
	public static final String LOGINED_KEY = "LOGINED";
	public static final String NETWORK_UNREACHABLE = "网络不通!";
	
	//statistic event name
	public static final String EVENT_SHOW_BANNER = "wdj_banner";
	public static final String EVENT_SHOW_APPWALL = "wdj_appwall";
	
	//baidu
	public static final String BAIDU_PUSH_UINFO_UPLOADED = "baidu_push_uinfo_uploaded";
	
	//wan dou jia
	public static final String WDJ_APPID = "100011527";
	public static final String WDJ_SECRETKEY = "41a3ddee1ae4d1f8332b76d553672cd1";
	public static final String WDJ_ADSID_BANNER = "b32a84a8cef022e665004a02980753e6";
	public static final String WDJ_ADSID_APPLIST = "f092c3bf496118d82d9f12315721a2e7";
	public static final String WDJ_APP = "APP";
	
	private static User me;

	public static User getMe() {
		return me;
	}

	public static void setMe(User me) {
		Config.me = me;
	}
	
}
