package com.zezame.timasi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.sql.Timestamp;
import java.util.HashMap;

public class JwtUtil {
    public static String generateToken(String id, Timestamp timestamp) {
        var claims = new HashMap<String, Object>();
        claims.put("id", id);
        claims.put("exp", timestamp);

        var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("bWlC3cN9OjHq7GKJsXkSpcMvoJKi3t0fQxKbMMX+ayc="));

        var jwtBuilder = Jwts.builder()
                .signWith(secretKey)
                .setClaims(claims);

        return jwtBuilder.compact();
    }

    public static Claims validateToken(String token) {
        var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("bWlC3cN9OjHq7GKJsXkSpcMvoJKi3t0fQxKbMMX+ayc="));
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("EXPIRED_TOKEN");
        } catch (JwtException je) {
            throw new RuntimeException("INVALID_TOKEN");
        }
    }
}
