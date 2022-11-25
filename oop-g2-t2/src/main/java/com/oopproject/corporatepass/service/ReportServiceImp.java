package com.oopproject.corporatepass.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopproject.corporatepass.repository.LoanRepository;
import com.oopproject.corporatepass.util.DateUtility;

@Service
public class ReportServiceImp implements ReportService {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public int getNumLoansByMonth(String year, String month){
        long firstMilliOfMonth = DateUtility.getFirstMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        long lastMilliOfMonth = DateUtility.getLastMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        Date startDate = new Date(firstMilliOfMonth);
        Date endDate = new Date(lastMilliOfMonth);

        return loanRepository.countByDateBetween(startDate, endDate);
    }

    @Override
    public ArrayList<Integer> getNumLoansByYear(String year) {
        int currentMonth = DateUtility.getCurrentMonth();
        ArrayList<Integer> numLoansPerMonthArray = new ArrayList<Integer>();
        for (int i = 1; i <= currentMonth; i++) {
            numLoansPerMonthArray.add(getNumLoansByMonth(year, ""+i));
        }
        return numLoansPerMonthArray;
    }

    @Override
    public int getNumBorrowersByMonth(String year, String month) {
        long firstMilliOfMonth = DateUtility.getFirstMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        long lastMilliOfMonth = DateUtility.getLastMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        Date startDate = new Date(firstMilliOfMonth);
        Date endDate = new Date(lastMilliOfMonth);

        return loanRepository.getNumBorrowersByMonth(startDate, endDate);
    }

    @Override
    public ArrayList<Integer> getNumBorrowersByYear(String year) {
        // get current month
        int currentMonth = DateUtility.getCurrentMonth();
        ArrayList<Integer> numBorrowersPerMonthArray = new ArrayList<Integer>();
        for (int i = 1; i <= currentMonth; i++) {
            numBorrowersPerMonthArray.add(getNumBorrowersByMonth(year, ""+i));
        }
        return numBorrowersPerMonthArray;
    }

    @Override
    public int getNumLoansByEmployee(String year, String month, String email) {
        long firstMilliOfMonth = DateUtility.getFirstMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        long lastMilliOfMonth = DateUtility.getLastMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        Date startDate = new Date(firstMilliOfMonth);
        Date endDate = new Date(lastMilliOfMonth);

        return loanRepository.countByDateBetweenAndEmail(startDate, endDate, email);
    }

    @Override
    public ArrayList<Integer> getTotalYearlyLoansByEmployee(String year, String email) {
        int currentMonth = DateUtility.getCurrentMonth();
        ArrayList<Integer> totalLoans = new ArrayList<Integer>();
        for (int i = 1; i <= currentMonth; i++) {
            totalLoans.add(getNumLoansByEmployee(year, ""+i, email));
        }
        return totalLoans;
    }

    @Override
    public ArrayList<Integer> getTotalMonthlyLoansByEmployee(String year, String month, String email) {
        ArrayList<Integer> totalMonthlyLoans = new ArrayList<Integer>();

        long firstMilliOfMonth = DateUtility.getFirstMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        long lastMilliOfMonth = DateUtility.getLastMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));

        long currentMilliofMonth = firstMilliOfMonth;

        while (currentMilliofMonth <= lastMilliOfMonth) {
            Date date = new Date(currentMilliofMonth);
            totalMonthlyLoans.add(loanRepository.countByDateBetweenAndEmail(date, date, email));
            currentMilliofMonth += 86400000;
        }
        return totalMonthlyLoans;
    }
}
