package com.example.SpringBlogAdmin.controller;


import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody AdminEntity adminEntity) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = adminService.createUser(adminEntity);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }


    @GetMapping("/all-user")
    public ResponseEntity<?> getalluser() {
        Map<String, Object> obj = adminService.getalluser();
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<AdminEntity> obj = adminService.findUserById(id);
        Map<String, Object> data = new LinkedHashMap<>();
        if (obj.isEmpty()) {
            data.put("status", "success");
            data.put("data", "data not found");
        } else {
            data.put("status", "success");
            data.put("data", obj);
        }
        return ResponseEntity.ok(data);
    }


    @GetMapping("/user-status/{id}")
    public ResponseEntity<?> statusUpdate(@PathVariable Long id) {
//        System.out.println(id);
        Boolean obj = adminService.statusUpdate(id);
        Map<String, Object> data = new LinkedHashMap<>();
        if (obj) {
            data.put("status", "success");
            data.put("msg", "User Status Updated Successfully");
        } else {
            data.put("status", "error");
            data.put("msg", "Something went Wrong");
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user-delete/{id}")
    public ResponseEntity<?> userDelete(@PathVariable("id") Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            Map<String, Object> obj = adminService.userDelete(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
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

    @PostMapping("/register")
    public ResponseEntity<?> registerController(@RequestBody AdminEntity adminEntity) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = adminService.createUser(adminEntity);
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
