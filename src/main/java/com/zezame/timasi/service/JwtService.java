package com.zezame.timasi.service;

import com.zezame.timasi.exceptiohandler.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class JwtService {
    private final Key secretKey;
    private final long expireTime;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration-minutes}") long expireTime) {
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret));
        this.expireTime = expireTime;
    }

    public String generateToken(String id) {
        var claims = new HashMap<String, Object>();
        claims.put("id", id);
        claims.put("exp", Timestamp.valueOf(LocalDateTime.now().plusMinutes(expireTime)));

        var jwtBuilder = Jwts.builder()
                .signWith(secretKey)
                .setClaims(claims);

        return jwtBuilder.compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw new UnauthorizedException("EXPIRED_TOKEN");
        } catch (JwtException je) {
            throw new UnauthorizedException("INVALID_TOKEN");
        }
    }
}
