package com.liu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;

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
