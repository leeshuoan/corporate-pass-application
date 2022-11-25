package com.oopproject.corporatepass.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopproject.corporatepass.controller.customClasses.EmailRequest;
import com.oopproject.corporatepass.model.Attractions;
import com.oopproject.corporatepass.model.CorporatePass;
import com.oopproject.corporatepass.model.Loan;
import com.oopproject.corporatepass.model.LoanDetails;
import com.oopproject.corporatepass.repository.AttractionsRepository;
import com.oopproject.corporatepass.repository.CorporatePassRepository;
import com.oopproject.corporatepass.repository.LoanDetailsRepository;
import com.oopproject.corporatepass.repository.LoanRepository;
import com.oopproject.corporatepass.repository.UserRepository;
import com.oopproject.corporatepass.util.DateUtility;

@Service
public class LoanServiceImp implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanDetailsRepository loanDetailsRepository;

    @Autowired
    private AttractionsRepository attractionsRepository;

    @Autowired
    private CorporatePassRepository corporatePassRepository;

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan deleteLoan(Loan loan) {
        loanRepository.delete(loan);
        return loan;
    }

    @Override
    public LoanDetails saveLoanDetails(Loan loan, int attractionId, CorporatePass corporatePass) {
        corporatePass.setStatus("reserved");
        corporatePass.setEmail(loan.getEmail());
        corporatePassRepository.save(corporatePass);
        String corporatePassId = corporatePass.getCorporatePassId();
        return loanDetailsRepository.save(new LoanDetails(loan.getLoanId(), corporatePassId));
    }

    @Override
    public boolean isWithinDateRange(Date date, int start, int end) {
        Date currentDate = new Date(System.currentTimeMillis());
        // difference of date and currentDate in days
        long diff = DateUtility.getDaysBetween(date, currentDate);
        return (diff >= start && diff <= end);
    }

    @Override
    public String getPdfGenerate(Loan loan, int attractionId, CorporatePass corporatePass) {
        Attractions attraction = attractionsRepository.findByAttractionIdEquals(attractionId).get();
        String employeeName = userRepository.findByEmailEquals(loan.getEmail()).get().getName();

        EmailRequest emailRequest = new EmailRequest("HR", loan.getEmail(), "Confirmation of your Corporate Pass",
                employeeName, attraction, corporatePass, loan.getDate(), loan.getLoanId());
        Map<String, Object> model = new HashMap<>();
        model.put("request", emailRequest);
        if (attraction.getPassType().equals("electronic")) {
            pdfGenerateService.generateCorporateLetterPDF(attraction, loan);
            return mailService.sendEpassMail(emailRequest, model);
        }
        pdfGenerateService.generateAuthorizationPDF(attraction, loan);
        return mailService.sendPpassMail(emailRequest, model);
    }

    @Override
    public void saveLoanAndLoanDetails(Loan loan, int attractionId, int numPass) {
        Loan savedLoan = saveLoan(loan);

        for (int i = 0; i < numPass; i++) {
            // CorporatePass corporatePass =
            // corporatePassRepository.findFirstByStatusEqualsAndAttractionIdEquals("available",
            // attractionId).orElseThrow(() -> new RuntimeException("Corporate pass not
            // found"));

            ArrayList<CorporatePass> corporatePasses = corporatePassRepository
                    .findFirstByStatusEqualsAndAttractionIdEquals("available", attractionId);
            if (corporatePasses.size() == 0) {
                boolean canLoan = false;

                corporatePasses = corporatePassRepository.findByStatusEqualsAndAttractionIdEquals("reserved",
                        attractionId);
                for (CorporatePass corporatePass : corporatePasses) {
                    String corporatePassId = corporatePass.getCorporatePassId();
                    Date today = new Date(System.currentTimeMillis());
                    ArrayList<Loan> loansList = loanRepository.findLoansbyCorporatePassIdAndDate(corporatePassId,
                            today);

                    boolean isDateClash = false;
                    for (Loan loanObj : loansList) {
                        if (loanObj.getDate().equals(loan.getDate())) {
                            isDateClash = true;
                            break;
                        }
                    }

                    if (!isDateClash) {
                        getPdfGenerate(loan, attractionId, corporatePass);
                        saveLoanDetails(loan, attractionId, corporatePass);
                        canLoan = true;
                        break;
                    }
                }

                if (!canLoan) {
                    deleteLoan(savedLoan);
                    throw new RuntimeException("No corporate pass available for loan");
                }

            } else {
                getPdfGenerate(savedLoan, attractionId, corporatePasses.get(0));
                saveLoanDetails(savedLoan, attractionId, corporatePasses.get(0));
            }
        }
    }

}
