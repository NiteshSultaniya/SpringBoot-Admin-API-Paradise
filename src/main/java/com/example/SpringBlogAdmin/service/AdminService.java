package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.config.JwtService;
import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.repo.AdminRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    public AdminRepo adminRepo;
    public JwtService jwtService;
    public PasswordEncoder passwordEncoder;
    public AuthenticationManager authenticationManager;

    public AdminService(AdminRepo adminRepo, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.adminRepo = adminRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public List<AdminEntity> getAdmin() {
        try {
            return adminRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching users", e);

        }
    }

    public String login(AdminEntity user) {
        try {

            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

            if(authentication.isAuthenticated()){
                return jwtService.generateToken(user);
//                    return token;
            }
            return "Bad Credential";

        } catch (Exception e) {
            throw new RuntimeException("Error fetching users", e);
        }
    }

    public Boolean verify(AdminEntity adminEntity)
    {
        try{

            return true;
        }catch (Exception e)
        {
            throw new RuntimeException("Error fetching users", e);

        }
    }
}
