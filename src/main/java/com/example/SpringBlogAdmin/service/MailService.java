package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.entity.EmailDTO;
import com.example.SpringBlogAdmin.entity.RoleEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailService {

    private EmailService emailService;
    public MailService(EmailService emailService)
    {
        this.emailService=emailService;
    }
    public Map<String, Object> sendMail(EmailDTO emailDTO) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
        emailService.sendEmail(emailDTO);
                data.put("status", "success");
                data.put("data", "Mail send successfully");
            return data;
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
            data.put("data", e.getMessage());
            return data;
        }
    }
}
