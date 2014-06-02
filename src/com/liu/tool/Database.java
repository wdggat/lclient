package com.liu.tool;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.liu.bean.DataType;
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
    	final String sql = "select associate,subject,time,content,type,sendbyme from messages;";
    	Cursor cursor = db.rawQuery(sql, null);
    	while(cursor.moveToNext()) {
    		String associate = cursor.getString(0);
    		String subject = cursor.getString(1);
    		long time = cursor.getLong(2);
    		String content = cursor.getString(3);
    		int type = cursor.getInt(4);
    		int sendbyme = cursor.getInt(5);
    		Message message = new Message(associate, subject, time, content, DataType.getByValue(type), BooleanUtils.toBoolean(sendbyme));
    		messages.add(message);
    	}
    	return messages;
    }
    
    public void insertMessage(Message message) {
    	final String sql = "INSERT INTO messages(subject, time, content, associate, type, sendbyme) VALUES (?, ?, ?, ?, ?, ?);";
    	db.execSQL(sql, new Object[]{message.getSubject(), message.getTime(), message.getContent(), message.getAssociate(), message.getDataType().getValue(), message.isSentByMe() ? 1 : 0});
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
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, subject VARCHAR(200), time TIMESTAMP, content TEXT, associate VARCHAR(100) NOT NULL, type INT, sendbyme INT)";
	
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
