package Main;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	private static String myEmailAccount = "1015817997@qq.com";
	private static String myEmailPsw = "btlemfpaczgwbdeb";
	//QQSMTP服务器地址为:smtp.qq.com
	private static String myEmailSMTPHost = "smtp.qq.com";
	
	private static String receiveMailAccount;
	
	private static Properties props = new Properties();
	
	private static Session session;
	
	private static MimeMessage message;
	
	public SendMail(String receiveMailAccount,String YZM) throws Exception {
		this.receiveMailAccount = receiveMailAccount;
		
		props.setProperty("mail.transport.protocol", "smtp");//使用协议(javaMail规范要求)
		props.setProperty("mail.smtp.host", myEmailSMTPHost);//发件人的邮箱的SMTP服务器地址
		props.setProperty("mail.smtp.auth", "true");//需要请求认证	
		
		session = Session.getInstance(props);
		session.setDebug(false);      //可以设置为debug模式，可以查看详细的发送Log
		
		//3.创建一封邮件
		message = createMimeMessage(session, myEmailAccount, receiveMailAccount, YZM);
		
		
	}
	
	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String YZM) throws Exception {
	    //  创建一封邮件
	    MimeMessage message = new MimeMessage(session);

	    //  From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）	
	    message.setFrom(new InternetAddress(sendMail, "杭州师范大学寝室管理系统", "UTF-8"));

	    //  To: 收件人（可以增加多个收件人、抄送、密送）
	    message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "(｡･∀･)ﾉﾞ嗨SIRI", "UTF-8"));
	    
	    //  Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
	    message.setSubject("绑定邮箱", "UTF-8");

	    //  Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
	    message.setContent("尊敬的管理员用户:<br/>   当您看到这封邮箱时,现在时间为:"+new Date()+"，您的邮箱绑定验证码为："+YZM, "text/html;charset=utf-8");
	    
	    // 设置发件时间
	    message.setSentDate(new Date());

	    // 保存设置
	    message.saveChanges();

	    return message;
	}
	
	public static void send() throws MessagingException {
		// 根据Session获取邮件传输对象
		Transport transport = session.getTransport();
				
		// 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
		transport.connect(myEmailAccount, myEmailPsw);
		        
		// 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
		 transport.sendMessage(message, message.getAllRecipients());
		 
		// 关闭连接
		transport.close();
	}
	
}

