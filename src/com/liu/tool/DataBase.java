package com.liu.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase {
	
	
	private final static class DatabaseHelper extends SQLiteOpenHelper {
		private static final int SCHEMA_VERSION = 1;
		private static final String createMessages = "CREATE TABLE IF NOT EXISTS messages("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, time TIMESTAMP, content TEXT, to VARCHAR(100) NOT NULL, type INT)";
	
        public DatabaseHelper(Context context) {
			super(context, Config.DATABASE_NAME, null, SCHEMA_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(createMessages);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("DB", "No update.");
		}
	}
}
