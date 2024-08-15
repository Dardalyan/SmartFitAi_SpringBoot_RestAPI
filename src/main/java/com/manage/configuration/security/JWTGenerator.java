package com.manage.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerator {

    // Generates JWT token
    public String generate(Authentication authentication){

        String username = authentication.getName(); // Holds as principal
        Date currentDate = new Date();
        Date expierDate = new Date(currentDate.getTime() + JWTConstants.JWT_EXPIRATION);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expierDate)
                .signWith(SignatureAlgorithm.HS512, JWTConstants.JWT_SECRET)
                .compact();
        return token;
    }

    // gets username from decoded token
    public String getUsernameByToken(String token){
        // Username can be either User.email or User.username
        Claims claims = Jwts.parser().setSigningKey(JWTConstants.JWT_SECRET)
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    // validates token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWTConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT expired or not valid !");
        }
    }

}
