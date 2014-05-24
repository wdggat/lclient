package com.liu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;

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
		ListView listView = (ListView) findViewById(R.id.msg_items);
		//TODO
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_timeline, R.id.timeline_create_msgbt, new String[]{"新建消息", "回复消息"});
		Button newmsgBt = (Button)findViewById(R.id.timeline_create_msgbt);
		newmsgBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "timeline new msg button pressed.");
				showPopUp(v);
			}
			
		});
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
//		popupWindow.showAsDropDown(v, location[0], location[1] + v.getHeight());
		Log.d(TAG, "popupWindow.isShowing() = " + popupWindow.isShowing() + String.format(", layout.width = %d, layout.height = %d", layout.getWidth(), layout.getHeight()));
//		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 100, 100);
	}
	
	public void onNewmsgClick(View v) {
		Intent intent = new Intent();
		intent.setClass(TimelineActivity.this, NewMsgActivity.class);
		startActivity(intent);
	}
	
	public void onReplyMsgClick(View v) {
		//TODO
	}
	
	public void onClickME(View v) {
		Intent intent = new Intent();
		intent.setClass(TimelineActivity.this, MeActivity.class);
		startActivity(intent);
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
