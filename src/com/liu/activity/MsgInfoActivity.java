package com.liu.activity;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liu.bean.Message;
import com.liu.bean.Response;
import com.liu.tool.Database;
import com.liu.tool.RequestHelper;

public class MsgInfoActivity extends BaseActivity {
	private static final String TAG = "MsgInfo";
	private static String associate; 
	private static ArrayList<Message> msgList;
	private static MsgInfoAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_msginfo);
		
		Intent intent = getIntent();
		ArrayList<String> msgStrList  = intent.getExtras().getStringArrayList("msgs");
		msgList = new ArrayList<Message>();
		for(String msgStr : msgStrList)
			msgList.add(JSON.parseObject(msgStr, Message.class));
		associate = intent.getExtras().getString("uid");
		((TextView)findViewById(R.id.msginfo_uid)).setText(associate);
		Log.d(TAG, "msgs read from bundle, " + msgList);
		
		adapter = new MsgInfoAdapter(this, msgList);
		ListView v = (ListView)findViewById(R.id.msgs);
		v.setAdapter(adapter);
	}
	
	public void onQuickReply(View v) {
		EditText contentET = (EditText)findViewById(R.id.msginfo_content);
		String content = contentET.getText().toString();
		if(StringUtils.isEmpty(content)) {
			Toast.makeText(this, "Message is empty.", Toast.LENGTH_SHORT).show();
			return;
		}
		Message msg = Message.quickMessage(associate, content);
		Response res = RequestHelper.sendMessage(msg);
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
	}
}
