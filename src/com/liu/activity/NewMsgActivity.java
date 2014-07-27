package com.liu.activity;

import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
		if(Utils.anyEmpty(receiverET, subjectET, contentET)) {
			Toast.makeText(this, "Something empty.", Toast.LENGTH_SHORT).show();
			return;
		}
		Message message = new Message(Config.getMe().getEmail(), Config.getMe().getUid(), receiverET.getText().toString(), subjectET.getText().toString(), System.currentTimeMillis()/1000, contentET.getText().toString(), DataType.NEW_MSG);
		Log.d(TAG, "$sending msg: " + message.toJson());
		Response res = null;
		try {
			res = new SendMsgAction().execute(message).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		Log.i(TAG, res == null ? "response: null" : ("$res: " + res.toString()));
		if(res == null || !res.succeed()) {
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
	
	private class SendMsgAction extends AsyncTask<Message, Void, Response> {

		@Override
		protected Response doInBackground(Message... msgs) {
			return RequestHelper.sendMessage(msgs[0]);
		}
		
	}
}
