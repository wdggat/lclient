package com.liu.tool;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.EditText;

import com.liu.activity.R;

public class Utils {

	// <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />  
    public static boolean isNetWorkAvailable(Context context) {    
         ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
         NetworkInfo info = cm.getActiveNetworkInfo();    
        return info != null && info.isConnected();    
    }   
    
    public static boolean isSDEnabled() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
    
/*    public static String getEditTextString(Activity activity, int rid) {
    	return ((EditText)activity.findViewById(R.id.mail)).getText().toString();
    }*/
}
