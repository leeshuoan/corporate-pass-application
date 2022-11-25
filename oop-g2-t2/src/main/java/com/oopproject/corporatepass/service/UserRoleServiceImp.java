package com.oopproject.corporatepass.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopproject.corporatepass.model.Role;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.model.UserRole;
import com.oopproject.corporatepass.repository.RoleRepository;
import com.oopproject.corporatepass.repository.UserRoleRepository;

@Service
public class UserRoleServiceImp implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
    
    @Override 
    public ArrayList<User> getAllUserWithRole(List<User> users) {
        for (User user: users){
            List<UserRole> userRoles = userRoleRepository.findByEmailEquals(user.getEmail());
            for (UserRole userRole: userRoles){
                Role role = roleRepository.findByIdEquals(userRole.getRoleId()).get();
                user.addRole(role);
            }
        }
        return (ArrayList<User>) users;
    }

    @Override
    public String updateUserRoles(String userEmail, String[] roles) {
        List<UserRole> userRoles = userRoleRepository.findByEmailEquals(userEmail);
        for (UserRole userRole: userRoles) {
            userRoleRepository.delete(userRole);
        }
        try {
            for (int i=0; i<roles.length; i++){
                int roleId = roleRepository.findIdByName(roles[i]).get(0).getId();
                UserRole userRole = new UserRole(userEmail, roleId);
                userRoleRepository.save(userRole);
            }
            return "user roles updated.";
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
