package com.gdghongik.springsecurity.global.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(CustomUserDetails userDetails) {
        //비밀키- >보통은 환경변수로 다룸
        //  secret: asdfasdf - 절대 깃허브에 올리지 않기(위조 가능성)
        //  expiration: 3600000 (단위:ms, 1시간)

        //발행 정보
        Date now = new Date();
        //만료 정보
        Date expiredAt = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(userDetails.getMemberId().toString())
                .claim("username",  userDetails.getUsername())
                .claim("role",  userDetails.getRole().name())
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(getSecretKey())
                .compact(); //완료


    }
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

