package com.vary.gong.email;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class JavaxEmailSender implements EmailSender {

	private String smtpHost;

	public JavaxEmailSender(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	@Override
	public void sendMail(String sender, List<String> recipients, String subject, String body) throws Exception {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", smtpHost);
		Session session = Session.getDefaultInstance(properties);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sender));

		for (String recipient: recipients) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		}

		message.setSubject(subject);
		message.setContent(body, "text/html; charset=utf-8");

		Transport.send(message);
	}
}
