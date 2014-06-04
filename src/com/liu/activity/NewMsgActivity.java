package com.liu.activity;

import android.content.Intent;
import android.os.Bundle;
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
			//TOTO, notify timeline
			Intent intent = new Intent();
			intent.setClass(NewMsgActivity.this, TimelineActivity.class);
			finish();
			startActivity(intent);
		}
	}
}
