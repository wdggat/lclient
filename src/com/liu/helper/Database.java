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
	
	private static SQLiteDatabase db;
	
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
    	final String sql = "select sender,fromUid,receiver,subject,time,content,type,localTime from messages;";
    	Cursor cursor = db.rawQuery(sql, null);
    	while(cursor.moveToNext()) {
    		String from = cursor.getString(0);
    		String fromUid = cursor.getString(1);
    		String to = cursor.getString(2);
    		String subject = cursor.getString(3);
    		long time = cursor.getLong(4);
    		String content = cursor.getString(5);
    		int type = cursor.getInt(6);
    		long localTime = cursor.getLong(7);
    		Message message = new Message(from, fromUid, to, subject, time, content, DataType.getByValue(type), localTime);
    		messages.add(message);
    	}
    	return messages;
    }
    
    public void insertMessage(Message message) {
    	final String sql = "INSERT INTO messages(subject, time, content, sender, fromUid, receiver, type, localTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    	db.execSQL(sql, new Object[]{message.getSubject(), message.getTime(), message.getContent(), message.getFrom(), message.getFromUid(), message.getTo(), message.getDataType().getValue(), message.getLocalTime()});
    }
    
    public void insertSingleMessage(Message message) {
    	beginTransaction();
    	insertMessage(message);
    	commitTransaction();
    	endTransaction();
    }
    
    public void dropMessage(Message message) {
    	final String sql = "DELETE FROM messages where sender=? and fromUid=? and receiver=?;";
    	db.execSQL(sql, new Object[]{message.getFrom(), message.getFromUid(), message.getTo()});
    }
	
	private final static class DatabaseHelper extends SQLiteOpenHelper {
		private static final int SCHEMA_VERSION = 1;
		private static final String createMessages = "CREATE TABLE IF NOT EXISTS messages("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, subject VARCHAR(200), time TIMESTAMP, content TEXT, sender VARCHAR(100), fromUid VARCHAR(100), receiver VARCHAR(100) NOT NULL, type INT, localTime TIMESTAMP)";
	
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
