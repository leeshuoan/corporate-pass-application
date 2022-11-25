package com.oopproject.corporatepass.controller.customClasses;

import java.sql.Date;

public class EmpLoanResponse {
    private String email;
    private Date loanDate;
    private String attractionName;
    private int numLoanDetails;
    private int loanId;
    private String previousBookerEmail;
    private String previousBookerName;
    
    public String getPreviousBookerEmail() {
        return previousBookerEmail;
    }

    public void setPreviousBookerEmail(String previousBookerEmail) {
        this.previousBookerEmail = previousBookerEmail;
    }

    public String getPreviousBookerName() {
        return previousBookerName;
    }

    public void setPreviousBookerName(String previousBookerName) {
        this.previousBookerName = previousBookerName;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getNumLoanDetails() {
        return numLoanDetails;
    }

    public void setNumLoanDetails(int numLoanDetails) {
        this.numLoanDetails = numLoanDetails;
    }

    public EmpLoanResponse(String email, Date loanDate, int numLoanDetails, String attractionName, int loanId, String previousBookerEmail, String previousBookerName) {
        this.email = email;
        this.loanDate = loanDate;
        this.numLoanDetails = numLoanDetails;
        this.attractionName = attractionName;
        this.loanId = loanId;
        this.previousBookerEmail = previousBookerEmail;
        this.previousBookerName = previousBookerName;
    }

    public EmpLoanResponse(String email, Date loanDate, int numLoanDetails, String attractionName, int loanId) {
        this.email = email;
        this.loanDate = loanDate;
        this.numLoanDetails = numLoanDetails;
        this.attractionName = attractionName;
        this.loanId = loanId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

}
