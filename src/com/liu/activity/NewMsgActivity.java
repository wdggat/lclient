package com.liu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.depends.WanDouJia;
import com.liu.helper.Config;
import com.liu.helper.Database;
import com.liu.helper.RequestHelper;
import com.liu.helper.Utils;
import com.liu.message.DataType;
import com.liu.message.Message;
import com.liu.message.Response;

public class NewMsgActivity extends BaseActivity{
	private static final String TAG = "NEWMSG";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_newmsg);
		
		ViewGroup bannerContainer = (ViewGroup) this.findViewById(R.id.banner_ad_container);
		WanDouJia.showBanner(NewMsgActivity.this, bannerContainer);
	}
	
	public void onCalloff(View v) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(NewMsgActivity.this);
//		builder.setCancelable(false);
		builder.setTitle("确认撤消消息?");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
				NewMsgActivity.this.finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Log.d(TAG, "cancel the calloff.");
//				ad.cancel();
			}
		});
		final AlertDialog ad = builder.create();
		ad.show();
	}
	
	public void onSubmit(View v) {
		EditText receiverET = (EditText) findViewById(R.id.newmsg_receiver);
		EditText subjectET = (EditText) findViewById(R.id.newmsg_subject);
		EditText contentET = (EditText) findViewById(R.id.newmsg_content);
		Message message = new Message(Config.getMe().getEmail(), Config.getMe().getUid(), receiverET.getText().toString().trim(), subjectET.getText().toString().trim(), System.currentTimeMillis()/1000, contentET.getText().toString().trim(), DataType.NEW_MSG);
		if(!message.isValidMessage()) {
			Toast.makeText(this, "字段不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		Log.d(TAG, "$sending msg: " + message.toJson());
		Response res = RequestHelper.sendMessageAsync(NewMsgActivity.this, message);
		if(res == null) {
			Toast.makeText(this, Config.NETWORK_UNREACHABLE, Toast.LENGTH_SHORT).show();
			return;
		} else if (!res.succeed()) {
			Toast.makeText(this, "Mail sent failed.", Toast.LENGTH_SHORT).show();
			return;
		} else {
			Toast.makeText(this, "Succeed.", Toast.LENGTH_SHORT).show();
			Database db = Database.getDatabase(NewMsgActivity.this);
			db.insertSingleMessage(message);
			Log.d(TAG, "New message to " + receiverET.getText().toString() + " wrote into db");
			
			Intent intent = new Intent();
			intent.setClass(NewMsgActivity.this, TimelineActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
