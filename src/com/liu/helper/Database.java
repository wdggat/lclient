package com.liu.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.liu.message.DataType;
import com.liu.message.Message;

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
	
	public boolean selfDestroy(final Context context) {
        synchronized (Database.class) {
            database = null;
            return context.deleteDatabase(Config.DATABASE_NAME);
        }
    }
	
	private SQLiteDatabase db;
	
    public void beginTransaction() {
        db.beginTransaction();
    }

    public void commitTransaction() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }
    
    public List<Message> readAllMessages() {
    	List<Message> messages = new ArrayList<Message>();
    	final String sql = "select sender,receiver,subject,time,content,type from messages;";
    	Cursor cursor = db.rawQuery(sql, null);
    	while(cursor.moveToNext()) {
    		String from = cursor.getString(0);
    		String to = cursor.getString(1);
    		String subject = cursor.getString(2);
    		long time = cursor.getLong(3);
    		String content = cursor.getString(4);
    		int type = cursor.getInt(5);
    		Message message = new Message(from, to, subject, time, content, DataType.getByValue(type));
    		messages.add(message);
    	}
    	return messages;
    }
    
    public void insertMessage(Message message) {
    	final String sql = "INSERT INTO messages(subject, time, content, sender, receiver, type) VALUES (?, ?, ?, ?, ?, ?);";
    	db.execSQL(sql, new Object[]{message.getSubject(), message.getTime(), message.getContent(), message.getFrom(), message.getTo(), message.getDataType().getValue()});
    }
    
    public void insertSingleMessage(Message message) {
    	beginTransaction();
    	insertMessage(message);
    	commitTransaction();
    	endTransaction();
    }
	
	private final static class DatabaseHelper extends SQLiteOpenHelper {
		private static final int SCHEMA_VERSION = 1;
		private static final String createMessages = "CREATE TABLE IF NOT EXISTS messages("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, subject VARCHAR(200), time TIMESTAMP, content TEXT, sender VARCHAR(100) NOT NULL, receiver VARCHAR(100) NOT NULL, type INT)";
	
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
