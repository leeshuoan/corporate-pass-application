package com.oopproject.corporatepass.controller.customClasses;

public class OtpRequest {
  private int otp;
  private String email;

  public OtpRequest(int otp, String email) {
    this.otp = otp;
    this.email = email;
  }

  public int getOtp() {
    return otp;
  }

  public void setOtp(int otp) {
    this.otp = otp;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  
}
