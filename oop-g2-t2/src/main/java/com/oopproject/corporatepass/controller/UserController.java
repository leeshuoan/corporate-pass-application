package com.oopproject.corporatepass.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.OtpRequest;
import com.oopproject.corporatepass.controller.customClasses.ResponseHandler;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.service.UserRoleService;
import com.oopproject.corporatepass.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        if (userService.userExists(user.getEmail())){
            return ResponseHandler.generateListResponse("User already exists", HttpStatus.BAD_REQUEST, null);
        }

        try {
            userService.registerUser(user);
            return ResponseHandler.generateListResponse("Register success", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateListResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOtp(@RequestBody OtpRequest otpRequest){
        if (userService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp())) {
            return ResponseHandler.generateListResponse("verified OTP", HttpStatus.OK, null);
        }
        return ResponseHandler.generateListResponse("invalid OTP", HttpStatus.OK, null);
    }

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/all-with-role")
    public ArrayList<User> getAllUsersWithRole(){
        List<User> users = getAllUser();
        return userRoleService.getAllUserWithRole(users);
    }

    @GetMapping("/user")
    public User getUser() {
        return userService.getUserByEmail(null);
    }
}
