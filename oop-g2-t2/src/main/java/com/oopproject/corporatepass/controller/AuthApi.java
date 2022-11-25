package com.oopproject.corporatepass.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.AuthRequest;
import com.oopproject.corporatepass.controller.customClasses.ResponseHandler;
import com.oopproject.corporatepass.controller.jwt.JwtTokenFilter;
import com.oopproject.corporatepass.controller.jwt.JwtTokenUtil;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.repository.UserRepository;
import com.oopproject.corporatepass.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthApi {
    @Autowired
    AuthenticationManager authManager;

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    JwtTokenFilter jwtFilter;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse httpResponse) {
        try {
            // get user's salt
            User user1 = userRepository.findByEmailEquals(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String salt = user1.getSalt();
            boolean enabled = user1.getEnabled();

            if (!enabled){
                return ResponseHandler.generateObjectResponse("User has not verified their email", HttpStatus.UNAUTHORIZED, null);
            }
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()+salt));
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);

            // add cookie to response
            Cookie cookie = new Cookie("jwt", accessToken);

            cookie.setMaxAge(24 * 60 * 60); // expires in 1 day
            cookie.setHttpOnly(true); // can't be accessed by client-side script (prevent XSS)
            cookie.setPath("/"); // global cookie accessible every where
            httpResponse.addCookie(cookie);

            return ResponseHandler.generateListStringResponse(null, HttpStatus.OK, List.of(jwtUtil.getUserRoles(accessToken)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse httpResponse) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0); // expires now
        cookie.setHttpOnly(true); // can't be accessed by client-side script (prevent XSS)
        cookie.setPath("/"); // global cookie accessible every where
        httpResponse.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin page";
    }

    @GetMapping("/employee")
    public ResponseEntity<Object> employee(@CookieValue("jwt") String token) {
        String email = jwtUtil.getEmailFromToken(token);
        User emp = employeeService.getEmployee(email);
        return ResponseHandler.generateObjectResponse(emp.getName(), HttpStatus.OK, emp);
    }

    @GetMapping("/gop")
    public String gop() {
        return "gop page";
    }

    @GetMapping("userrole")
    public ResponseEntity<Object> userRole(@CookieValue("jwt") String token) {
        String[] rolesName = jwtUtil.getUserRoles(token);
        List<String> roles = List.of(rolesName);
        return ResponseHandler.generateListStringResponse(null, HttpStatus.OK, roles);
    }
}
