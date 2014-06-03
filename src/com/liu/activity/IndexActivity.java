package com.liu.activity;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liu.bean.DataType;
import com.liu.bean.Response;
import com.liu.bean.User;
import com.liu.tool.Config;
import com.liu.tool.RequestHelper;
import com.liu.tool.Utils;

public class IndexActivity extends BaseActivity{
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
			}
			
		});
		
		Button loginBt = (Button)findViewById(R.id.loginbt);
		loginBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = ((EditText)findViewById(R.id.username)).getText().toString();
				String password = ((EditText)findViewById(R.id.password)).getText().toString();
				if(!checkParams(username, password)) {
					Toast.makeText(IndexActivity.this, "Username or password not property.", Toast.LENGTH_SHORT).show();
					return;
				}
				if(login(username, password)) {
					Intent intent = new Intent();
					intent.setClass(IndexActivity.this, TimelineActivity.class);
					startActivity(intent);
				    cacheLoginInfo(username, password);
				}
				else
					Toast.makeText(IndexActivity.this, "Username and password isn't a couple.", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void passwordForget(View v) {
		//TODO
	}
	
	private boolean checkParams(String username, String password) {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
			return false;
		return true;
	}
	
	private boolean login(String username, String password) {
		String jsonStr = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
		Log.d(TAG, "user login: " + jsonStr);
		Response res = RequestHelper.sendData(DataType.LOGIN, jsonStr);
		if(res.succeed()) {
			User me = User.fromJsonStr(res.getContent());
			Utils.cacheUserInfo(IndexActivity.this, me);
			return true;
		}
		return false;
	}
	
	private boolean cacheLoginInfo(String username, String password) {
		Editor sp = IndexActivity.this.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).edit();
		sp.putString("username", username);
		sp.putString("password", password);
		sp.putBoolean(Config.LOGINED_KEY, true);
		return sp.commit();
	}
}
