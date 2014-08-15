package com.liu.depends;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.liu.helper.Config;
import com.umeng.analytics.MobclickAgent;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.loader.Fetcher.AdFormat;
import com.wandoujia.ads.sdk.widget.AdBanner;

public class WanDouJia {
	private static final String TAG = WanDouJia.class.getName();
	
	public static void showAppWall(Context context) {
		Ads.showAppWall(context, Config.WDJ_ADSID_APPLIST);
		MobclickAgent.onEvent(context, Config.EVENT_SHOW_APPWALL);
		Log.i(TAG, "wan dou jia appwall showed.");
	}
	
	public static void showBanner(Context context, ViewGroup container) {
		AdBanner banner = Ads.showBannerAd(context, container, Config.WDJ_ADSID_BANNER);
		banner.startAutoScroll();
		MobclickAgent.onEvent(context, Config.EVENT_SHOW_BANNER);
		Log.i(TAG, "wan dou jia banner showed.");
	}
	
	public static boolean isInitialized() {
		return Ads.isLoaded(AdFormat.appwall, Config.WDJ_ADSID_APPLIST);
	}
}
