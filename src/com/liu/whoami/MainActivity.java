package com.liu.whoami;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.whoami_main);
		
		Button makeDraftBn = (Button) findViewById(R.id.make_draft);
		Button takeDraftBn = (Button) findViewById(R.id.take_draft);
		Button submitBn = (Button) findViewById(R.id.submit);
		final EditText tv = (EditText) findViewById(R.id.mailtext);
		final EditText mailTos = (EditText) findViewById(R.id.email);
		final EditText topicEt = (EditText) findViewById(R.id.topicET);
		makeDraftBn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View draftBn) {
				try {
					Log.v("MainActivity", "Make draft button clicked.");
					FileUtils.write(new File(SDFiles.getDraftPath()), tv.getText());
					clearETs(new EditText[] {mailTos, topicEt, tv});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}});
		
		takeDraftBn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				try {
					Log.v("MainActivity", "Take draft button clicked.");
					tv.setText(FileUtils.readFileToString(new File(SDFiles.getDraftPath())));
//					FileUtils.deleteQuietly(new File(SDFiles.getDraftPath()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		submitBn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!checkMail(mailTos, tv)) {
					Toast.makeText(MainActivity.this, "Mail uncompleted.", Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(MainActivity.this, "Mail sent successfully. ", Toast.LENGTH_SHORT).show();
//				MailSender.sendMail(
//						StringUtils.split(mailTos.getText().toString().trim()),
//						topicEt.getText().toString(), tv.getText().toString());
				clearETs(new EditText[] {mailTos, topicEt, tv});
			}

		});
	}

	private boolean checkMail(EditText mailTos, EditText tv) {
		String toStr = mailTos.getText().toString();
		String content = tv.getText().toString();
		return !(StringUtils.isEmpty(toStr) || StringUtils.isEmpty(content));
	}
	
	private void clearETs(EditText[] editTexts) {
		for (EditText et : editTexts)
			et.setText(Constants.EMPTY);
	}
}
