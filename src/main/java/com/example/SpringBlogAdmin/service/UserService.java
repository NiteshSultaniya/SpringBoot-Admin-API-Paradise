package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.UserEntity;
import com.example.SpringBlogAdmin.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;


@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final Supplier<Long> idGenerator;
    private final String UPLOAD_DIR = "src/main/resources/static/assets/";

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, Supplier<Long> idGenerator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.idGenerator = idGenerator;
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

    public Optional<UserEntity> findUserById(Long id) {
        try {
            Optional<UserEntity> obj = userRepo.findById(id);
            return obj;
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Map<String, Object> adduser(UserEntity user) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            if (user.getId() > 0 && user.getId() != null) {
                Optional<UserEntity> existingUserOpt = userRepo.findById(user.getId());
                if (existingUserOpt.isEmpty()) {
                    data.put("status", 404);
                    data.put("msg", "User not found");
                    return data;
                }
                UserEntity existingUser = existingUserOpt.get();
                if (!existingUser.getEmail().equals(user.getEmail())) {
                    Boolean emailAlreadyExists = userRepo.findByEmail(user.getEmail());
                    if (emailAlreadyExists) {
                        data.put("status", 201);
                        data.put("msg", "Email already exists");
                        return data;
                    }
                }
                if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
                }else{
                    user.setPassword(existingUser.getPassword());
                }
                userRepo.save(user);
                data.put("status", 200);
                data.put("msg", "Data Updated Successfully");
                return data;
            } else {
                Boolean useremailexist = userRepo.findByEmail(user.getEmail().trim());
                if (useremailexist) {
                    data.put("status", 201);
                    data.put("msg", "Email already exists");
                    return data;
                } else {
                    user.setId(idGenerator.get());
                    user.setEmail(user.getEmail().trim());
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userRepo.save(user);
                    data.put("status", 200);
                    data.put("msg", "User added Succeffulyy");
                    return data;
                }
            }
        } catch (Exception e) {
            data.put("status", 400);
            data.put("msg",e.getMessage());
            return data;
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
