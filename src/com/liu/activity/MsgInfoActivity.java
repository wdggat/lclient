package com.liu.activity;

import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MsgInfoActivity extends BaseActivity {
	private static final String TAG = "MsgInfo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_msginfo);
		
		Intent intent = getIntent();
		ArrayList<String> msgList  = intent.getExtras().getStringArrayList("msgs");
		Log.d(TAG, "msgs put in, " + ArrayUtils.toString(msgList));
	}
}
