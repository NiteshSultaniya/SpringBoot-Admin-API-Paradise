package com.example.SpringBlogAdmin.service;
import com.example.SpringBlogAdmin.entity.EmailDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private EmailDTO emailDTO;

    public EmailService(JavaMailSender mailSender,EmailDTO emailDTO) {
        this.mailSender = mailSender;
        this.emailDTO = emailDTO;
    }

    @Async // <-- Runs in separate thread
    public void sendEmail(EmailDTO emailDTO) {
        System.out.println("Thread Name: " + Thread.currentThread().getName());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDTO.getTo());
        message.setFrom("rahul456@gmail.com");
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getMessage());
        mailSender.send(message);
        System.out.println("Email sent to " + emailDTO.getTo());
    }
}
