package com.oopproject.corporatepass.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oopproject.corporatepass.model.Attractions;
import com.oopproject.corporatepass.model.CorporatePass;
import com.oopproject.corporatepass.model.Loan;
import com.oopproject.corporatepass.model.LoanDetails;
import com.oopproject.corporatepass.repository.CorporatePassRepository;
import com.oopproject.corporatepass.repository.LoanDetailsRepository;
import com.oopproject.corporatepass.repository.LoanRepository;

@Component
public class PassScheduler {
    
    @Autowired
    private CorporatePassRepository corporatePassRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanDetailsRepository loanDetailsRepository;

    @Autowired
    private AttractionsService attractionsService;

    // @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 12 * * ?")
    public void electronicPassLoaned() {
        // get all loans
        Date today = new Date(System.currentTimeMillis());
        ArrayList<Loan> todayLoanList = loanRepository.findByDateEquals(today);
        
        for (Loan loan: todayLoanList) {
            int loanId = loan.getLoanId();
            ArrayList<LoanDetails> loanDetailsList = loanDetailsRepository.findByLoanIdEquals(loanId);

            for (LoanDetails loanDetails : loanDetailsList) {
                String corporatePassId = loanDetails.getCorporatePassId();
                Optional<CorporatePass> corporatePass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId);

                if (!(corporatePass == null)) {
                    int attractionId = corporatePass.get().getAttractionId();
                    String email = corporatePass.get().getEmail();
                    Attractions attraction = attractionsService.getAttractionById(attractionId);
                    String passType = attraction.getPassType();

                    if (passType.equals("electronic")) {
                        // generalOfficeService.passLoaned(corporatePassId, email);
                        CorporatePass corpPass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId).get();
                        corpPass.setStatus("loaned");
                        corpPass.setEmail(email);
                        corporatePassRepository.save(corpPass);
                    }
                } 
            }
        }
    }

    // @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 12 * * ?")
    public void electronicPassReturned() {
        // get all loans
        Date yesterday = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        ArrayList<Loan> yesterdayLoanList = loanRepository.findByDateEquals(yesterday);
        
        for (Loan loan: yesterdayLoanList) {
            int loanId = loan.getLoanId();
            ArrayList<LoanDetails> loanDetailsList = loanDetailsRepository.findByLoanIdEquals(loanId);

            for (LoanDetails loanDetails : loanDetailsList) {
                String corporatePassId = loanDetails.getCorporatePassId();
                Optional<CorporatePass> corporatePass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId);

                if (!(corporatePass == null)) {
                    int attractionId = corporatePass.get().getAttractionId();
                    Attractions attraction = attractionsService.getAttractionById(attractionId);
                    String passType = attraction.getPassType();

                    if (passType.equals("electronic")) {
                        // generalOfficeService.passReturned(corporatePassId);
                        CorporatePass corpPass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId).get();
                        corpPass.setStatus("available");
                        corpPass.setEmail("-");
                        corporatePassRepository.save(corpPass);
                    }
                }
            }
        }
    }
}
