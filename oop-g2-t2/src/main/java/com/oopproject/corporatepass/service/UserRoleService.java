package com.oopproject.corporatepass.service;

import java.util.*;

import com.oopproject.corporatepass.model.UserRole;
import com.oopproject.corporatepass.model.User;

public interface UserRoleService {
    public UserRole saveUserRole(UserRole userRole);
    public ArrayList<User> getAllUserWithRole(List<User> users);
    public String updateUserRoles(String userEmail, String[] roles);
}
