package com.oopproject.corporatepass.controller.customClasses;

import java.sql.Date;

public class LoanResponse {
  private String email;
  private Date loanDate;
  private String corporatePassId;
  private String loanStatus;
  private String attractionName;

  public LoanResponse(String email, Date loanDate, String corporatePassId, String loanStatus, String attractionName) {
    this.email = email;
    this.loanDate = loanDate;
    this.corporatePassId = corporatePassId;
    this.attractionName = attractionName;
    this.loanStatus = loanStatus;
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

  public String getCorporatePassId() {
    return corporatePassId;
  }

  public void setCorporatePassId(String corporatePassId) {
    this.corporatePassId = corporatePassId;
  }

  public String getLoanedStatus() {
    return loanStatus;
  }

  public void setLoanedStatus(String loanStatus) {
    this.loanStatus = loanStatus;
  }

  public String getAttractionName() {
    return attractionName;
  }

  public void setAttractionName(String attractionName) {
    this.attractionName = attractionName;
  }

}
