package com.liu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.test.AndroidTestCase;

import com.liu.bean.DataType;
import com.liu.bean.Message;

public class TimelineActivityTest extends AndroidTestCase {
	public void testGroupMessages() {
		Message m1 = new Message("wdggat@163.com", "test_subject", 1400520549, "test_content", DataType.NEW_MSG, true);
		Message m2 = new Message("wdggat@163.com", "test_subject", 1400520549, "test_content", DataType.NEW_MSG, true);
		Message m3 = new Message("hzliuxiaolong@163.com", "test_subject", 1400520549, "test_content", DataType.NEW_MSG, true);
		Message m4 = new Message("wdggat@163.com", "test_subject", 1400520549, "test_content", DataType.NEW_MSG, true);
		List<Message> msgs = new ArrayList<Message>();
		msgs.add(m1);
		msgs.add(m2);
		msgs.add(m4);
		List<Message> msgs2 = new ArrayList<Message>();
		msgs2.add(m3);
		
		List<Message> msgs3 = new ArrayList<Message>();
		msgs3.add(m1);
		msgs3.add(m2);
		msgs3.add(m4);
		msgs3.add(m3);
		
		Map<String, List<Message>> expected = new HashMap<String, List<Message>>();
		expected.put("wdggat@163.com", msgs);
		expected.put("hzliuxiaolong@163.com", msgs2);
		
		Map<String, List<Message>> actual = TimelineActivity.groupMessage(msgs3);
		assertEquals(expected, actual);
	}
}
