package com.liu.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liu.bean.Message;

public class MsgInfoActivity extends BaseActivity {
	private static final String TAG = "MsgInfo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_msginfo);
		
		Intent intent = getIntent();
		ArrayList<String> msgStrList  = intent.getExtras().getStringArrayList("msgs");
		ArrayList<Message> msgList = new ArrayList<Message>();
		for(String msgStr : msgStrList)
			msgList.add(JSON.parseObject(msgStr, Message.class));
		String associate = intent.getExtras().getString("uid");
		((TextView)findViewById(R.id.msginfo_uid)).setText(associate);
		Log.d(TAG, "msgs read from bundle, " + msgList);
		
		MsgInfoAdapter adapter = new MsgInfoAdapter(this, msgList);
		ListView v = (ListView)findViewById(R.id.msgs);
		v.setAdapter(adapter);
	}
}
