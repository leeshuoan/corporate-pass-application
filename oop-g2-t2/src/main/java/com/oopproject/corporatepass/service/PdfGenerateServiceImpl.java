package com.oopproject.corporatepass.service;

import com.lowagie.text.DocumentException;
import com.oopproject.corporatepass.model.Attractions;
import com.oopproject.corporatepass.model.Loan;
import com.oopproject.corporatepass.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Service
public class PdfGenerateServiceImpl implements PdfGenerateService{
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private UserRepository userRepository;

    @Value("${pdf.directory}")
    private String pdfDirectory;

    @Override
    public void generatePdfFile (String templateName, Map<String, Object> data, String pdfFileName){ 
        // IContext context = new Context();
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(templateName, context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String generateAuthorizationPDF(Attractions attraction, Loan loan){
        String employeeName = userRepository.findByEmailEquals(loan.getEmail()).get().getName();

        Map<String, Object> data = new HashMap<>();
        LocalDate date = java.time.LocalDate.now();
        // split the address by comma and get the first part
        String[] fullAddress = attraction.getAddress().split(",");
        String address = fullAddress[0];
        String postal = fullAddress[1];
        String[] cardNameList = attraction.getCardName().split(" ");
        String cardName = cardNameList[0];
        String secondHalf = cardNameList[1] + " " + cardNameList[2];

        data.put("attractionName", attraction.getName());
        data.put("attractionCardType", attraction.getCardType());
        data.put("address", address);
        data.put("postal", postal);
        data.put("cardName", cardName);
        data.put("secondHalf", secondHalf + "(s)");
        data.put("loanDate", loan.getDate().toString());
        data.put("employeeName", employeeName);
        data.put("date", date.toString());

        generatePdfFile("authorization-letter", data, "authorizationletter"+loan.getLoanId()+ ".pdf");

        // get pdf file and attach to 
        return "authorizationletter"+loan.getLoanId()+ ".pdf created successfully";
    }

    public String generateCorporateLetterPDF(Attractions attraction, Loan loan){
        String employeeName = userRepository.findByEmailEquals(loan.getEmail()).get().getName();

        Map<String, Object> data = new HashMap<>();
        LocalDate date = java.time.LocalDate.now();

        data.put("loanDate", loan.getDate().toString());
        data.put("employeeName", employeeName);
        data.put("date", date.toString());

        generatePdfFile("corporate-membership-letter", data, "corporateMembershipLetter"+loan.getLoanId()+ ".pdf");

        // get pdf file and attach to 
        return "corporateMembershipLetter"+loan.getLoanId()+ ".pdf created successfully";
    }


}