package com.liu.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import android.test.AndroidTestCase;

import com.liu.message.DataType;
import com.liu.message.Message;

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
}
