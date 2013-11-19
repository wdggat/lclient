package com.liu.whoami.mail;

import com.liu.whoami.mail.MultiMailSender.MultiMailSenderInfo;

public class MailSender {

    private static String sendFrom = "monitort@163.com";
    private static String passwd = "neteasemonitort";

/*    private static Properties getSmtpSettings() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.163.com");
        properties.setProperty("mail.smtp.port", "25");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.auth", "true");
        return properties;
    }*/
    
	public static Boolean sendMail(final String[] mailtos, final String topic, final String content) {
		new Thread(new Runnable() {

			@Override
			public void run() {
//		System.out.println("MailTos: " + ArrayUtils.toString(mailtos) + ", topic: " + topic + ", content: " + content);
				MultiMailSenderInfo mailInfo = new MultiMailSenderInfo(); 
				mailInfo.setMailServerHost("smtp.163.com"); 
				mailInfo.setMailServerPort("25"); 
				mailInfo.setValidate(true); 
				mailInfo.setUserName(sendFrom); 
				mailInfo.setPassword(passwd);
				mailInfo.setFromAddress(sendFrom); 
				mailInfo.setSubject(topic); 
				mailInfo.setContent(content);
				mailInfo.setToAddress(mailtos); 
				MultiMailSender sms = new MultiMailSender(); 
				sms.sendTextMail(mailInfo);//发送文体格式  
			}
			
		}).start();
		return true;
	}
}
