package com.oopproject.corporatepass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.ResponseHandler;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.service.AdminRoleService;
import com.oopproject.corporatepass.service.UserRoleService;
import com.oopproject.corporatepass.service.UserService;


@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;


    // batch delete/ single delete employees
    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteEmployees(@RequestBody String[] emails){
        try{
            adminRoleService.deleteEmployees(emails);
            return ResponseHandler.generateObjectResponse("Successfully deleted employee(s)",
            HttpStatus.OK, emails);
        }catch(IllegalStateException e){
            return ResponseHandler.generateObjectResponse(e.getMessage(),
            HttpStatus.BAD_REQUEST, e);
        }
    }

    @PutMapping(path = "/addAdmin/{email}")
    public String addAdmin(@PathVariable("email") String email) {
        try {
            adminRoleService.addAdmin(email);
            return "Successfully made employee with email: " + email + " an admin";
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping(path = "/updateEmployee")
    public ResponseEntity<Object> updateUser(@RequestBody User updateUserRoleRequest) {
        try {
            User updatedUser = userService.updateUserDetails(updateUserRoleRequest);
            String[] roles = updateUserRoleRequest.getRoles().stream().map(x -> x.getName()).toArray(String[]::new);
            userRoleService.updateUserRoles(updatedUser.getEmail(), roles);
            return ResponseHandler.generateObjectResponse("Successfully updated " + updatedUser.getUsername(), HttpStatus.OK, updatedUser);
        } catch (UsernameNotFoundException e) {
            return ResponseHandler.generateObjectResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PutMapping(path = "/banEmployee/{email}")
    public String banEmployee(@PathVariable("email") String email) {
        try {
            adminRoleService.banEmployee(email);
            return "Successfully banned employee with email: " + email;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PutMapping(path = "/unbanEmployee/{email}")
    public String unbanEmployee(@PathVariable("email") String email) {
        try {
            adminRoleService.unbanEmployee(email);
            return "Successfully unbanned employee with email: " + email;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    // @GetMapping("/montlyLoans/{month}")
    // public int monthlyLoans(@PathVariable int month) {
    //     System.out.println(month);
    //     adminRoleService.getMonthlyLoans(month);
    //     // userService.saveUser(user);
    //     // userRoleService.saveUserRole(new UserRole(user.getEmail(), user.getType()));
    //     return 0;
    // }
}
