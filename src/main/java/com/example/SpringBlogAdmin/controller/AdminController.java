package com.example.SpringBlogAdmin.controller;


import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/admin")
    public ResponseEntity<?> getAdmin() {

        Map<String, Object> userData = new LinkedHashMap<>();
        List<AdminEntity> userDataa = adminService.getAdmin();
        if (userDataa.isEmpty()) {
            userData.put("status", "success");
            userData.put("userData", "Data Not Found");
        } else {
            userData.put("status", "success");
            userData.put("userData", userDataa);
        }
        return ResponseEntity.ok(userData);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@RequestBody AdminEntity adminEntity)
    {
        Map<String,Object> userData=new LinkedHashMap<>();
        String token= adminService.login(adminEntity);
        userData.put("status","Success");
        userData.put("token",token);
        return ResponseEntity.ok(userData);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify()
    {
        Map<String,Object> userData=new LinkedHashMap<>();
        userData.put("status",200);
        userData.put("msg","User is Authenticated");
        return ResponseEntity.ok(userData);
    }
}
