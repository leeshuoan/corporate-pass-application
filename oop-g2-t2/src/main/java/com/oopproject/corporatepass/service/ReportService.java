package com.oopproject.corporatepass.service;

import java.util.ArrayList;

public interface ReportService {
  public int getNumLoansByMonth(String year, String month);
  public ArrayList<Integer> getNumLoansByYear(String year);
  public int getNumBorrowersByMonth(String year, String month);
  public ArrayList<Integer> getNumBorrowersByYear(String year);
  public int getNumLoansByEmployee (String year, String month, String email);
  public ArrayList<Integer> getTotalYearlyLoansByEmployee(String year, String email);
  public ArrayList<Integer> getTotalMonthlyLoansByEmployee(String year, String month, String email);
}
