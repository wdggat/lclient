package com.liu.depends;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.liu.activity.BaseActivity;
import com.liu.activity.MsgInfoActivity;
import com.liu.activity.TimelineActivity;
import com.liu.helper.Config;
import com.liu.helper.RequestHelper;
import com.liu.helper.Utils;
import com.liu.message.DataType;
import com.liu.message.Event;
import com.liu.message.Message;
import com.liu.message.Response;

public class BaiduPushReceiver extends FrontiaPushMessageReceiver {
	private static final String TAG = BaiduPushReceiver.class.getName();
	private static final int SUCCESS_CODE = 0;
	private static final String TYPE_MESSAGE = "m";

	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		if(errorCode == SUCCESS_CODE) {
			Utils.setBind(context, true);
			Log.i(TAG, "Succeed to bind baidu-push-server, appid - " + appid + ", userId - " + userId + ", channelId - " + channelId);
			Event baiduBind = new Event(DataType.BAIDU_PUSH_BIND);
			baiduBind.putEntry(Event.BAIDU_USERID, userId);
			baiduBind.putEntry(Event.BAIDU_CHANNELID, channelId);
			baiduBind.putEntry(Event.USERNAME, BaseActivity.ME);
			
			Utils.putSharedPreferences(context, Event.BAIDU_USERID, userId);
			Utils.putSharedPreferences(context, Event.BAIDU_CHANNELID, channelId);
			Response res = RequestHelper.sendEvent(baiduBind);
			if(res.succeed())
				Utils.putSharedPreferences(context, Config.BAIDU_PUSH_UINFO_UPLOADED, true);
			else
				Utils.putSharedPreferences(context, Config.BAIDU_PUSH_UINFO_UPLOADED, false);
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
	public void onMessage(Context context, String message,String customContentString) {
		Log.i(TAG, "$msg_receive: " + message);
		try {
			Message msg = JSON.parseObject(message, Message.class);
			TimelineActivity.dataChange(msg);
			MsgInfoActivity.dataChange(msg);
		} catch (Throwable t) {
			Log.e(TAG, "$msg_dealing_error in baidu.onMessage, " + message, t);
			return;
		}
	}

	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString) {
		Log.i(TAG, "$notification_receive: " + title + ", description: " + description);
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
