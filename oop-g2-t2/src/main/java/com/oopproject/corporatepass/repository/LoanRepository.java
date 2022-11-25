package com.oopproject.corporatepass.repository;

import java.util.*;
import java.sql.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.oopproject.corporatepass.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {

    Optional<Loan> findByLoanIdEquals(int loanId);

    int countByDateBetweenAndEmail(Date startDate, Date endDate, String email);

    ArrayList<Loan> findByDateBetweenAndEmail(Date startDate, Date endDate, String email);

    int countByDateBetween(Date startDate, Date endDate);

    void deleteByLoanId(int loanId);

    ArrayList<Loan> findByDateEquals(Date date);

    @Query(value = "SELECT COUNT(DISTINCT email) from loan where date between ?1 and ?2", nativeQuery = true)
    int getNumBorrowersByMonth(Date startDate, Date endDate);

    @Query(value = "SELECT * from loan where email = ?1 and date >= ?2", nativeQuery = true)
    ArrayList<Loan> findLoansbyEmailAndDate(String email, Date loanDate);

    // join loan table with loan_details table and select all rows where
    // corporate_pass_id = ?1 and date is more than todays date
    @Query(value = "SELECT l.loan_id, l.date, l.email from loan l join loan_details ld on l.loan_id = ld.loan_id where ld.corporate_pass_id = ?1 and l.date > ?2", nativeQuery = true)
    ArrayList<Loan> findLoansbyCorporatePassIdAndDate(String corporatePassId, Date today);

    // get the date of the loans in descending order and get the first one
    
    @Query(
        value = "SELECT l.date from loan l join loan_details ld on l.loan_id = ld.loan_id where ld.corporate_pass_id = ?1 and l.email = ?2 ORDER BY l.date DESC LIMIT 1",
        nativeQuery = true)
    Date findDatebyCorporatePassId(String corporatePassId, String email);
}
