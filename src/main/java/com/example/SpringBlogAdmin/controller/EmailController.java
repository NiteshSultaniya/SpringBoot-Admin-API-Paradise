package com.example.SpringBlogAdmin.controller;

import com.example.SpringBlogAdmin.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class EmailController {
    public EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

//    @GetMapping("/send-email")
//    public ResponseEntity<?> sendEmail() {
//        Map<String, Object> obj = new HashMap<>();
//        try {
//            CompletableFuture<String> completableFuture = emailService.sendEmail("");
//            System.out.println(completableFuture.isDone());
//            System.out.println("first excussion");
//                obj.put("after send","msg send succefully");
//            return ResponseEntity.ok(obj);
//        } catch (Exception e) {
//            obj.put("error",e.getMessage());
//            return ResponseEntity.ok(obj);
//        }
//    }
}
