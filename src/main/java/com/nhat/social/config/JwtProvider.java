package com.nhat.social.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {
    private static SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    public static String generateToken(Authentication authentication){
        return Jwts.builder()
                .issuer("Social")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()*86400000))
                .claim("email", authentication.getName())
                .signWith(secretKey).compact();
    }
    public static String getEmailFromToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();

        return claims.get("email").toString();

    }
}
