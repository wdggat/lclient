package com.liu.activity;

import android.test.AndroidTestCase;

import com.liu.bean.DataType;
import com.liu.bean.Message;
import com.liu.bean.TimelineListItem;

public class TimelineListItemTest extends AndroidTestCase {
	public void testToString() {
//		return associate + "    " + sdf.format(new Date((long)time * 1000)) + "\n" +  msgGlimpse;
		Message msg = new Message("user", "subject", 1400000000, "content", DataType.NEW_MSG, true);
		TimelineListItem item = TimelineListItem.fromMsg(msg);
		String expected = "user    2014-05-13 16:53:20\ncontent";
		assertEquals(expected, item.toString());
	}
}
