package com.example.SpringBlogAdmin.controller;

import com.example.SpringBlogAdmin.entity.EmailDTO;
import com.example.SpringBlogAdmin.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    private MailService mailService;
    public MailController(MailService mailService)
    {
        this.mailService=mailService;
    }
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody EmailDTO emailDTO)
    {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = mailService.sendMail(emailDTO);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }
}
