package com.liu.activity;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.liu.other.Config;
import com.liu.other.HttpClientVM;
import com.liu.other.RegistResponse;
import com.liu.other.Utils;

import android.annotation.SuppressLint;
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

public class RegistActivity extends BaseActivity {
	private String emailAddr = "";
	private int genderRadioId = 0;
	private String province = "";
	private long birthday = 0;
	private String phone = "";
	private String password = "";
	private String passwordConfirm = "";
	private HttpClientVM clientVM = HttpClientVM.getClientVM();
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
				if(validateRegist()) {
					boolean registResult = registServer();
					if(registResult) {
						//TODO
					}
				}
			}

		});
	}
	
	@SuppressLint("NewApi")
	private boolean validateRegist() {
		emailAddr = ((EditText)findViewById(R.id.mail)).getText().toString();
		genderRadioId = ((RadioGroup)findViewById(R.id.gender_group)).getCheckedRadioButtonId();
		province = ((Spinner)findViewById(R.id.province)).getSelectedItem().toString();
		birthday = ((DatePicker)findViewById(R.id.birthday)).getCalendarView().getDate();
		phone = ((EditText)findViewById(R.id.phone)).getText().toString();
		password = ((EditText)findViewById(R.id.password)).getText().toString();
		passwordConfirm = ((EditText)findViewById(R.id.password)).getText().toString();
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
		return true;
	}
	
	private boolean registServer() {
		Map<String, String> infos = new HashMap<String, String>();
		infos.put("email", emailAddr);
		infos.put("province", province);
		infos.put("birthday", birthday + "");
		infos.put("phone", phone);
		infos.put("password", password);
		infos.put("gender", getGender(genderRadioId) + "");
		//{"code":200, "content":"successful."}
		String response = clientVM.post(Config.server, infos);
		if(response == null) {
			Log.d("REGIST", "Posting regist info failed.");
			Toast.makeText(RegistActivity.this, "Posting regist info error, network may has problem.", Toast.LENGTH_SHORT).show();
			return false;
		}
		RegistResponse res = JSON.parseObject(response, RegistResponse.class);
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
			case R.id.genderbt_male: return Config.MALE;
			case R.id.genderbt_female: return Config.FEMALE;
			default: return Config.GENDER_UNSET;
		}
	}
	
	/*private boolean passwordEncrypt(String password) {
		char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		 RandomUtils.nextInt();
	}*/
}
