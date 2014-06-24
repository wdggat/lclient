package com.liu.depends;

import android.content.Context;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.liu.tool.Utils;

public class Depends {
	public static void initAll(Context context) {
		initBaiduPushReceiver(context);
	}
	
	private static void initBaiduPushReceiver(Context context) {
		// Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
        // 通过share preference实现的绑定标志开关，如果已经成功绑定，就取消这次绑定
        if (!Utils.hasBind(context.getApplicationContext())) {
            PushManager.startWork(context.getApplicationContext(),
                    PushConstants.LOGIN_TYPE_API_KEY,
                    Utils.getMetaValue(context, "api_key"));
            // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
            // PushManager.enableLbs(getApplicationContext());
        }
	}
}
