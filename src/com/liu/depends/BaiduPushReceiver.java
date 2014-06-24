package com.liu.depends;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.liu.activity.MsgInfoActivity;
import com.liu.activity.TimelineActivity;
import com.liu.bean.Message;
import com.liu.tool.Utils;

public class BaiduPushReceiver extends FrontiaPushMessageReceiver {
	private static final String TAG = BaiduPushReceiver.class.getName();
	private static final int SUCCESS_CODE = 0;
	private static final String TYPE_MESSAGE = "m";

	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		if(errorCode == SUCCESS_CODE) {
			Utils.setBind(context, true);
			//TODO
		} else {
			Log.e(TAG, "bind baidu server failed, errorCode " + errorCode);
		}
	}

	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListTags(Context context, int errorCode,
			List<String> tags, String requestId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(Context context, String message, String customContentString) {
		Log.i(TAG, "$msg_receive: " + message);
		Message msg = JSON.parseObject(message, Message.class);
		TimelineActivity.dataChange(msg);
		MsgInfoActivity.dataChange(msg);
	}

	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		Log.i(TAG, "Receiver unbind, errorCode: " + errorCode);
		if(errorCode == SUCCESS_CODE) {
			Utils.setBind(context, false);
			//TODO
		}
	}
	
}
