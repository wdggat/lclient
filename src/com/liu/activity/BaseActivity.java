package com.liu.activity;

import com.liu.depends.BaiduPushReceiver;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class BaseActivity extends Activity {
	public void backtoTimeline(View v) {
		Intent intent = new Intent();
		intent.setClass(this, TimelineActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	public void currentActivityFinish(View v) {
		this.finish();
	}
	
	private void rmNotify() {
		BaiduPushReceiver.rmMsgNotify(this);
	}
	
	@Override
	public void onUserInteraction() {
		rmNotify();
	}

	@Override
	public void onResume() {
		super.onResume();
		rmNotify();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
