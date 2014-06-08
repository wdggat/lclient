package com.liu.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class BaseActivity extends Activity {
	public static String ME;
	public void backtoTimeline(View v){
		Intent intent = new Intent();
		intent.setClass(this, TimelineActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	public void currentActivityFinish(View v) {
		this.finish();
	}
}
