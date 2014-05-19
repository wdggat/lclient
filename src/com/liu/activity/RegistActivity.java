package com.liu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liu.bean.DataType;
import com.liu.bean.Response;
import com.liu.bean.User;
import com.liu.tool.Config;
import com.liu.tool.RequestHelper;
import com.liu.tool.Utils;

public class RegistActivity extends BaseActivity {
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_regist);
		
		Button backtoIndexBt = (Button)findViewById(R.id.back_bt);
		backtoIndexBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setContentView(R.layout.layout_index);
			}
			
		});
		
		Button registBt = (Button)findViewById(R.id.registbt);
		registBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!Utils.isNetWorkAvailable(RegistActivity.this)){
					Toast.makeText(RegistActivity.this, "Network unavailable.", Toast.LENGTH_SHORT).show();
					return;
				}
				if(checkParams()) {
					boolean registResult = registServer();
					if(registResult) {
						cacheUserInfo(user);
						setContentView(R.layout.layout_timeline);
					}
				}
			}

		});
	}
	
	@SuppressLint("NewApi")
	private boolean checkParams() {
		String emailAddr = ((EditText)findViewById(R.id.mail)).getText().toString();
		int genderRadioId = ((RadioGroup)findViewById(R.id.gender_group)).getCheckedRadioButtonId();
		String province = ((Spinner)findViewById(R.id.province)).getSelectedItem().toString();
		long birthday = ((DatePicker)findViewById(R.id.birthday)).getCalendarView().getDate();
		String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
		String password = ((EditText)findViewById(R.id.password)).getText().toString();
		String passwordConfirm = ((EditText)findViewById(R.id.password)).getText().toString();
		if(emailAddr.isEmpty()) {
			Toast.makeText(RegistActivity.this, "Email empty.", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(phone.isEmpty()) {
			Toast.makeText(RegistActivity.this, "Phone empty.", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(password.isEmpty() || password.length() < 6 || password.length() > 18) {
			Toast.makeText(RegistActivity.this, "Password's length should be larger than 5.", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!password.equals(passwordConfirm)) {
			Toast.makeText(RegistActivity.this, "Password comfirms wrong.", Toast.LENGTH_SHORT).show();
			return false;
		}
		user = new User(emailAddr, getGender(genderRadioId), province, birthday, phone, password);
		return true;
	}
	
	private boolean registServer() {
		//{"code":200, "content":"successful."}
		String response = RequestHelper.sendData(DataType.REGIST, user.toJson());
		if(response == null) {
			Log.d("REGIST", "Posting regist info failed.");
			Toast.makeText(RegistActivity.this, "Posting regist info error, network may has problem.", Toast.LENGTH_SHORT).show();
			return false;
		}
		Response res = JSON.parseObject(response, Response.class);
		if(!res.succeed()) {
			Log.d("REGIST", "Regist failed: " + res.getContent());
			Toast.makeText(RegistActivity.this, "Failed: " + res.getContent(), Toast.LENGTH_SHORT).show();
			return false;
		}
		Log.d("REGIST", "Regist successfully.");
		Toast.makeText(RegistActivity.this, "Succeed.", Toast.LENGTH_SHORT).show();
		return true;
	}
	
	private int getGender(int genderRadioId) {
		switch(genderRadioId){
			case R.id.genderbt_male: return User.MALE;
			case R.id.genderbt_female: return User.FEMALE;
			default: return User.GENDER_UNSET;
		}
	}
	
	private boolean cacheUserInfo(User user) {
		Editor sp = RegistActivity.this.getSharedPreferences(Config.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).edit();
		sp.putString("uinfo", user.toJson());
		return sp.commit();
	}
	
	/*private boolean passwordEncrypt(String password) {
		char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		 RandomUtils.nextInt();
	}*/
}
