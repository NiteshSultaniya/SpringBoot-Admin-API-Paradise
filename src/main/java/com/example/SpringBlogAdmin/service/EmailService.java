package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.PaymentOrderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Payment;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Async("emailExecutor")
public class EmailService {

    public JavaMailSender javaMailSender;
    public SimpleMailMessage simpleMailMessage;
//    public LoadEmailTemplate simpleMailMessage;
//    public String loadEmailTemplate;

    public EmailService(JavaMailSender javaMailSender,SimpleMailMessage simpleMailMessage){
        this.javaMailSender=javaMailSender;
        this.simpleMailMessage=simpleMailMessage;
//        this.loadEmailTemplate=loadEmailTemplate;
    }

    public void sendEmail(Payment paymentDetails, Optional<PaymentOrderEntity> paymentOrderDetails ) throws JsonProcessingException {
        Map<String,Object> obj=new HashMap<>();

        obj.put("data", new ObjectMapper().readValue(paymentDetails.toString(), Map.class));
        try {
            String html = loadEmailTemplate();

            html = html.replace("{{customerName}}", paymentOrderDetails.get().getName());
            html = html.replace("{{orderId}}", paymentDetails.get("order_id"));
            html = html.replace("{{paymentId}}", paymentDetails.get("id"));
            html = html.replace("{{amount}}",String.valueOf((Integer) paymentDetails.get("amount") / 100)
            );
            html = html.replace("{{paymentMethod}}", paymentDetails.get("method"));
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo((String) paymentDetails.get("email"));
            helper.setSubject("Payment Successful");
            helper.setText(html, true);

            javaMailSender.send(message);
        }catch(Exception e){
            return;
        }
    }


    public String loadEmailTemplate() throws IOException {
        ClassPathResource resource=new ClassPathResource("templates/payment-success.html");
        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}
