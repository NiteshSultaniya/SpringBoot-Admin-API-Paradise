package com.example.SpringBlogAdmin.controller;


import com.example.SpringBlogAdmin.entity.UserEntity;
import com.example.SpringBlogAdmin.repo.UserRepo;
import com.example.SpringBlogAdmin.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;

    public UserController(UserService userService,UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping("/all-user")
    public ResponseEntity<?> getalluser() {
        Map<String, Object> data = new LinkedHashMap<>();
        List<UserEntity> obj = userService.getalluser();
        if (obj.isEmpty()) {
            data.put("status", "success");
            data.put("data", "data not found");
        } else {
            data.put("status", "success");
            data.put("data", obj);
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id)
    {
        Optional<UserEntity> obj = userService.findUserById(id);
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

    @PostMapping("/add-user-process")
    public ResponseEntity<?> adduser(@RequestBody UserEntity user) {
        Map<String,Object> obj = userService.adduser(user);
        return ResponseEntity.ok(obj);
    }


    @GetMapping("/user-status/{id}")
    public ResponseEntity<?> statusUpdate(@PathVariable Long id) {
//        System.out.println(id);
        Boolean obj = userService.statusUpdate(id);
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
    public ResponseEntity<?> deleteUser(@PathVariable Long id)
    {
            Map<String, Object> data = new LinkedHashMap<>();
        try {
           int obj= userRepo.deleteEntityById(id);
           if(obj>0)
           {
            data.put("status", "success");
            data.put("msg", "User Deleted Successfully");

           }else {
               data.put("status", "error");
               data.put("msg", "Something went Wrong");
           }
        }catch (Exception e)
        {
            data.put("status", "error");
            data.put("msg", "Something went Wrong");
        }
            return ResponseEntity.ok(data);

    }
}
