package com.liu.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;

import com.liu.depends.Depends;
import com.liu.depends.WanDouJia;
import com.liu.helper.Cache;
import com.liu.helper.Config;
import com.liu.helper.Database;
import com.liu.helper.Utils;
import com.liu.message.Message;
import com.liu.message.TimelineListItem;

public class TimelineActivity extends BaseActivity {
	private static final String TAG = "TIMELINE";
	private static TreeSet<TimelineListItem> treesetItems;
	//This redundance items is just for TimelineAdapter, becaust treesetItems can't satisfy the apies in adapter
	private static List<TimelineListItem> listItems = new ArrayList<TimelineListItem>();
	private static TimelineAdapter timelineAdapter;
	private static PopupWindow popupWindow;
	private static TreeMap<String, TreeSet<Message>> allMessages;
	private static ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_timeline);
		
		Cache.init(TimelineActivity.this, Config.SHAREDPREFERENCES_NAME, psListener);
		
		if(Config.getMe() == null)
		    Config.setMe(Utils.getME(TimelineActivity.this));
		Log.d(TAG, "ME_info: " + Utils.getME(TimelineActivity.this).toJson());
		
		Depends.initAll(TimelineActivity.this);
		ViewGroup bannerContainer = (ViewGroup) this.findViewById(R.id.banner_ad_container);
		WanDouJia.showBanner(TimelineActivity.this, bannerContainer);
		
//		Log.d(TAG, "come in timeline activity.");
		allMessages = groupMessage(Database.readAllMessages());
		
//		if(allMessages.isEmpty()) {
//			Log.d(TAG, "read 0 messages, so fill some test data.");
//			FillDBTestData.fillTimelineMsgs();
//			allMessages.putAll(groupMessage(db.readAllMessages()));
//			Log.d(TAG, "read " + allMessages.size() + " users' messages now.");
//		}
		
		//TODO, delete
		for(String theOtherGuy : allMessages.keySet())
			for(Message msg : allMessages.get(theOtherGuy))
			    Log.d(TAG, "READ MSG: " + theOtherGuy + " - " + msg.toJson());
		Log.d(TAG, "Read " + allMessages.size() + " users' messages");
		
		treesetItems  = getListItems(allMessages);
		listView = (ListView) findViewById(R.id.msg_items);
//		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		listItems.clear();
		listItems.addAll(treesetItems);
		timelineAdapter = new TimelineAdapter(this, listItems);
		Log.d(TAG, "listItems for Timeline, " + listItems);
		listView.setAdapter(timelineAdapter);
		
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
	
	public static TreeSet<TimelineListItem> getListItems(
			TreeMap<String, TreeSet<Message>> allMessages) {
		TreeSet<TimelineListItem> listItems = new TreeSet<TimelineListItem>();
		for(TreeSet<Message> messages : allMessages.values()) {
			TimelineListItem item = TimelineListItem.fromMsg(messages.last());
			item.setUnread(Cache.getUnread(item.getAssociate()));
			listItems.add(item);
		}
		return listItems;
	}

	@SuppressLint("NewApi")
	private void showPopUp(View v) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TableLayout layout = (TableLayout) inflater.inflate(R.layout.layout_timeline_newmsg, null);

		popupWindow = new PopupWindow(layout, 200, 300, true);
		
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
		popupWindow.dismiss();
		startActivity(intent);
//		finish();
	}
	
	public void onReplymsgClick(View v) {
		Intent intent = new Intent();
		intent.setClass(TimelineActivity.this, ReplyMsgActivity.class);
		popupWindow.dismiss();
		startActivity(intent);
	}
	
	public void onClickME(View v) {
		Intent intent = new Intent();
		intent.setClass(TimelineActivity.this, MeActivity.class);
		popupWindow.dismiss();
		startActivity(intent);
	}
	
	public void onReadmeClick(View v) {
		Intent intent = new Intent();
		intent.setClass(TimelineActivity.this, ReadmeActivity.class);
		popupWindow.dismiss();
		startActivity(intent);
	}
	
	public void onClickAppWall(View v) {
		WanDouJia.showAppWall(TimelineActivity.this);
	}
	
	public static TreeMap<String, TreeSet<Message>> groupMessage(List<Message> messages) {
		TreeMap<String, TreeSet<Message>> allMessages = new TreeMap<String, TreeSet<Message>>();
		for(Message message : messages) {
			TreeSet<Message> uMsgs = new TreeSet<Message>();
			String theOtherGuy = Utils.getTheOtherGuy(message, Config.getMe().getEmail());
			if(StringUtils.isBlank(theOtherGuy)) {
				Database.dropMessage(message);
				continue;
			}
			if(allMessages.containsKey(theOtherGuy))
				uMsgs = allMessages.get(theOtherGuy);
			uMsgs.add(message);
			allMessages.put(theOtherGuy, uMsgs);
		}
		return allMessages;
	}
	
	private static void addMessage(Message message) {
		TreeSet<Message> uMsgs = new TreeSet<Message>();
		String theOtherGuy = Utils.getTheOtherGuy(message, Config.getMe().getEmail());
		if(allMessages.containsKey(theOtherGuy))
			uMsgs = allMessages.get(theOtherGuy);
		uMsgs.add(message);
		allMessages.put(theOtherGuy, uMsgs);
	}
	
	public static void dataChange(Message newmsg) {
		// not opened, receiver run in background
		if(timelineAdapter == null)
			return;
		boolean matched = false;
		addMessage(newmsg);
		treesetItems  = getListItems(allMessages);
		String associate = Utils.getTheOtherGuy(newmsg, Config.getMe().getEmail());
		for(TimelineListItem item : treesetItems){
			if(item.getAssociate().equals(associate)) {
				item.setTime(newmsg.getTime());
				item.setContent(newmsg.getContent());
				item.setUnread(Cache.getUnread(item.getAssociate()));
				Log.d(TAG, "$unread " + associate + "=" + Cache.getUnread(item.getAssociate()));
				matched = true;
			}
		}
		if (false == matched) {
			TimelineListItem newItem = TimelineListItem.fromMsg(newmsg);
			newItem.setUnread(Cache.getUnread(newItem.getAssociate()));
			treesetItems.add(newItem);
		}
		listItems.clear();
		listItems.addAll(treesetItems);
		Log.d(TAG, "data changed, " + listItems);
		timelineAdapter.notifyDataSetChanged();
	}
	
	public static boolean isAppInBackround() {
		return timelineAdapter == null;
	}
	
	private OnSharedPreferenceChangeListener psListener = new OnSharedPreferenceChangeListener() {  
        @Override  
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        	Log.i("Timeline.KEY_CHANGE detected, ", "key: " + key);
        	//TODO, this may not be a string key
            if(Cache.isUnReadMsgKey(key)) {
            	Log.i("Timeline.KEY_CHANGE", "key: " + key + " = " + sharedPreferences.getInt(key, 0));
            	String associate = Cache.getAssociateFromUnreadMsgKey(key);
            	for(TimelineListItem item : listItems) {
            		if(item.getAssociate().equals(associate)) 
            			item.setUnread(Cache.getUnread(associate));
            	}
            	timelineAdapter.notifyDataSetChanged();
            }
        }
    };  
}
