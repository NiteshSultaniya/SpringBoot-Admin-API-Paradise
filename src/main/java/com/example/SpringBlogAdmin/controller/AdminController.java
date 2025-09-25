package com.example.SpringBlogAdmin.controller;


import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@RequestBody AdminEntity adminEntity) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = adminService.login(adminEntity);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify() {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            mapdata.put("status", 200);
            mapdata.put("msg", "User is Authenticated");
            return ResponseEntity.ok(mapdata);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }
}
