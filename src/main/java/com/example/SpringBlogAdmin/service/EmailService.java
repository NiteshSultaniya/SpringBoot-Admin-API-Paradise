package com.example.SpringBlogAdmin.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Async("emailExecutor")
public class EmailService {

    public JavaMailSender javaMailSender;
    public SimpleMailMessage simpleMailMessage;

    public EmailService(JavaMailSender javaMailSender,SimpleMailMessage simpleMailMessage){
        this.javaMailSender=javaMailSender;
        this.simpleMailMessage=simpleMailMessage;
    }


    public CompletableFuture<String> sendEmail()
    {
        Map<String,Object> obj=new HashMap<>();
        try {
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo("aimnikku07@gmail.com");
            simpleMailMessage.setSubject("subject");
            simpleMailMessage.setText("body");
            simpleMailMessage.setFrom("niteshsultaniya63@gmail.com");
            System.out.println("Sending email on thread: " + Thread.currentThread().getName());

            javaMailSender.send(simpleMailMessage);
            return CompletableFuture.completedFuture("suceess");
        }catch(Exception e){
            return CompletableFuture.completedFuture("error");
        }
    }
}
