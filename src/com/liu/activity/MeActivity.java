package com.liu.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.liu.bean.User;
import com.liu.tool.Utils;

public class MeActivity extends BaseActivity {
	private static final String TAG = "ME";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_me);
		
		User me = Utils.getME(MeActivity.this);
		TextView mailTV = (TextView)findViewById(R.id.me_mail);
		mailTV.setText(me.getEmailAddr());
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
				backtoTimeline(v);
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
	
}
