package com.example.SpringBlogAdmin.config;

import com.example.SpringBlogAdmin.entity.AdminEntity;
import com.example.SpringBlogAdmin.repo.AdminRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepo adminRepo;
    public CustomUserDetailsService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity userdata = adminRepo.findByUsername(username);
        if (userdata == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new User(userdata.getUsername(), userdata.getPassword(), List.of(new SimpleGrantedAuthority(userdata.getRole())));
    }
}
