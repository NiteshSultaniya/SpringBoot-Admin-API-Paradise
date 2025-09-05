package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.UserEntity;
import com.example.SpringBlogAdmin.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> getalluser() {
        try {
            List<UserEntity> obj = userRepo.findAll();
            return obj;
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public String adduser(UserEntity user) {
        try {
            Boolean useremailexist = userRepo.findByEmail(user.getEmail());
            if (useremailexist) {
                return "Email already exists";
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(user);
                return "User added Succeffulyy";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Boolean statusUpdate(Long id) {
        try {
            UserEntity user = userRepo.findById(id).orElseThrow();
            Integer currentStatus = user.getStatus();
            Integer newStatus = (currentStatus == 1) ? 0 : 1;
            userRepo.updateStatusById(newStatus, id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
