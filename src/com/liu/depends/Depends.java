package com.liu.depends;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.liu.helper.Config;
import com.liu.helper.Utils;
import com.liu.message.Event;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.loader.Fetcher.AdFormat;

public class Depends {
	private static final String TAG = Depends.class.getCanonicalName();
	
	public static void initAll(Context context) {
		initBaiduPushReceiver(context);
		initWandoujia(context);
	}
	
	private static boolean initBaiduPushReceiver(Context context) {
		// Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
        // 通过share preference实现的绑定标志开关，如果已经成功绑定，就取消这次绑定
        if (!Utils.hasBind(context.getApplicationContext())) {
//		if(true){
            PushManager.startWork(context.getApplicationContext(),
                    PushConstants.LOGIN_TYPE_API_KEY,
                    Utils.getMetaValue(context, "api_key"));
            // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
            // PushManager.enableLbs(getApplicationContext());
        }
		
		if(!Utils.getSharedPreferences(context, Config.BAIDU_PUSH_UINFO_UPLOADED, false)) {
			String baiduUserId = Utils.getSharedPreferences(context, Event.BAIDU_USERID, "");
			String baiduChannelId = Utils.getSharedPreferences(context, Event.BAIDU_CHANNELID, "");
			if(!StringUtils.isBlank(baiduUserId)) {
			    return BaiduPushReceiver.bindBaiduPushInfo(context,	baiduUserId, baiduChannelId);
			} else {
				Utils.setBaiduPushBind(context, false);
				return false;
			}
		}
		return true;
	}
	
	public static boolean initWandoujia(Context context) {
		// Init AdsSdk.
		 try {
			 if(WanDouJia.isInitialized())
				 return true;
		   Ads.init(context, Config.WDJ_APPID, Config.WDJ_SECRETKEY);
		   Log.i(TAG, "wan dou jia inited.");
		   Ads.preLoad(context, AdFormat.appwall, Config.WDJ_APP, Config.WDJ_ADSID_APPLIST);
		   return true;
		 } catch (Exception e) {
		   e.printStackTrace();
		   Log.i(TAG, "Failed to init wan dou jia", e);
		   return false;
		 }
	}
	
}
