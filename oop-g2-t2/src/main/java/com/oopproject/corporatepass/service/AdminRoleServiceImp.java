package com.oopproject.corporatepass.service;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

import com.oopproject.corporatepass.model.Loan;
// import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.model.UserRole;
import com.oopproject.corporatepass.repository.LoanRepository;
import com.oopproject.corporatepass.repository.UserRepository;
import com.oopproject.corporatepass.repository.UserRoleRepository;
// import com.oopproject.corporatepass.service.EmployeeService;

@Service
public class AdminRoleServiceImp implements AdminRoleService{
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private EmployeeService employeeService;    

    @Override
    @Transactional
    public void addAdmin(String email){
        List<UserRole> userRoles = userRoleRepository.findByEmailEquals(email);
        if (userRoles.size() == 0){
            throw new IllegalStateException("Employee with email: " + email + " does not exist");
        }
        boolean isAdmin = false;
        for(UserRole u: userRoles){
            if (u.getRoleId() == 1){
                isAdmin = true;
            }
        }
        if (!isAdmin){
            UserRole makeUserAdmin = new UserRole(email, 1);
            userRoleRepository.save(makeUserAdmin);
        }
        else{
            throw new IllegalStateException("Employee is already an admin");
        }
    }
    // new method to account for batch delete as well
    @Override
    @Transactional
    public void deleteEmployees(String[] emails) {
        for (String email: emails){
            boolean exists = userRepository.existsById(email);
            if (!exists){
                throw new IllegalStateException("Employee with email: " + email + " does not exist");
            }
            userRepository.deleteById(email);
            userRoleRepository.deleteByEmail(email);

            Date currentDate = new Date(System.currentTimeMillis());
            ArrayList<Loan> loanList = loanRepository.findLoansbyEmailAndDate(email, currentDate);
            for (Loan aLoan: loanList) {
                int loanId = aLoan.getLoanId();
                if (!employeeService.cancelLoan(loanId)) {
                    throw new IllegalStateException("Employee with email: " + email + " does not have loans");
                }
            }
        }
    }
    // public void csvToEmployees(MultipartFile file, User user) {
    //     // validate file
    //     if (file.isEmpty()) {
    //         model.addAttribute("message", "Please select a CSV file to upload.");
    //         model.addAttribute("status", false);
    //     } else {

    @Override
    @Transactional
    public void banEmployee(String email) {
        boolean exists = userRepository.existsById(email);
        if (!exists){
            throw new IllegalStateException("Employee with email: " + email + " does not exist");
        }
        userRepository.updateUserIsBanned(email, 1);

        Date currentDate = new Date(System.currentTimeMillis());
        ArrayList<Loan> loanList = loanRepository.findLoansbyEmailAndDate(email, currentDate);
        for (Loan aLoan: loanList) {
            int loanId = aLoan.getLoanId();
            if (!employeeService.cancelLoan(loanId)) {
                throw new IllegalStateException("Employee with email: " + email + " does not have loans");
            }
        }
        
    }
    
    @Override
    @Transactional
    public void unbanEmployee(String email) {
        boolean exists = userRepository.existsById(email);
        if (!exists){
            throw new IllegalStateException("Employee with email: " + email + " does not exist");
        }
        userRepository.updateUserIsBanned(email, 0);
        
    }
    
    //         try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
    
    //             CsvToBean csvToBean = new CsvToBeanBuilder(reader)
    //                     .withType(Employee.class)
    //                     .withIgnoreLeadingWhiteSpace(true)
    //                     .build();
    
    //             List users = csvToBean.parse();
    //             employeeRepository.saveAll(users);
    
    //             model.addAttribute("users", users);
    //             model.addAttribute("status", true);
    
    //         } catch (Exception ex) {
    //             model.addAttribute("message", "An error occurred while processing the CSV file.");
    //             model.addAttribute("status", false);
    //         }
    //     }
    // }
    // @Override
    // public int getMonthlyLoans(int month) {
    //     System.out.println(month);
    //     loanRepository.findByMonthEquals(month);
    // return 0;
    // }
}
