package com.liu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.liu.helper.Config;
import com.liu.helper.Utils;
import com.liu.message.User;

public class MeActivity extends BaseActivity {
	private static final String TAG = "ME";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_me);
		
		User me = Config.getMe();
		TextView mailTV = (TextView)findViewById(R.id.me_mail);
		mailTV.setText(me.getEmail());
		TextView genderTV = (TextView)findViewById(R.id.me_gender);
		genderTV.setText(me.showGender());
		TextView phoneTV = (TextView)findViewById(R.id.me_phone);
		phoneTV.setText(me.getPhone());
		TextView birthdayTV = (TextView)findViewById(R.id.me_birthday);
		birthdayTV.setText(me.showBirthday());
		TextView provinceTV = (TextView)findViewById(R.id.me_province);
		provinceTV.setText(me.getProvince());
		
		Button backToTimelineBt = (Button)findViewById(R.id.back_bt);
		backToTimelineBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MeActivity.this.finish();
			}
			
		});
		
		/* Change info not allowed for now.
		Button changeInfoBt = (Button)findViewById(R.id.me_change_infobt);
		changeInfoBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO
			}
			
		});*/
	}
	
	public void passwordChange(View v) {
		Log.d(TAG, "Begin to change password.");
		Intent intent = new Intent();
		intent.setClass(MeActivity.this, PasswordChangeActivity.class);
		startActivity(intent);
	}
	
}
