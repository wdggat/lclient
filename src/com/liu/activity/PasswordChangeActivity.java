package com.liu.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.depends.WanDouJia;
import com.liu.helper.Config;
import com.liu.helper.RequestHelper;
import com.liu.helper.Utils;
import com.liu.message.DataType;
import com.liu.message.Event;
import com.liu.message.Response;

public class PasswordChangeActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_password_change);
		
		ViewGroup bannerContainer = (ViewGroup) this.findViewById(R.id.msginfo_wdj_banner);
		WanDouJia.showBanner(PasswordChangeActivity.this, bannerContainer);
		
		Button backToTimelineBt = (Button)findViewById(R.id.back_bt);
		backToTimelineBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PasswordChangeActivity.this.finish();
			}
			
		});
		
	}
	
	public void passwordChange(View v) {
		EditText oldPassword = (EditText)findViewById(R.id.password_old);
		EditText newPassword = (EditText)findViewById(R.id.password_new);
		EditText newConfirmPassword = (EditText)findViewById(R.id.password_new_confirm);
		if(Utils.anyEmpty(oldPassword, newPassword, newConfirmPassword)) {
			Toast.makeText(this, "Password empty.", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!newPassword.getText().toString().equals(newConfirmPassword.getText().toString())) {
			Toast.makeText(this, "密码确认错误!", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!Utils.checkPassword(newPassword.getText().toString())) {
			Toast.makeText(this, "密码长度需在6到20之间!", Toast.LENGTH_SHORT).show();
			return;
		}
		Event pc = new Event(DataType.PASSWORD_CHANGE);
		pc.putEntry(Event.USERNAME, Config.getMe().getEmail());
		pc.putEntry(Event.PASSWORD, oldPassword.getText().toString());
		pc.putEntry(Event.PASSWORD_NEW, newPassword.getText().toString());
		Response res = RequestHelper.sendEventAsync(PasswordChangeActivity.this, pc);
		if(res == null) {
			Toast.makeText(this, "sorry, 网络不通!", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!res.succeed()){
			Toast.makeText(this, "sorry, " + res.getContent(), Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
		
		finish();
	}
}
