package com.oopproject.corporatepass.service;

import java.sql.Date;

import com.oopproject.corporatepass.model.CorporatePass;
import com.oopproject.corporatepass.model.Loan;
import com.oopproject.corporatepass.model.LoanDetails;

public interface LoanService {
    public Loan saveLoan(Loan loan);
    public Loan deleteLoan(Loan loan);
    public LoanDetails saveLoanDetails(Loan loan, int attractionId, CorporatePass corporatePass);
    public void saveLoanAndLoanDetails(Loan loan, int attractionId, int numPass);
    public boolean isWithinDateRange(Date date, int start, int end);
    public String getPdfGenerate(Loan loan, int attractionId, CorporatePass corporatePass);
}
