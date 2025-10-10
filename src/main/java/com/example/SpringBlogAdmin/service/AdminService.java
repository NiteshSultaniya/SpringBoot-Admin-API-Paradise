package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.config.CustomUserDetailsService;
import com.example.SpringBlogAdmin.config.JwtService;
import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.entity.RoleEntity;
import com.example.SpringBlogAdmin.repo.AdminRepo;
import com.example.SpringBlogAdmin.repo.RoleRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@Service
public class AdminService {
    public AdminRepo adminRepo;
    public RoleRepo roleRepo;
    public JwtService jwtService;
    public PasswordEncoder passwordEncoder;
    public AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final Supplier<Long> idGenerator;

    public AdminService(AdminRepo adminRepo,RoleRepo roleRepo, CustomUserDetailsService customUserDetailsService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, Supplier<Long> idGenerator) {
        this.adminRepo = adminRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.idGenerator = idGenerator;
        this.roleRepo = roleRepo;
    }


    public Map<String, Object> getalluser() {
        Map<String, Object> data = new LinkedHashMap<>();

        try {
            List<AdminEntity> obj = adminRepo.findAll();
            List<RoleEntity> roledata = roleRepo.activeStatusData();
            if (obj.isEmpty()) {
                data.put("status", "success");
                data.put("data", "data not found");
            } else {
                data.put("status", "success");
                data.put("data", obj);
            }
            data.put("roleData",roledata);
            return data;
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
            data.put("data", e.getMessage());
            return data;
        }
    }

    public Optional<AdminEntity> findUserById(Long id) {
        try {
            Optional<AdminEntity> obj = adminRepo.findById(id);
            return obj;
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
            return Optional.empty();
        }
    }


    public Boolean statusUpdate(Long id) {
        try {
            AdminEntity user = adminRepo.findById(id).orElseThrow();
            Integer currentStatus = user.getStatus();
            Integer newStatus = (currentStatus == 1) ? 0 : 1;
            adminRepo.updateStatusById(newStatus, id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Map<String, Object> createUser(AdminEntity user) {
        Map<String, Object> data = new LinkedHashMap<>();
        System.out.println(user.getId());
        try {
           Optional<RoleEntity> roleData=roleRepo.findById(user.getRoleId());
            if (user.getId() > 0 && user.getId() != null) {
                Optional<AdminEntity> existingUserOpt = adminRepo.findById(user.getId());
                if (existingUserOpt.isEmpty()) {
                    data.put("status", 404);
                    data.put("msg", "User not found");
                    return data;
                }
                AdminEntity existingUser = existingUserOpt.get();
                if (!existingUser.getEmail().equals(user.getEmail())) {
                    Boolean emailAlreadyExists = adminRepo.findByEmail(user.getEmail());
                    if (emailAlreadyExists) {
                        data.put("status", 201);
                        data.put("msg", "Email already exists");
                        return data;
                    }
                }
                if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
                } else {
                    user.setPassword(existingUser.getPassword());
                }
                if(roleData.isPresent())
                {
                    user.setRoleName(roleData.get().getRoleName());
                }
                adminRepo.save(user);
                data.put("status", 200);
                data.put("msg", "Data Updated Successfully");
                return data;
            } else {
                Boolean useremailexist = adminRepo.findByEmail(user.getEmail().trim());
                if (useremailexist) {
                    data.put("status", 201);
                    data.put("msg", "Email already exists");
                    return data;
                } else {
                    user.setId(idGenerator.get());
                    user.setEmail(user.getEmail().trim());
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setRoleId(user.getRoleId());
                    if(roleData.isPresent())
                    {
                    user.setRoleName(roleData.get().getRoleName());

                    }else{
                        user.setRoleName("GUEST");
                    }
                    adminRepo.save(user);
                    data.put("status", 200);
                    data.put("msg", "User added Succeffulyy");
                    return data;
                }
            }
        } catch (Exception e) {
            data.put("status", 400);
            data.put("msg", e.getMessage());
            return data;
        }
    }


    public Map<String, Object> userDelete(Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            AdminEntity cat = adminRepo.findById(id).orElseThrow();
            int rowEffected = adminRepo.deleteEntityById(id);
            if (rowEffected > 0) {
                mapdata.put("status", 200);
                mapdata.put("msg", "User Deleted Successfully");
                return mapdata;
            } else {
                mapdata.put("status", 201);
                mapdata.put("msg", "Something Went Wrong");
                return mapdata;
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

    public Map<String, Object> login(AdminEntity user) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
                AdminEntity admindata = adminRepo.findByUsername(user.getUsername());
                Map<String, Object> claims = new HashMap<>();
                System.out.print(admindata.getId());
                claims.put("roleId", admindata.getRoleId());
                claims.put("roleName", admindata.getRoleName());
                claims.put("userId", admindata.getId());
                String token = jwtService.generateToken(admindata, claims);
                mapdata.put("roleName", admindata.getRoleName());
                mapdata.put("token", token);
                mapdata.put("status", "Success");
                return mapdata;
            } else {
                mapdata.put("status", 401);
                mapdata.put("msg", "Bad Credential");
                return mapdata;
            }

        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

}
