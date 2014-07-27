package com.liu.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.liu.helper.Database;
import com.liu.message.DataType;
import com.liu.message.Message;

public class FillDBTestData extends Activity{
	
	private static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
		context = this;
	}
	
	public static void fillTimelineMsgs() {
		String from = "hzliuxiaolong@163.com";
		String fromUid = "TEST_UID_WHOAMI";
		String to = "shliuxiaolong@163.com";
		Message message_1 = new Message(from, to, "这是一个严肃的主题，额", 1400947200, "关于Bayesian Methods，信息论大神David MacKay于1992年发表在Neural Computation上两篇的文章：1) Bayesian Interpolation 2) A Practical Bayesian Framework for Backpropagation Networks 关于regularization,Occam's razor,evidence,model complexity有太多太多的insight，当年Mackay才25岁，哭了", DataType.NEW_MSG);
	    Message message_2 = new Message(to, from, "", 1400947210, "who the hell are you?", DataType.QUICK_MSG);
	    Message message_3 = new Message(to, from, "", 1400947220, "what the hell are you talking about?", DataType.QUICK_MSG);	
	    Message message_4 = new Message(from, to, "", 1400947310, "yeah, i'm ted", DataType.QUICK_MSG);
	    Message message_5 = new Message(to, from, "", 1400947410, "【for 循环为何可恨？】Java的闭包(Closure)特征最近成为了一个热门话题。一些精英正在起草一份议案，要在Java将来的版本中加入闭包特征。然而，提议中的闭包语法以及语言上的这种扩充受到了众多Java程序员的猛烈抨击。 不久前，出版过数十本编程 （分享自 @IT技术博客大学习） http://t.cn/RvUeu66", DataType.QUICK_MSG);
	    Message message_6 = new Message(from, to, "", 1400947520, "i just want to tell u this is a good app", DataType.QUICK_MSG);
	    Message message_7 = new Message(to, from, "", 1400947220, "...", DataType.QUICK_MSG);
	    
	    Message message_8 = new Message(from, to, "这是另一个严肃的主题, 额", 1400957220, "hello, again", DataType.QUICK_MSG);
	    Message message_9 = new Message(to, from, "", 1400957250, "为啥又联系俺?", DataType.QUICK_MSG);
	    Message message_10 = new Message(from, to, "", 1400957260, "只是因为无聊，呵呵", DataType.QUICK_MSG);
	    Message message_11 = new Message(to, from, "", 1400957270, "那也就呵呵了，goodbye", DataType.QUICK_MSG);
	    
	    to = "HEHEO_IWLSE_AIQEX_OWIFE_QLSEI";
	    Message message_12 = new Message(from, to, "", 1400967250, "是谁啊，你妹", DataType.REPLY);
	    Message message_13 = new Message(to, from, "", 1400967260, "哈哈，不是额，是你哥.", DataType.QUICK_MSG);
	    Message message_14 = new Message(from, to, "", 1400967350, "good, you have something to tell me?", DataType.QUICK_MSG);
	    
	    Database db = Database.getDatabase(context);
	    Message[] messages = new Message[]{message_1, message_2, message_3, message_4, message_5, message_6, message_7, message_8, message_9, message_10, message_11, message_12, message_13, message_14};
	    db.beginTransaction();
	    for(Message message : messages)
	    	db.insertMessage(message);
	    db.commitTransaction();
	    db.endTransaction();
	}
}
