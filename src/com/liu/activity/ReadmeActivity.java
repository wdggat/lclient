package com.liu.activity;

import android.os.Bundle;
import android.view.View;

public class ReadmeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_readme);
	}
	
	public void backtoTimeline(View v) {
		this.finish();
	}
}
