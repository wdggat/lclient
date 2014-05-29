package com.liu.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;

import com.liu.bean.Message;
import com.liu.bean.TimelineListItem;
import com.liu.tool.Database;

public class TimelineActivity extends BaseActivity {
	private static final String TAG = "TIMELINE";
	private Database db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_timeline);
		
//		Log.d(TAG, "come in timeline activity.");
		db = Database.getDatabase(this);
		final TreeMap<String, TreeSet<Message>> allMessages = groupMessage(db.readAllMessages());
		
		if(allMessages.isEmpty()) {
			Log.d(TAG, "read 0 messages, so fill some test data.");
			FillDBTestData.fillTimelineMsgs();
			allMessages.putAll(groupMessage(db.readAllMessages()));
//			Log.d(TAG, "read " + allMessages.size() + " users' messages now.");
		}
		
		//TODO, delete
		for(TreeSet<Message> msgs : allMessages.values())
			for(Message msg : msgs)
			    Log.d(TAG, "READ MSG: " + msg.toJson());
		Log.d(TAG, "Read " + allMessages.size() + " users' messages");
		
		List listItems  = getListItems(allMessages);
		ListView listView = (ListView) findViewById(R.id.msg_items);
//		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		TimelineAdapter arrayAdapter = new TimelineAdapter(this, listItems);
		listView.setAdapter(arrayAdapter);
		
		Button newmsgBt = (Button)findViewById(R.id.timeline_create_msgbt);
		newmsgBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "timeline new msg button pressed.");
				showPopUp(v);
			}
			
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "position clicked: " + position);
				Bundle bundle = new Bundle();
				ArrayList<String> msgList = new ArrayList<String>();
				TimelineListItem item = (TimelineListItem)parent.getItemAtPosition(position);
				for(Message msg : allMessages.get(item.getAssociate()))
					msgList.add(msg.toJson());
				bundle.putStringArrayList("msgs", msgList);
				bundle.putString("uid", item.getAssociate());
				Log.d(TAG, "msgs put into bundle, " + msgList);
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(TimelineActivity.this, MsgInfoActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
	private List<TimelineListItem> getListItems(
			TreeMap<String, TreeSet<Message>> allMessages) {
		List<TimelineListItem> listItems = new ArrayList<TimelineListItem>();
		for(TreeSet<Message> messages : allMessages.values()) {
			TimelineListItem item = TimelineListItem.fromMsg(messages.last());
			listItems.add(item);
		}
		return listItems;
	}

	@SuppressLint("NewApi")
	private void showPopUp(View v) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TableLayout layout = (TableLayout) inflater.inflate(R.layout.layout_timeline_newmsg, null);

		PopupWindow popupWindow = new PopupWindow(layout, 200, 200, true);
		
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		
		popupWindow.update();
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] + v.getHeight());
//		Log.d(TAG, "popupWindow.isShowing() = " + popupWindow.isShowing() + String.format(", layout.width = %d, layout.height = %d", layout.getWidth(), layout.getHeight()));
	}
	
	public void onNewmsgClick(View v) {
		Intent intent = new Intent();
		intent.setClass(TimelineActivity.this, NewMsgActivity.class);
		startActivity(intent);
	}
	
	public void onReplymsgClick(View v) {
		//TODO
	}
	
	public void onClickME(View v) {
		Intent intent = new Intent();
		intent.setClass(TimelineActivity.this, MeActivity.class);
		startActivity(intent);
	}
	
	public static TreeMap<String, TreeSet<Message>> groupMessage(List<Message> messages) {
		TreeMap<String, TreeSet<Message>> allMessages = new TreeMap<String, TreeSet<Message>>();
		for(Message message : messages) {
			TreeSet<Message> uMsgs = new TreeSet<Message>();
			if(allMessages.containsKey(message.getAssociate()))
				uMsgs = allMessages.get(message.getAssociate());
			uMsgs.add(message);
			allMessages.put(message.getAssociate(), uMsgs);
		}
		return allMessages;
	}
	
}
