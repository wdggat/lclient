package com.liu.activity;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liu.depends.WanDouJia;
import com.liu.helper.Config;
import com.liu.helper.Database;
import com.liu.helper.RequestHelper;
import com.liu.message.Message;
import com.liu.message.Response;

public class MsgInfoActivity extends BaseActivity {
	private static final String TAG = "MsgInfo";
	private static String associate; 
	private static ArrayList<Message> msgList;
	private static MsgInfoAdapter adapter;
	private static ListView msgsView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_msginfo);
		
		ViewGroup bannerContainer = (ViewGroup) this.findViewById(R.id.msginfo_wdj_banner);
		WanDouJia.showBanner(MsgInfoActivity.this, bannerContainer);
		
		Intent intent = getIntent();
		ArrayList<String> msgStrList  = intent.getExtras().getStringArrayList("msgs");
		msgList = new ArrayList<Message>();
		for(String msgStr : msgStrList)
			msgList.add(JSON.parseObject(msgStr, Message.class));
		associate = intent.getExtras().getString("uid");
		((TextView)findViewById(R.id.msginfo_uid)).setText(associate);
//		Log.d(TAG, "msgs read from bundle, " + msgList);
		
		adapter = new MsgInfoAdapter(this, msgList);
		msgsView = (ListView)findViewById(R.id.msgs);
		msgsView.setAdapter(adapter);
		msgsView.requestFocus();
	}
	
	public void onQuickReply(View v) {
		EditText contentET = (EditText)findViewById(R.id.msginfo_content);
		String content = contentET.getText().toString();
		if(StringUtils.isBlank(content)) {
			Toast.makeText(this, "消息不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		Message msg = Message.quickMessage(Config.getMe().getEmail(), Config.getMe().getUid(), associate, content);
		Response res = RequestHelper.sendMessageAsync(MsgInfoActivity.this, msg);
		if(res == null) {
			Toast.makeText(this, Config.NETWORK_UNREACHABLE, Toast.LENGTH_SHORT).show();
			return;
		}
		if(!res.succeed()) {
			Toast.makeText(this, "Message sent failed.", Toast.LENGTH_SHORT).show();
			return;
		}
		Database db = Database.getDatabase(this);
		db.insertSingleMessage(msg);
		Log.i(TAG, "new_quick_msg inserted into db, " + msg.toJson());
		msgList.add(msg);
		contentET.setText("");
		contentET.clearFocus();
		adapter.notifyDataSetChanged();
		msgsView.requestFocus();
	}
	
	public static void dataChange(Message message) {
		if(msgList == null || !msgList.get(0).getFrom().equals(message.getFrom()))
			return;
		msgList.add(message);
		adapter.notifyDataSetChanged();
	}
}
