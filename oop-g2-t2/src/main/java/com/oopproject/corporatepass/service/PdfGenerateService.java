package com.oopproject.corporatepass.service;

import java.util.Map;

import com.oopproject.corporatepass.model.Attractions;
import com.oopproject.corporatepass.model.Loan;

public interface PdfGenerateService {
    void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
    String generateAuthorizationPDF(Attractions attraction, Loan loan);
    String generateCorporateLetterPDF(Attractions attraction, Loan loan);
}