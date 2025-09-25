package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.config.CustomUserDetailsService;
import com.example.SpringBlogAdmin.config.JwtService;
import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.repo.AdminRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    public AdminRepo adminRepo;
    public JwtService jwtService;
    public PasswordEncoder passwordEncoder;
    public AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;


    public AdminService(AdminRepo adminRepo,CustomUserDetailsService customUserDetailsService,JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.adminRepo = adminRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }


    public Map<String,Object> login(AdminEntity user) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {

            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

            if(authentication.isAuthenticated()){
                UserDetails userDetails =customUserDetailsService.loadUserByUsername(user.getUsername());
                AdminEntity admindata=adminRepo.findByUsername(user.getUsername());
                Map<String, Object> claims = new HashMap<>();
                claims.put("role", admindata.getRole());
                String token=jwtService.generateToken(user, claims);
                mapdata.put("role",admindata.getRole());
                mapdata.put("token",token);
                mapdata.put("status","Success");
//                    return token;
                return mapdata;

            }else {
                mapdata.put("status", 401);
                mapdata.put("msg", "Bad Credential");
                return mapdata;
            }

        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;        }
    }

}
