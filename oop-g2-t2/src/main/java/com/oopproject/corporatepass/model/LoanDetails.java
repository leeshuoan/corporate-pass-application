package com.oopproject.corporatepass.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoanDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int loanId;
    private String corporatePassId;
    public LoanDetails() {
    }
    public LoanDetails(int loanId, String corporatePassId) {
        this.loanId = loanId;
        this.corporatePassId = corporatePassId;
    }
    public int getLoanId() {
        return loanId;
    }
    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }
    public String getCorporatePassId() {
        return this.corporatePassId;
    }
    public void setCorporatePassId(String corporatePassId) {
        this.corporatePassId = corporatePassId;
    }
}
