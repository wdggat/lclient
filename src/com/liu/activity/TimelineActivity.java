package com.liu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;

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
//				PopupWindow pw = new PopupWindow();
//				pw.setFocusable(true);
//				pw.setOutsideTouchable(true);
//				pw.showAsDropDown(findViewById(R.id.timeline_newmsg_menu));
				showPopUp(v);
			}
			
		});
		PopupWindow pw = new PopupWindow();
		
	}
	
	@SuppressLint("NewApi")
	private void showPopUp(View v) {
//		TableLayout layout = new TableLayout(this);
//		layout.setBackgroundColor(Color.BLACK);
//		layout.setOrientation(LinearLayout.HORIZONTAL);
//		TextView tv = new TextView(this);
//		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		tv.setText("新建消息");
//		layout.addView(tv);
//		
//		TextView tv2 = new TextView(this);
//		tv2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		tv2.setText("回复消息");
//		layout.addView(tv2);
		TableLayout layout = (TableLayout) findViewById(R.id.timeline_newmsg_menu);

		PopupWindow popupWindow = new PopupWindow(layout, 200, 200, true);
		
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(getWallpaper());
		popupWindow.setClippingEnabled(true);
		
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		
//		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());
		popupWindow.showAsDropDown(v, location[0], location[1]-popupWindow.getHeight());
		popupWindow.update();
		Log.d(TAG, "popupWindow.isShowing() = " + popupWindow.isShowing());
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
