package com.example.demo.security.util;

import com.example.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private final String jwtSecret = "jansdjn190dsak-ndkasjnd!";
    private final String jwtIssuer = "student-service.uz";

    public String generateToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setId(String.valueOf(user.getId()));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setSubject(String.format("%s %s", user.getPhone(), user.getPassword()));
        jwtBuilder.signWith(SignatureAlgorithm.ES256, jwtSecret);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (12*60*60*1000)));
        jwtBuilder.setIssuer(jwtIssuer);
        return jwtBuilder.compact();
    }

    public String generateToken(Long id, String phone){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setId(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setSubject(String.format("%s %s", id, phone));
        jwtBuilder.signWith(SignatureAlgorithm.ES256, jwtSecret);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (12*60*60*1000)));
        jwtBuilder.setIssuer(jwtIssuer);
        return jwtBuilder.compact();
    }

    public String getPhone(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(" ")[1];
    }

    public String getId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(" ")[0];
    }

    public boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
