package com.oopproject.corporatepass.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.LoanResponse;
import com.oopproject.corporatepass.model.Loan;
// import com.oopproject.corporatepass.controller.jwt.JwtTokenFilter;
import com.oopproject.corporatepass.service.GeneralOfficeService;

@RestController
@RequestMapping("/api/v1/gop")
@CrossOrigin(origins = "*")
public class GeneralOfficeController {

    // @Autowired
    // private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private GeneralOfficeService generalOfficeService;

    @PostMapping("/get-user-loan")
    public ResponseEntity<List<LoanResponse>> employee(@RequestBody Loan loan) {
        List<LoanResponse> loanResponse = generalOfficeService.getUserLoan(loan.getEmail(), loan.getDate());
        return ResponseEntity.ok().body(loanResponse);
    }

    @GetMapping("/returned-pass/{id}")
    public @ResponseBody String returned(@PathVariable(value = "id") String corporatePassId) {
        try {
            return generalOfficeService.passReturned(corporatePassId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/found-pass/{id}")
    public @ResponseBody String found(@PathVariable(value = "id") String corporatePassId) {
        try {
            return generalOfficeService.passFound(corporatePassId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/loan-pass/{id}")
    public @ResponseBody String loan(@PathVariable(value = "id") String corporatePassId, @RequestBody String email) {
        try {
            return generalOfficeService.passLoaned(corporatePassId, email);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/lost-pass/{id}")
    public @ResponseBody String lsot(@PathVariable(value = "id") String corporatePassId) {
        try {
            return generalOfficeService.passLost(corporatePassId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
