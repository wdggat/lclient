package com.liu.activity;

import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistActivity extends BaseActivity {
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
				if(validateRegist()) {
					boolean registResult = registServer();
				}
			}

		});
	}
	
	@SuppressLint("NewApi")
	private boolean validateRegist() {
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
		return true;
	}
	
	private boolean registServer() {
		// TODO
		return true;
	}
}
