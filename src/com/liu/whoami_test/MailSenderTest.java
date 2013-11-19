package com.liu.whoami_test;

import android.test.AndroidTestCase;

import com.liu.whoami.mail.MailSender;
import com.liu.whoami.mail.MultiMailSender;
import com.liu.whoami.mail.MultiMailSender.MultiMailSenderInfo;


public class MailSenderTest extends AndroidTestCase {
	public void test_sendmail() {
		String topic = "WhoAmI测试邮件";
		String content = "WhoAmI测试内容、\n龙门客栈";
		MailSender.sendMail(new String[]{"wdggat@163.com"}, topic, content);
		System.out.println("Succeeded.");
//		Log.i("AndroidTestCase", "Hello, Android unittest.");
	}
	
	public void test_2() {
	         //这个类主要是设置邮件
	      MultiMailSenderInfo mailInfo = new MultiMailSenderInfo(); 
	      mailInfo.setMailServerHost("smtp.163.com"); 
	      mailInfo.setMailServerPort("25"); 
	      mailInfo.setValidate(true); 
	      mailInfo.setUserName("monitort@163.com"); 
	      mailInfo.setPassword("neteasemonitort");//您的邮箱密码 
	      mailInfo.setFromAddress("monitort@163.com");
	      mailInfo.setToAddress(new String[]{"wdggat@163.com"}); 
	      mailInfo.setSubject("WhoAmi_testSubject"); 
	      mailInfo.setContent("WhoAmi_testContent");
	      String[] receivers = new String[]{"wdggat@163.com"}; 
	      String[] ccs = receivers; 
	      mailInfo.setReceivers(receivers); 
	      mailInfo.setCcs(ccs); 
	      //这个类主要来发送邮件 
	      MultiMailSender sms = new MultiMailSender(); 
	      sms.sendTextMail(mailInfo);//发送文体格式 
//	      MultiMailSender.sendHtmlMail(mailInfo);//发送html格式 
//	      MultiMailSender.sendMailtoMultiCC(mailInfo);//发送抄送 
	}
}
