package com.oopproject.corporatepass.service;

import java.sql.Date;
import java.util.ArrayList;

import com.oopproject.corporatepass.controller.customClasses.EmailRequest;
import com.oopproject.corporatepass.controller.customClasses.LoanResponse;

public interface GeneralOfficeService {
    public ArrayList<LoanResponse> getUserLoan(String email, Date date);

    public String sendCollectedMail(EmailRequest emailRequest);

    public String sendLostMail(EmailRequest emailRequest);

    public String passLoaned(String corporatePassId, String email);

    public String passReturned(String corporatePassId);

    public String passFound(String corporatePassId);

    public String passLost(String corporatePassId);
}
