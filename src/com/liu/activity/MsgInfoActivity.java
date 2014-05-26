package com.liu.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MsgInfoActivity extends BaseActivity {
	private static final String TAG = "MsgInfo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_msginfo);
		
		Intent intent = getIntent();
		ArrayList<String> msgList  = intent.getExtras().getStringArrayList("msgs");
		String associate = intent.getExtras().getString("uid");
		((TextView)findViewById(R.id.msginfo_uid)).setText(associate);
		Log.d(TAG, "msgs read from bundle, " + msgList);
		
	}
}
