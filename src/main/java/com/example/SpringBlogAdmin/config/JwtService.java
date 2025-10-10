package com.example.SpringBlogAdmin.config;


import com.example.SpringBlogAdmin.entity.AdminEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


@Service
public class JwtService {
    public SecretKey secretKey;
    private String STATIC_SECRET_KEY;
    private HttpServletRequest request;


    public JwtService(@Value("${STATIC_SECRET_KEY}") String STATIC_SECRET_KEY,HttpServletRequest request) {
        System.out.println(STATIC_SECRET_KEY);
        byte[] secreateKey = STATIC_SECRET_KEY.getBytes();

        this.secretKey = Keys.hmacShaKeyFor(secreateKey);
        this.request = request;
    }

    public String generateToken(AdminEntity user,Map<String, Object> claims) {

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername())
                .setId(user.getPassword())
                .setIssuer("DCB")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(secretKey)
                .compact();
    }

    //Claim Resolver
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Long   extractUserId() {
        final String authHeader=request.getHeader("Authorization");
        final String token=authHeader.substring(7);
        Object userId = extractClaims(token).get("userId");
        return userId != null ? Long.parseLong(userId.toString()) : null;

    }

    public String extractpassword(String token) {
        return extractClaims(token, Claims::getId);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey)  // Method used in 0.11.x
                .build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        final String password = extractpassword(token);
//        System.out.println(userName);
//        System.out.println(password);
//        return false
        return (userName.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


    private final Set<String> blacklistedTokens = Collections.synchronizedSet(new HashSet<>());
    public void blackListToken(String token) {
        blacklistedTokens.add(token);
    }
    public Boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

}
