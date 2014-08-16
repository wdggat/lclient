package com.liu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.liu.depends.WanDouJia;
import com.liu.helper.Config;
import com.liu.message.User;

public class MeActivity extends BaseActivity {
	private static final String TAG = "ME";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_me);
		
		User me = Config.getMe();
		TextView uidTV = (TextView)findViewById(R.id.me_uid);
		uidTV.setText(me.getUid());
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
		
		ViewGroup bannerContainer = (ViewGroup) this.findViewById(R.id.banner_ad_container);
		WanDouJia.showBanner(MeActivity.this, bannerContainer);
		
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
