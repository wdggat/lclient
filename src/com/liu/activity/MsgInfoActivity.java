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
import com.liu.helper.Utils;
import com.liu.message.Message;
import com.liu.message.Response;

public class MsgInfoActivity extends BaseActivity {
	private static final String TAG = "MsgInfo";
	private static String associate; 
	private static ArrayList<Message> msgList = new ArrayList<Message>();
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
		Log.d(TAG, "msgs read from bundle, " + msgStrList);
		msgList.clear();
		for(String msgStr : msgStrList)
			msgList.add(JSON.parseObject(msgStr, Message.class));
		associate = intent.getExtras().getString("uid");
		((TextView)findViewById(R.id.msginfo_uid)).setText(associate);
		
		adapter = new MsgInfoAdapter(this, msgList);
		msgsView = (ListView)findViewById(R.id.msgs);
		msgsView.setAdapter(adapter);
		msgsView.setSelection(msgList.size() - 1);
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
		Log.i(TAG, "new_quick_msg inserted into db, " + msg.toJson());
		contentET.setText("");
		contentET.clearFocus();
		dataChange(msg);
		TimelineActivity.dataChange(msg);
		Database.insertMessage(msg);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//TODO
	}
	
	public static void dataChange(Message message) {
		//if not current user's msg, discard
		if (adapter == null || msgList == null
				|| (!msgList.isEmpty() && !Utils.getTheOtherGuy(message,Config.getMe().getEmail()).equals(
						Utils.getTheOtherGuy(msgList.get(0), Config.getMe().getEmail()))))
			return;
		msgList.add(message);
		adapter.notifyDataSetChanged();
		Log.d(TAG, "$data_changed," + message.toJson());
		msgsView.setSelection(msgList.size() - 1);
	}
}
