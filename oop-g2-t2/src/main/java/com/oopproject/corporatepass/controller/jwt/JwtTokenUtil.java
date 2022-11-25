package com.oopproject.corporatepass.controller.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.oopproject.corporatepass.model.Role;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.repository.RoleRepository;
import com.oopproject.corporatepass.repository.UserRoleRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 365 * 24 * 60 * 60 * 1000; // 1 year
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(User user) {
        userRoleRepository.findByEmailEquals(user.getEmail()).forEach(userRole -> {
            Role role = roleRepository.findByIdEquals(userRole.getRoleId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.addRole(role);
        });
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getUsername(), user.getEmail()))
                .setIssuer("CodeJava")
                .claim("roles", user.getRoles().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        Claims claims = parseClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String[] jwtSubject = subject.split(",");
        return jwtSubject[1];
    }

    public String[] getUserRoles(String token) {
        Claims claims = parseClaims(token);
        String roles = (String) claims.get("roles");

        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");
        return roleNames;
    }
}
