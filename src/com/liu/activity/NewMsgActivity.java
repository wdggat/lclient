package com.liu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.bean.DataType;
import com.liu.bean.Message;
import com.liu.bean.Response;
import com.liu.tool.Database;
import com.liu.tool.RequestHelper;
import com.liu.tool.Utils;

public class NewMsgActivity extends BaseActivity{
	private static final String TAG = "NEWMSG";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_newmsg);
	}
	
	public void onCalloff(View v) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(NewMsgActivity.this);
//		builder.setCancelable(false);
		builder.setTitle("确认撤消消息?");
		final AlertDialog ad = builder.create();
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				NewMsgActivity.this.finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				ad.cancel();
			}
		});
		ad.show();
	}
	
	public void onSubmit(View v) {
		EditText receiverET = (EditText) findViewById(R.id.newmsg_receiver);
		EditText subjectET = (EditText) findViewById(R.id.newmsg_subject);
		EditText contentET = (EditText) findViewById(R.id.newmsg_content);
		if(Utils.anyEmpty(receiverET, subjectET, contentET)) {
			Toast.makeText(this, "Something empty.", Toast.LENGTH_SHORT).show();
			return;
		}
		Message message = new Message(receiverET.getText().toString(), subjectET.getText().toString(), System.currentTimeMillis()/1000, contentET.getText().toString(), DataType.NEW_MSG, true);
		Response res = RequestHelper.sendMessage(message);
		if(!res.succeed()) {
			Toast.makeText(this, "Mail sent failed.", Toast.LENGTH_SHORT).show();
			return;
		} else {
			Toast.makeText(this, "Succeed.", Toast.LENGTH_SHORT).show();
			Database db = Database.getDatabase(NewMsgActivity.this);
			db.insertSingleMessage(message);
			Log.d(TAG, "New message to " + receiverET.getText().toString() + " wrote into db");
			//TOTO, notify timeline
			Intent intent = new Intent();
			intent.setClass(NewMsgActivity.this, TimelineActivity.class);
			finish();
			startActivity(intent);
		}
	}
}
