package com.oopproject.corporatepass.service;

import java.sql.Date;
import java.util.ArrayList;

import com.oopproject.corporatepass.controller.customClasses.EmpLoanResponse;
import com.oopproject.corporatepass.model.User;

public interface EmployeeService {
    public User getEmployee(String email);

    public boolean isExceedLimit(String email);

    public boolean cancelLoan(String corporatePassId, int loanId);

    public boolean cancelLoan(int loanId);

    public int numBookingsLeft(String email);

    public int numAvailablePass(int attractionId, Date date);
    public User previousBookerInfo(String corporatePassId, Date date);

    public ArrayList<EmpLoanResponse> getLoansByEmail(String email);
}
