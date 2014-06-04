package com.liu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.bean.DataType;
import com.liu.bean.Event;
import com.liu.bean.Response;
import com.liu.tool.RequestHelper;
import com.liu.tool.Utils;

public class PasswordChangeActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_password_change);
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
		Event pc = new Event(DataType.PASSWORD_CHANGE);
		Response res = RequestHelper.sendEvent(pc);
		if(!res.succeed()){
			Toast.makeText(this, "sorry, 修改密码失败!", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent();
		intent.setClass(PasswordChangeActivity.this, TimelineActivity.class);
		finish();
		startActivity(intent);
	}
}