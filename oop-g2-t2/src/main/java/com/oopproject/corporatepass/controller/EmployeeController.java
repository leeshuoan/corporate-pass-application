package com.oopproject.corporatepass.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.LoanRequest;
import com.oopproject.corporatepass.controller.customClasses.ResponseHandler;
import com.oopproject.corporatepass.controller.jwt.JwtTokenUtil;
import com.oopproject.corporatepass.model.Loan;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.service.EmployeeService;
import com.oopproject.corporatepass.service.LoanService;

@RestController
@RequestMapping("/api/v1/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoanService loanService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @PostMapping("/loan")
    public ResponseEntity<Object> employee(@CookieValue("jwt") String token, @RequestBody LoanRequest loanRequest) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        Date date = new Date(loanRequest.getDate());

        String message = null;
        if (!loanService.isWithinDateRange(date, 1, 8 * 7)) { // 1 day to 8 weeks
            message = "Date is not within 1 day to 8 weeks prior to visit date";
            return ResponseHandler.generateObjectResponse(message, HttpStatus.BAD_REQUEST, message);
        }

        if (employeeService.isExceedLimit(email)) {
            message = "Exceeded limit";
            return ResponseHandler.generateObjectResponse(message, HttpStatus.BAD_REQUEST, message);
        }

        try {
            Loan loan = new Loan(date, email);
            loanService.saveLoanAndLoanDetails(loan, loanRequest.getAttractionId(), loanRequest.getNumPass());
            message = "Loan request successful";
            return ResponseHandler.generateObjectResponse(message, HttpStatus.OK, message);
        } catch (Exception e) {
            message = "Loan request failed unexpectedly";
            return ResponseHandler.generateObjectResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, message);
        }
    }

    @GetMapping("/numbookingsleft")
    public ResponseEntity<Object> numBookingsLeft(@CookieValue("jwt") String token) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        return ResponseHandler.generateIntResponse("Number of bookings left for this month", HttpStatus.OK,
                employeeService.numBookingsLeft(email));
    }

    @PostMapping("/cancel")
    public ResponseEntity<Object> cancel(@RequestParam int loanId) {
        try {
            if (employeeService.cancelLoan(loanId)) {
                return ResponseHandler.generateObjectResponse("Loan cancelled successful", HttpStatus.OK,
                        "Loan cancelled successful");
            }
            return ResponseHandler.generateObjectResponse("Loan cancelled failed", HttpStatus.BAD_REQUEST,
                    "Loan cancelled failed");
        } catch (UsernameNotFoundException e) {
            return ResponseHandler.generateObjectResponse(e.getMessage(), HttpStatus.BAD_REQUEST,
                    "Loan cancelled failed");
        }
    }

    @GetMapping("/numavailablepass")
    public ResponseEntity<Object> numAvailablePass(@RequestParam int attractionId, @RequestParam Date date) {
        int numAvailablePass = employeeService.numAvailablePass(attractionId, date);
        return ResponseHandler.generateIntResponse("Number of available passes", HttpStatus.OK, numAvailablePass);
    }

    @GetMapping("/loans")
    public ResponseEntity<Object> allLoans(@CookieValue("jwt") String token) {
        String email = jwtUtil.getEmailFromToken(token);
        List<Object> objectList = new ArrayList<Object>(employeeService.getLoansByEmail(email));
        return ResponseHandler.generateListResponse(null, HttpStatus.OK, objectList);
    }

    @GetMapping("/previousbookerinfo")
    public ResponseEntity<Object> previousBookerInfo(@RequestParam String corporatePassId, @RequestParam Date date) {
        User previousBooker = employeeService.previousBookerInfo(corporatePassId, date);
        if (previousBooker == null) {
            return ResponseHandler.generateObjectResponse("No user booked before your requested date", HttpStatus.OK, previousBooker);
        }
        return ResponseHandler.generateObjectResponse("Info of the user who borrowed before your requested date", HttpStatus.OK, previousBooker);
    }
}
