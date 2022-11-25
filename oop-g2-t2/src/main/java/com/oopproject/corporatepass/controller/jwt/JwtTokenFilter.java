package com.oopproject.corporatepass.controller.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.oopproject.corporatepass.model.Role;
import com.oopproject.corporatepass.model.User;

import io.jsonwebtoken.Claims;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getAccessTokenFromCookie(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtUtil.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContextFromCookie(token, request);
        filterChain.doFilter(request, response);
    }

    private String getAccessTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        return cookie != null ? cookie.getValue() : null;
    }

    private void setAuthenticationContextFromCookie(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        User userDetails = new User();
        Claims claims = jwtUtil.parseClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String[] roleNames = jwtUtil.getUserRoles(token);

        for (String aRoleName : roleNames) {
            userDetails.addRole(new Role(aRoleName.trim()));
        }

        String[] jwtSubject = subject.split(",");

        userDetails.setUsername(jwtSubject[0]);
        userDetails.setEmail(jwtSubject[1]);

        return userDetails;
    }
}