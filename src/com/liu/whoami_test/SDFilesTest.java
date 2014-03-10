package com.liu.whoami_test;

import java.io.File;

import android.test.AndroidTestCase;

import com.liu.whoami.SDFiles;

public class SDFilesTest extends AndroidTestCase {
	public void test_sdEnabled() {
		assertTrue(SDFiles.isSDEnabled());
	}
	
	public void test_getSDDir() {
		assertEquals(new File("/mnt/sdcard"), SDFiles.getSDDir());
	}
	
	public void test_getDraftPath() {
		String expected = "/mnt/sdcard/whoami/draft.txt";
		assertEquals(expected, SDFiles.getDraftPath());
	}
}
