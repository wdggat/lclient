package com.liu.depends;

import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.liu.helper.Config;
import com.liu.helper.RequestHelper;
import com.liu.helper.Utils;
import com.liu.message.DataType;
import com.liu.message.Event;
import com.liu.message.Response;

public class Depends {
	private static final String TAG = Depends.class.getCanonicalName();
	
	public static void initAll(Context context) {
		initBaiduPushReceiver(context);
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
			Event baiduBind = new Event(DataType.BAIDU_PUSH_BIND);
			baiduBind.putEntry(Event.USERNAME, Config.getMe().getEmail());
			baiduBind.putEntry(Event.BAIDU_USERID, Utils.getSharedPreferences(context, Event.BAIDU_USERID, ""));
			baiduBind.putEntry(Event.BAIDU_CHANNELID, Utils.getSharedPreferences(context, Event.BAIDU_CHANNELID, ""));
			Response res = RequestHelper.sendEventAsync(context, baiduBind);
			if(res != null && res.succeed())
				return Utils.putSharedPreferences(context, Config.BAIDU_PUSH_UINFO_UPLOADED, true);
			Log.e(TAG, "bind baidu server failed, BAIDU_USERID: " + Event.BAIDU_USERID + ", BAIDU_CHANNELID: " + Event.BAIDU_CHANNELID);
			return false;
		}
		return true;
	}
	
}
