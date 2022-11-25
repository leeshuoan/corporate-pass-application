package com.oopproject.corporatepass.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oopproject.corporatepass.controller.customClasses.EmailRequest;
import com.oopproject.corporatepass.exception.ResourceNotFoundException;
import com.oopproject.corporatepass.model.Attractions;
import com.oopproject.corporatepass.model.CorporatePass;
import com.oopproject.corporatepass.model.Loan;
import com.oopproject.corporatepass.model.LoanDetails;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.repository.CorporatePassRepository;
import com.oopproject.corporatepass.repository.LoanDetailsRepository;
import com.oopproject.corporatepass.repository.LoanRepository;


@Component
public class EmailScheduler {
    
    @Autowired
    private CorporatePassRepository corporatePassRepository;

    @Autowired
    private UserService userService;

    @Autowired 
    private MailService mailService;

    @Autowired
    private AttractionsService attractionsService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanDetailsRepository loanDetailsRepository;

    // this means that this method will be executed everyday 12pm (for actual production) 
    // send every 1 minute for demo purposes
    // @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendReminderMail() {
        // get all loans
        List<CorporatePass> loanedPasses = corporatePassRepository.findEmailOfLoanedPasses("loaned"); 
        // for each loan, send a reminder mail
        for (CorporatePass pass : loanedPasses) {
            // find the User based on pass.email()
            User user = userService.getUserByEmail(pass.getEmail());

            // get loan date and determine the day
            Date loanDate = loanRepository.findDatebyCorporatePassId(pass.getCorporatePassId(),pass.getEmail());
            // determine if its saturday
            boolean isSaturday = loanDate.toLocalDate().getDayOfWeek().getValue() == 6;
            if (!isSaturday) {
                // if its not saturday, send reminder mail
                Attractions attraction = attractionsService.getAttractionById(pass.getAttractionId());
                EmailRequest emailRequest = new EmailRequest("HR", pass.getEmail(), "Reminder to return your Corporate Pass",user.getName(),attraction);
                Map<String, Object> model = new HashMap<>();
                model.put("request", emailRequest);
                mailService.sendReminderMail(emailRequest,model);
            }
        }
    }
    
    // @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendCollectionMail() throws ResourceNotFoundException {
        // get tomorrow's date
        Date tomorrow = new java.sql.Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        // get all loans
        ArrayList<Loan> loans = loanRepository.findByDateEquals(tomorrow);
        for (Loan loan:loans){
            // find the User based on pass.email()
            User user = userService.getUserByEmail(loan.getEmail());
            ArrayList<LoanDetails> loanDetails = loanDetailsRepository.findByLoanIdEquals(loan.getLoanId());
            LoanDetails loan1 = loanDetails.get(0);
            CorporatePass pass1 = corporatePassRepository.findByCorporatePassIdEquals(loan1.getCorporatePassId())
                .orElseThrow(()-> new ResourceNotFoundException("Corporate Pass not found ::" + loan1.getCorporatePassId()));
            Attractions attraction1 = attractionsService.getAttractionById(pass1.getAttractionId());
            EmailRequest emailRequest = new EmailRequest("HR", loan.getEmail(), "Reminder to collect your Corporate Pass",user.getName(),attraction1,pass1,tomorrow,loan.getLoanId());
            Map<String, Object> model = new HashMap<>();
            model.put("request", emailRequest);
            mailService.sendCollectionMail(emailRequest,model);
        }
    }
}
