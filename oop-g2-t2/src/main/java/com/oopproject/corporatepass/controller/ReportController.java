package com.oopproject.corporatepass.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.ReportMonthRequest;
import com.oopproject.corporatepass.service.ReportService;

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin(origins = "*")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/loan-by-month")
    public String loansByMonth(@RequestBody ReportMonthRequest recordMonthRequest) {
        return "" + reportService.getNumLoansByMonth(recordMonthRequest.getYear(), recordMonthRequest.getMonth());
    }

    @PostMapping("/loan-by-year")
    public ArrayList<Integer> loansByYear(@RequestBody ReportMonthRequest recordMonthRequest) {
        return reportService.getNumLoansByYear(recordMonthRequest.getYear());
    }

    @GetMapping("/borrowers-by-month")
    public String borrowersByMonth(@RequestBody ReportMonthRequest recordMonthRequest) {
        return "" + reportService.getNumBorrowersByMonth(recordMonthRequest.getYear(), recordMonthRequest.getMonth());
    }

    @PostMapping("/borrowers-by-year")
    public ArrayList<Integer> borrowersByYear(@RequestBody ReportMonthRequest recordMonthRequest) {
        return reportService.getNumBorrowersByYear(recordMonthRequest.getYear());
    }

    @PostMapping("/loans-by-employee")
    public String loansByEmployee(@RequestBody ReportMonthRequest reportMonthEmailRequest) {
        return "" + reportService.getNumLoansByEmployee(reportMonthEmailRequest.getYear(),
            reportMonthEmailRequest.getMonth(), reportMonthEmailRequest.getEmail());
    }

    @PostMapping("/total-yearly-loans-by-employee")
    public String totalYearlyLoansByEmployee(@RequestBody ReportMonthRequest reportMonthEmailRequest) {
        return "" + reportService.getTotalYearlyLoansByEmployee(reportMonthEmailRequest.getYear(), reportMonthEmailRequest.getEmail());
    }

    @PostMapping("/total-monthly-loans-by-employee")
    public String totalMonthlyLoansByEmployee(@RequestBody ReportMonthRequest reportMonthEmailRequest) {
        return "" + reportService.getTotalMonthlyLoansByEmployee(reportMonthEmailRequest.getYear(), 
        reportMonthEmailRequest.getMonth(), reportMonthEmailRequest.getEmail());
    }
}
