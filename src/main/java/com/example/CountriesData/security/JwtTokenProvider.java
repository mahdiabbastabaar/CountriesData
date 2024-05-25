package com.example.CountriesData.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;


import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationTime;


    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationTime);

        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(expireDate)
            .signWith(key())
            .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from JWT token
    public String getUsername(String token){

        return Jwts.parser()
            .verifyWith((SecretKey) key())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) throws Exception {
        try{
            Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
            return true;
        }catch (MalformedJwtException malformedJwtException){
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
            throw new Exception("Invalid JWT Token");
        }catch (ExpiredJwtException expiredJwtException){
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
            throw new Exception("Expired JWT token");
        }catch (UnsupportedJwtException unsupportedJwtException){
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
            throw new Exception("Unsupported JWT token");
        }catch (IllegalArgumentException illegalArgumentException){
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt claims string is null or empty");
            throw new Exception("Jwt claims string is null or empty");
        }
    }

}
