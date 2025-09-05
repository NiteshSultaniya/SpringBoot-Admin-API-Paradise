package com.example.SpringBlogAdmin.config;


import com.example.SpringBlogAdmin.entity.AdminEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtService {

    //    Token Expiration Check
    public SecretKey secretKey;
    private String STATIC_SECRET_KEY;

    public JwtService(@Value("${STATIC_SECRET_KEY}") String STATIC_SECRET_KEY) {
        System.out.println(STATIC_SECRET_KEY);
        byte[] secreateKey = STATIC_SECRET_KEY.getBytes();

        this.secretKey = Keys.hmacShaKeyFor(secreateKey);
    }

    public String generateToken(AdminEntity user) {
        Map<String, Object> claim = new HashMap();
//        System.out.println(user.getUsername());
        return Jwts.builder().setClaims(claim)
                .setSubject(user.getUsername())
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
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
