package com.liu.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.liu.bean.Message;

public class Database {
	private static Database database;
	
	private Database(Context context) {
		db = new DatabaseHelper(context).getWritableDatabase();
	}

	public static synchronized Database getDatabase(final Context context) {
		if (null == database) {
            database = new Database(context);
        }
        return database;
	}
	
	boolean selfDestroy(final Context context) {
        synchronized (Database.class) {
            database = null;
            return context.deleteDatabase(Config.DATABASE_NAME);
        }
    }
	
	private SQLiteDatabase db;
	
    void beginTransaction() {
        db.beginTransaction();
    }

    void commitTransaction() {
        db.setTransactionSuccessful();
    }

    void endTransaction() {
        db.endTransaction();
    }
    
    Map<String, List<Message>> readAllMessages() {
    	Map<String, List<Message>> messages = new HashMap<String, List<Message>>();
    	//TODO
    	return messages;
    }
	
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
