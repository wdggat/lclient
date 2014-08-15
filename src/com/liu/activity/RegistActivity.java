package com.liu.activity;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.liu.helper.RequestHelper;
import com.liu.helper.Utils;
import com.liu.message.DataType;
import com.liu.message.Event;
import com.liu.message.Response;
import com.liu.message.User;

public class RegistActivity extends BaseActivity {
	private static final String TAG = "REGIST";
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_regist);
		
		Button backtoIndexBt = (Button)findViewById(R.id.back_bt);
		backtoIndexBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RegistActivity.this, IndexActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
		
		Button registBt = (Button)findViewById(R.id.submit_bt);
		registBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "submit button pressed.");
				if(!Utils.isNetWorkAvailable(RegistActivity.this)){
					Toast.makeText(RegistActivity.this, "Network unavailable.", Toast.LENGTH_SHORT).show();
					return;
				}
				new RegistAction().execute("");
			}

		});
	}
	
	@Override
	protected void onDestroy() {
		Log.i(TAG, "activity is destroying...");
		super.onDestroy();
	}
	
	@SuppressLint("NewApi")
	private boolean checkParams() {
		Message msg = handler.obtainMessage();
		String emailAddr = ((EditText)findViewById(R.id.mail)).getText().toString();
		int genderRadioId = ((RadioGroup)findViewById(R.id.gender_group)).getCheckedRadioButtonId();
		String province = ((Spinner)findViewById(R.id.provinces_spinner)).getSelectedItem().toString();
//		long birthday = ((DatePicker)findViewById(R.id.birthday)).getCalendarView().getDate() / 1000;
		DatePicker datePicker = (DatePicker)findViewById(R.id.birthday);
		Calendar c = Calendar.getInstance();
		c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 0, 0, 0);
		String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
		String password = ((EditText)findViewById(R.id.password)).getText().toString();
		String passwordConfirm = ((EditText)findViewById(R.id.password_confirm)).getText().toString();
		if(emailAddr.isEmpty()) {
			msg.what = 1;
			handler.sendMessage(msg);
			return false;
		}
		if(phone.isEmpty()) {
			msg.what = 2;
			handler.sendMessage(msg);
			return false;
		}
		if(password.isEmpty() || password.length() < 6 || password.length() > 20) {
			msg.what = 3;
			handler.sendMessage(msg);
			return false;
		}
		if(!password.equals(passwordConfirm)) {
			msg.what = 4;
			handler.sendMessage(msg);
			return false;
		}
		user = new User(emailAddr, getGender(genderRadioId), province, c.getTimeInMillis() / 1000, phone, password, "");
		return true;
	}
	
	private boolean registServer() {
		Message msg = handler.obtainMessage();
		Event registEvent = new Event(DataType.REGIST);
		registEvent.putEntry(Event.USER, user.toJson());
		Response res = RequestHelper.sendEvent(RegistActivity.this, registEvent);
		Log.i(TAG, "user_regist, " + user.toJson());
		if(res.networkUnreachable()) {
			Log.e("REGIST", "Posting regist info failed.");
			msg.what = 5;
			handler.sendMessage(msg);
			return false;
		}
		if(!res.succeed()) {
			Log.e("REGIST", "Regist failed: " + res.getContent());
			Bundle bundler = new Bundle();
			bundler.putString("content", res.getContent());
			msg.setData(bundler);
			msg.what = 6;
			handler.sendMessage(msg);
			return false;
		}
		user = User.fromJsonStr(res.getContent());
		return true;
	}
	
	private int getGender(int genderRadioId) {
		switch(genderRadioId){
			case R.id.genderbt_male: return User.MALE;
			case R.id.genderbt_female: return User.FEMALE;
			default: return User.GENDER_UNSET;
		}
	}
	
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch(message.what){
			case 1:
				Toast.makeText(RegistActivity.this, "Email empty.", Toast.LENGTH_SHORT).show();
				return;
			case 2:
				Toast.makeText(RegistActivity.this, "Phone empty.", Toast.LENGTH_SHORT).show();
				return;
			case 3:
				Toast.makeText(RegistActivity.this, "Password's length should be larger than 5.", Toast.LENGTH_SHORT).show();
				return;
			case 4:
				Toast.makeText(RegistActivity.this, "Password comfirms wrong.", Toast.LENGTH_SHORT).show();
				return;
			case 5:
				Toast.makeText(RegistActivity.this, "Posting regist info error, network may has problem.", Toast.LENGTH_LONG).show();
				return;
			case 6:
				Toast.makeText(RegistActivity.this, "Failed, " + message.getData().getString("content"), Toast.LENGTH_SHORT).show();
				return;
			case 7:
				Toast.makeText(RegistActivity.this, "Succeed, 为您分配的uid: " + user.getUid(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(RegistActivity.this, TimelineActivity.class);
				RegistActivity.this.finish();
				startActivity(intent);
			}
		}
	};
	
	private class RegistAction extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			if(checkParams()) {
				boolean registResult = registServer();
				if(registResult) {
					Log.i(TAG, "Regist successfully.");
					Utils.cacheUserInfo(RegistActivity.this, user);
					Log.i(TAG, "user info cached.");
					Message msg = handler.obtainMessage();
					msg.what = 7;
					handler.sendMessage(msg);
					return true;
				}
			}
			return false;
		}
		
	}
	
	/*private boolean passwordEncrypt(String password) {
		char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		 RandomUtils.nextInt();
	}*/
}
