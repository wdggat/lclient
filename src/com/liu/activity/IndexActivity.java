package com.liu.activity;

import org.apache.commons.lang.StringUtils;

import com.liu.other.Utils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class IndexActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_index);
		
		Button registBt = (Button)findViewById(R.id.registbt);
		registBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setContentView(R.layout.layout_regist);
			}
			
		});
		
		Button loginBt = (Button)findViewById(R.id.loginbt);
		loginBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = Utils.getEditTextString(IndexActivity.this, R.id.username);
				String password = Utils.getEditTextString(IndexActivity.this, R.id.password);
				if(!checkParams(username, password)) {
					Toast.makeText(IndexActivity.this, "Username or password not property.", Toast.LENGTH_SHORT).show();
					return;
				}
				if(login(username, password))
				    setContentView(R.layout.layout_timeline);
				else
					Toast.makeText(IndexActivity.this, "Username and password isn't a couple.", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	private boolean checkParams(String username, String password) {
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password))
			return false;
		return true;
	}
	
	private boolean login(String username, String password) {
		//TODO
		return true;
	}
}
