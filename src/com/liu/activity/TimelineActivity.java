package com.liu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;

import com.liu.bean.Message;
import com.liu.tool.Database;

public class TimelineActivity extends BaseActivity {
	private static final String TAG = "TIMELINE";
	private Database db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_timeline);
		
		Log.d(TAG, "come in timeline activity.");
		db = Database.getDatabase(this);
		Map<String, List<Message>> allMessages = groupMessage(db.readAllMessages());
		//TODO
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_timeline, R.id.timeline_create_msgbt, new String[]{"新建消息", "回复消息"});
		Button newmsgBt = (Button)findViewById(R.id.timeline_create_msgbt);
		newmsgBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "timeline new msg button pressed.");
				PopupWindow pw = new PopupWindow();
				pw.setFocusable(true);
				pw.setOutsideTouchable(true);
				pw.showAsDropDown(findViewById(R.id.timeline_newmsg_menu));
			}
			
		});
		PopupWindow pw = new PopupWindow();
		
	}
	
	public static Map<String, List<Message>> groupMessage(List<Message> messages) {
		Map<String, List<Message>> allMessages = new HashMap<String, List<Message>>();
		for(Message message : messages) {
			List<Message> uMsgs = new ArrayList<Message>();
			if(allMessages.containsKey(message.getAssociate()))
				uMsgs = allMessages.get(message.getAssociate());
			uMsgs.add(message);
			allMessages.put(message.getAssociate(), uMsgs);
		}
		return allMessages;
	}
	
}
