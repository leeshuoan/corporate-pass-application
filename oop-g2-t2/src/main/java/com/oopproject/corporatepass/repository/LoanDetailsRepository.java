package com.oopproject.corporatepass.repository;

import org.springframework.stereotype.Repository;

import com.oopproject.corporatepass.model.LoanDetails;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoanDetailsRepository extends JpaRepository<LoanDetails, String> {
    ArrayList<LoanDetails> findByLoanIdEquals(int loanId);

    void deleteByCorporatePassIdAndLoanId(String corporatePassId, int loanId);

    int countByLoanId(int loanId);
}
