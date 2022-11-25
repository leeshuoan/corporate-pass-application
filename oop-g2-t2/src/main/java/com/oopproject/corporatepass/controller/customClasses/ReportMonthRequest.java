package com.oopproject.corporatepass.controller.customClasses;

public class ReportMonthRequest {
  private String year;
  private String month;
  private String email;

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  public String getMonth() {
    return this.month;
  }

  public String getYear() {
    return this.year;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public void setYear(String year) {
    this.year = year;
  }
}
