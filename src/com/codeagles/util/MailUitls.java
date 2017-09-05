package com.codeagles.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送工具
 * 
 * @author Codeagles
 * 
 */
public class MailUitls {
	/**
	 * 发邮件方法
	 * 
	 * @param to
	 *            :收件人
	 * @param code
	 *            :激活码
	 */
	public static void sendMail(String to, String code) {
		/**
		 * 1.获得一个Session对象. 2.创建一个代表邮件的对象Message. 3.发送邮件Transport
		 */
		// 1.获得连接对象
		Properties props = new Properties();
		props.setProperty("mail.host", "localhost");
		//该Session 为javax.mail包下的类
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("service@codeagles.com", "111");
			}

		});
		// 2.创建邮件对象:
		Message message = new MimeMessage(session);
		// 设置发件人
		try {
			message.setFrom(new InternetAddress("service@codeagles.com"));
			// 设置收件人
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			// 抄送 CC 密送BCC
			// 设置标题
			message.setSubject("来自'唯之'商城官方激活邮件");
			// 设置邮件正文:
			message.setContent(
					"<h1>'唯之'商城官方激活邮件!点下面链接完成激活操作</h1><h3><a href='http://localhost:8080/SSHNetShop/user_active.action?code="
							+ code
							+ "'>http://192.168.0.106:8080/SSHNetShop/user_active.action?code="
							+ code + "</a></h3>", "text/html;charset=UTF-8");
			// 3.发送邮件:
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		sendMail("aaa@codeagles.com", "11111111111111");
	}
}
