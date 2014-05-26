package com.liu.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class BaseActivity extends Activity {
	public void backtoTimeline(View v){
		Intent intent = new Intent();
		intent.setClass(this, TimelineActivity.class);
		this.finish();
		startActivity(intent);
	}
}
