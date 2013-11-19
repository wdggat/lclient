package com.liu.whoami;

import java.io.File;

import android.os.Environment;

public class SDFiles {
	
	public static boolean isSDEnabled() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	public static final File getSDDir() {
		return Environment.getExternalStorageDirectory();
	}
	
	public static String getDraftPath() {
	    return getSDDir().getAbsolutePath() + "/" + Constants.user_dir + "/" + Constants.draft_path;
	}
}
