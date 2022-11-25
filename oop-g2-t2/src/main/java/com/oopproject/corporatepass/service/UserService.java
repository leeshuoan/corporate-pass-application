package com.oopproject.corporatepass.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.oopproject.corporatepass.model.User;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUser();
    boolean userExists(String email);
    User updateUser(User user);
    User updateUserDetails(User user);
    User getUserByEmail(String email);
    void registerUser(User user) throws MessagingException, UnsupportedEncodingException;
    void sendVerificationEmail(User user, int otp) throws MessagingException, UnsupportedEncodingException;
    boolean verifyOtp(String email, int otp);
    
}
