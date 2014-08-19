package com.liu.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import android.test.AndroidTestCase;

import com.alibaba.fastjson.JSON;
import com.liu.message.DataType;
import com.liu.message.Message;
import com.liu.message.TimelineListItem;

public class TimelineActivityTest extends AndroidTestCase {
	String from = "hzliuxiaolong@163.com";
	String fromUid = "test_uid";
	String to = "shliuxiaolong@163.com";
	String to2 = "lxl_awesome@163.com";
	public void testGroupMessages() {
		Message m1 = new Message(from, fromUid, to, "test_subject", 1400520549, "test_content", DataType.NEW_MSG, 1400520549);
		Message m2 = new Message(from, fromUid, to, "test_subject", 1400520549, "test_content", DataType.NEW_MSG, 1400520549);
		Message m3 = new Message(from, fromUid, to2, "test_subject", 1400520549, "test_content", DataType.NEW_MSG, 1400520549);
		Message m4 = new Message(from, fromUid, to, "test_subject", 1400520549, "test_content", DataType.NEW_MSG, 1400520549);
		TreeSet<Message> msgs = new TreeSet<Message>();
		msgs.add(m1);
		msgs.add(m2);
		msgs.add(m4);
		TreeSet<Message> msgs2 = new TreeSet<Message>();
		msgs2.add(m3);
		
		List<Message> msgs3 = new ArrayList<Message>();
		msgs3.add(m1);
		msgs3.add(m2);
		msgs3.add(m4);
		msgs3.add(m3);
		
		TreeMap<String, TreeSet<Message>> expected = new TreeMap<String, TreeSet<Message>>();
		expected.put("wdggat@163.com", msgs);
		expected.put("hzliuxiaolong@163.com", msgs2);
		
		Map<String, TreeSet<Message>> actual = TimelineActivity.groupMessage(msgs3);
		assertEquals(expected, actual);
	}
	
/*	public void testGetListItems() {
//		D/TIMELINE( 1766): READ MSG: 10001 - {"content":"hello","dataType":"REPLY","formatedTime":"2014-08-18 18:19:08","from":"hzliuxiaolong@126.com","fromUid":"10002","localTime":0,"subject":"","time":1408385948,"to":"10001","toEmail":false,"validMessage":true}
//		D/TIMELINE( 1766): READ MSG: hzliuxiaolong@163.com - {"content":"hello","dataType":"REPLY","formatedTime":"2014-08-19 11:19:47","from":"hzliuxiaolong@163.com","fromUid":"10001","localTime":0,"subject":"","time":1408447187,"to":"10002","toEmail":false,"validMessage":true}
		TreeSet<TimelineListItem> expected = new TreeSet<TimelineListItem>();
		TimelineListItem item1 = new TimelineListItem("10001", 1408385948, "hello", 0);
		TimelineListItem item2 = new TimelineListItem("hzliuxiaolong@163.com", 1408447187, "hello", 0);
		expected.add(item1);
		expected.add(item2);
		Message msg1 = JSON.parseObject("{\"content\":\"hello\",\"dataType\":\"REPLY\",\"formatedTime\":\"2014-08-18 18:19:08\",\"from\":\"hzliuxiaolong@126.com\",\"fromUid\":\"10002\",\"localTime\":0,\"subject\":\"\",\"time\":1408385948,\"to\":\"10001\",\"toEmail\":false,\"validMessage\":true}", Message.class);
		Message msg2 = JSON.parseObject("{\"content\":\"hello\",\"dataType\":\"REPLY\",\"formatedTime\":\"2014-08-19 11:19:47\",\"from\":\"hzliuxiaolong@163.com\",\"fromUid\":\"10001\",\"localTime\":0,\"subject\":\"\",\"time\":1408447187,\"to\":\"10002\",\"toEmail\":false,\"validMessage\":true}", Message.class);
		TreeSet tree1 = new TreeSet();
		tree1.add(msg1);
		TreeSet tree2 = new TreeSet();
		tree1.add(msg2);
		TreeMap<String, TreeSet<Message>> allMessages = new TreeMap<String, TreeSet<Message>>();
		allMessages.put("10001", tree1);
		allMessages.put("hzliuxiaolong@163.com", tree2);
		TreeSet<TimelineListItem> actual = TimelineActivity.getListItems(allMessages);
		assertEquals(expected, actual);
	}*/
}
