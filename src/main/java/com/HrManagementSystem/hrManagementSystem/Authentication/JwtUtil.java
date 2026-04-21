package com.HrManagementSystem.hrManagementSystem.Authentication;

import com.HrManagementSystem.hrManagementSystem.Entity.Employee;
import com.HrManagementSystem.hrManagementSystem.Entity.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private Employee emp;
    private final String SECRET = "myAuthenticationSuperKey12345678901233";
    private Key getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
    public String generateToken(Employee emp){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", emp.getRole().getName());
        claims.put("permissions",
                emp.getRole()
                        .getPermissions()
                        .stream()
                        .map(Permission::getName)
                        .toList());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(emp.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }
//    public boolean isTokenValid(String token,String username){
//        return extractUsername(token).equals(username) && !isTokenExpired(token);
//
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractAllClaims(token).getExpiration().before(new Date());
//    }


    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
