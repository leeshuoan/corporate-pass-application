package com.oopproject.corporatepass.repository;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oopproject.corporatepass.model.CorporatePass;

@Repository
public interface CorporatePassRepository extends JpaRepository<CorporatePass, String> {
    Optional<CorporatePass> findByCorporatePassIdEquals(String corporatePassId);
    int countByStatusAndAttractionIdEquals(String status, int atttractionId);
    int countByAttractionIdEquals(int atttractionId);
    ArrayList<CorporatePass> findFirstByStatusEqualsAndAttractionIdEquals(String status, int attractionId);
    ArrayList<CorporatePass> findByStatusEqualsAndAttractionIdEquals(String status, int attractionId);
    Optional<CorporatePass> findByEmailEquals(String email);
    // get all corporate passes
    @Query(value = "SELECT * FROM corporate_pass", nativeQuery = true)
    List<CorporatePass> getAllCorporatePasses();


    @Query
    (value = "SELECT * FROM corporate_pass WHERE status = ?1", nativeQuery = true)
    List<CorporatePass> findEmailOfLoanedPasses(String status);

    @Query
    (value = "SELECT COUNT(cp.attraction_id) FROM corporate_pass cp join loan_details ld on cp.corporate_pass_id = ld.corporate_pass_id join loan l on ld.loan_id = l.loan_id where l.date = ?1 and cp.attraction_id = ?2", nativeQuery = true)
    int countAvailablePassByDateAndAttractionId(Date date, int attractionId);

    @Query(
        value = "SELECT cp.corporate_pass_id, cp.status, cp.attraction_id, cp.email from loan l join loan_details ld on l.loan_id = ld.loan_id join corporate_pass cp on cp.corporate_pass_id = ld.corporate_pass_id where l.email = ?1 and l.date = ?2",
        nativeQuery = true)
    ArrayList<CorporatePass> findCorporatePassByEmailAndDate(String email, Date loanDate);
}
