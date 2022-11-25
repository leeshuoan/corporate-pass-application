package com.oopproject.corporatepass.service;

// import org.springframework.web.multipart.MultipartFile;
// import com.oopproject.corporatepass.model.User;

public interface AdminRoleService {
    void addAdmin(String email);
    // int getMonthlyLoans(int month);
    
    // delete one/batch delete
    void deleteEmployees(String[] emails);
    
    void banEmployee(String email);

    void unbanEmployee(String email);

    // void csvToEmployees(MultipartFile file, User user);
}
