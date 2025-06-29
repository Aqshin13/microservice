package com.microservice.email_service;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Value("app.mail.username")
    private String usernameForEmail;


    @Value("app.mail.password")
    private String passwordForEmail;

    private JavaMailSenderImpl mailSender;

    private String activationEmail = """
            <html>
                <body>
                    <h1>Account activation</h1>
                    <a href="${url}">Active the account</a>
                </body>
            </html>
            """;

    @PostConstruct
    public void sender() {
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername(usernameForEmail);
        mailSender.setPassword(passwordForEmail);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
    }

    public void sendActivationToken(String email, String activationToken) {
        var activationUrl = "http://localhost:5173/activation/" + activationToken;
        String mailBody = activationEmail
                .replace("${url}", activationUrl);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom("MSapp@mail.ru");
            message.setTo(email);
            message.setSubject("Activate your account");
            message.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        this.mailSender.send(mimeMessage);

    }
}
