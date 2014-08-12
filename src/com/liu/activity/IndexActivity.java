package com.liu.activity;

import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.helper.Config;
import com.liu.helper.RequestHelper;
import com.liu.helper.Utils;
import com.liu.message.DataType;
import com.liu.message.Event;
import com.liu.message.Message;
import com.liu.message.Response;
import com.liu.message.User;

public class IndexActivity extends BaseActivity {
	private static final String TAG = "INDEX";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_index);
		
		SharedPreferences sp = IndexActivity.this.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		if(sp.getBoolean(Config.LOGINED_KEY, false)) {
			Intent intent = new Intent();
			intent.setClass(IndexActivity.this, TimelineActivity.class);
			startActivity(intent);
			finish();
		}
		
		Button registBt = (Button)findViewById(R.id.registbt);
		registBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(IndexActivity.this, RegistActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
		
		Button loginBt = (Button)findViewById(R.id.loginbt);
		loginBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = ((EditText)findViewById(R.id.username)).getText().toString();
				String password = ((EditText)findViewById(R.id.password)).getText().toString();
				if(!checkParams(username, password)) {
					Toast.makeText(IndexActivity.this, "用户名或密码不正确.", Toast.LENGTH_SHORT).show();
					return;
				}
				Response response = null;
				try {
					response = new LoginAction().execute(username, password).get();
				} catch (Exception e) {
					Log.e(TAG, "error occurs during login.", e);
					return;
				} 
				if(response.succeed()) {
					User me = User.fromJsonStr(response.getContent());
					Utils.cacheUserInfo(IndexActivity.this, me);
					
					Intent intent = new Intent();
					intent.setClass(IndexActivity.this, TimelineActivity.class);
					startActivity(intent);
				    cacheLoginInfo(username, password);
				    finish();
				} else if(response.networkUnreachable()) {
					Toast.makeText(IndexActivity.this, "网络不可达!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(IndexActivity.this, "用户名密码不匹配.", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
	}
	
	public void passwordForget(View v) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textEntryView = inflater
				.inflate(R.layout.layout_password_forget, null);
		final EditText edtInput = (EditText) textEntryView
				.findViewById(R.id.passwd_forget_email);
		final AlertDialog.Builder builder = new AlertDialog.Builder(IndexActivity.this);
//		builder.setCancelable(false);
		builder.setTitle("请输入邮箱:");
		builder.setView(textEntryView);
		final AlertDialog ad = builder.create();
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				new Runnable() {
					public void run() {
						Event pf = new Event(DataType.PASSWORD_FORGET);
						pf.putEntry(Event.EMAIL, edtInput.getText().toString());
						Response res = RequestHelper.sendEvent(IndexActivity.this, pf);
						if (res.succeed()) {
							ad.cancel();
							Toast.makeText(IndexActivity.this,"Password sent to "+ edtInput.getText().toString(),
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(IndexActivity.this,"Password sent failed.", Toast.LENGTH_SHORT).show();
						}
					}
				}.run();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				ad.cancel();
			}
		});
		ad.show();
	}
	
	private boolean checkParams(String username, String password) {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || !username.contains("@"))
			return false;
		return true;
	}
	
	private boolean cacheLoginInfo(String username, String password) {
		Editor sp = IndexActivity.this.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).edit();
		sp.putString("username", username);
		sp.putString("password", password);
		sp.putBoolean(Config.LOGINED_KEY, true);
		return sp.commit();
	}
	
	private class LoginAction extends AsyncTask<String, Void, Response> {

		@Override
		protected Response doInBackground(String ... loginInfo) {
			return login(loginInfo[0], loginInfo[1]);
		}
		
		private Response login(String username, String password) {
			Event event = new Event(DataType.LOGIN);
			event.putEntry(Event.USERNAME, username);
			event.putEntry(Event.PASSWORD, password);
			Log.d(TAG, "user login: " + event.toJson());
			return RequestHelper.sendEvent(IndexActivity.this, event);
		}
		
	}
}
