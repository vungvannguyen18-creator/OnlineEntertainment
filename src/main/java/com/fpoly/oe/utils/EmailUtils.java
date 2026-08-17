package com.fpoly.oe.utils;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtils {
    private static final String EMAIL = "your_email@gmail.com"; 
    private static final String PASSWORD = "your_app_password";

    public static void sendWelcomeEmail(String toEmail, String fullname) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Chào mừng đến với Online Entertainment", "UTF-8");
            
            String htmlContent = "<h3>Xin chào " + fullname + "!</h3>"
                    + "<p>Cảm ơn bạn đã đăng ký tài khoản tại hệ thống Online Entertainment.</p>"
                    + "<p>Chúc bạn có những giây phút giải trí tuyệt vời!</p>";
            
            message.setContent(htmlContent, "text/html; charset=UTF-8");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

