package com.oopproject.corporatepass.service;

import java.util.*;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopproject.corporatepass.controller.customClasses.EmailRequest;
import com.oopproject.corporatepass.controller.customClasses.LoanResponse;
import com.oopproject.corporatepass.model.CorporatePass;
import com.oopproject.corporatepass.repository.AttractionsRepository;
import com.oopproject.corporatepass.repository.CorporatePassRepository;
import com.oopproject.corporatepass.repository.UserRepository;

@Service
public class GeneralOfficeServiceImp implements GeneralOfficeService {

    @Autowired
    private CorporatePassRepository corporatePassRepository;

    @Autowired
    private AttractionsRepository attractionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
	private MailService mailService;

    @Override
    public ArrayList<LoanResponse> getUserLoan(String email, Date loanDate) {
        ArrayList<CorporatePass> corporatePasses = corporatePassRepository.findCorporatePassByEmailAndDate(email,
                loanDate);
        ArrayList<LoanResponse> loanResponse = new ArrayList<>();
        for (CorporatePass corporatePass : corporatePasses) {
            String attractionName = attractionsRepository.findByAttractionIdEquals(corporatePass.getAttractionId())
                    .get().getName();
            loanResponse.add(new LoanResponse(email, loanDate, corporatePass.getCorporatePassId(),
                    corporatePass.getStatus(), attractionName));
        }

        return loanResponse;
    }

    @Override
    public String passReturned(String corporatePassId) {
        CorporatePass corpPass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId).get();
        corpPass.setStatus("available");
        corpPass.setEmail("-");
        corporatePassRepository.save(corpPass);

        return "Pass has been returned";
    }

    @Override
    public String passFound(String corporatePassId) {
        CorporatePass corpPass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId).get();
        corpPass.setStatus("available");
        corporatePassRepository.save(corpPass);

        return "Pass has been made available";
    }

    @Override
    public String passLoaned(String corporatePassId, String email) {
        CorporatePass corpPass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId).get();
        corpPass.setStatus("loaned");
        corpPass.setEmail(email);
        corporatePassRepository.save(corpPass);
        
        String name = userRepository.findByEmailEquals(email).get().getName();
        sendCollectedMail(new EmailRequest("GOP", email, "Pass Collected", name, null));

        return "Pass has been loaned to respective loaner";
    }

    @Override
    public String passLost(String corporatePassId) {
        CorporatePass corpPass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId).get();
        corpPass.setStatus("lost");
        corporatePassRepository.save(corpPass);
        
        String[] adminsEmail = userRepository.getAllAdminsEmail();
        for (String email: adminsEmail) {
            Date date = new Date(System.currentTimeMillis());
            EmailRequest er = new EmailRequest("GOP", email, "Pass Lost", null, null, corpPass, date, 0);
            sendLostMail(er);
        }

        return "Pass is now lost";
    }

    @Override
    public String sendCollectedMail(EmailRequest emailRequest) {
        Map<String, Object> model = new HashMap<>();
		model.put("request", emailRequest);
		return mailService.sendCollectedMail(emailRequest, model);
    }

    @Override
    public String sendLostMail(EmailRequest emailRequest) {
		Map<String, Object> model = new HashMap<>();
		model.put("request", emailRequest);
		return mailService.sendLostMail(emailRequest, model);
	}
}
