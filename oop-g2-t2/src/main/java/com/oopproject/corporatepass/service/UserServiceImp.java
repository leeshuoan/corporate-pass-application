package com.oopproject.corporatepass.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.model.UserRole;
import com.oopproject.corporatepass.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        User savedUser = saveUser(user);
        ArrayList<Integer> types = user.getType();
        for (Integer type : types) {
            userRoleService.saveUserRole(new UserRole(user.getEmail(), type));
        }

        try {
            TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
            KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(totp.getAlgorithm());
            int macLengthInBytes = Mac.getInstance(totp.getAlgorithm()).getMacLength();
            keyGenerator.init(macLengthInBytes * 8);
            SecretKey key = keyGenerator.generateKey();
            Instant now = Instant.now();
            int otp = totp.generateOneTimePassword(key, now);
            user.setVerificationCode(otp);
            user.setEnabled(false);
            userRepository.save(user);
            sendVerificationEmail(savedUser, otp);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println(e);
        }
    }

    @Override
    public void sendVerificationEmail(User user, int otp)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "g2t2oop@gmail.com";
        String senderName = "OOP G2T2";
        String subject = "Corporate Pass Application OTP";
        String content = "Dear [[name]],<br><br>"
                + "To authenticate, please use the following One Time Password (OTP)<br>"
                + "<h3>[[OTP]]</h3>"
                + "Don't share this OTP with anyone. We will never ask you for your password or OTP.<br><br>"
                + "Thank you,<br>"
                + "Singapore Sports School";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String otpString = String.format("%06d", otp);
        content = content.replace("[[name]]", user.getName());
        content = content.replace("[[OTP]]", otpString);

        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public User saveUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String UPPERCASE_ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) {
            int index = rnd.nextInt(UPPERCASE_ALPHANUMERIC_CHARS.length());
            salt.append(UPPERCASE_ALPHANUMERIC_CHARS.charAt(index));
        }
        user.setSalt(salt.toString());
        String encodedPassword = passwordEncoder.encode(user.getPassword() + user.getSalt());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsById(email);
    }

    @Override
    public User updateUser(User user) {
        if (userExists(user.getEmail())) {
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    @Transactional
    public User updateUserDetails(User user) {
        if (userExists(user.getEmail())) {
            User userToUpdate = userRepository.findById(user.getEmail()).get();
            userRepository.updateUserDetails(user.getEmail(), user.getUsername(), user.getName(), user.getContact());
            return userRepository.save(userToUpdate);
        }
        throw new UsernameNotFoundException("No such user");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findById(email).get();
    }

    @Override
    public boolean verifyOtp(String email, int otp) {
        User user = userRepository.findById(email).get();
        if (user.getVerificationCode() == otp) {
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
