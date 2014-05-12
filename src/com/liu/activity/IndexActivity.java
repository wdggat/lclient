package com.liu.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
				if(login())
				    setContentView(R.layout.layout_timeline);
			}
			
		});
	}
	
	private boolean login() {
		//TODO
		return true;
	}
}
