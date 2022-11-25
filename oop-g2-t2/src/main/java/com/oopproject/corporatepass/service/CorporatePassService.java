package com.oopproject.corporatepass.service;

import com.oopproject.corporatepass.exception.ResourceNotFoundException;
import com.oopproject.corporatepass.model.CorporatePass;

public interface CorporatePassService {
    CorporatePass updateCorparatePass(
            String corporatePassId, CorporatePass corporatePass) throws ResourceNotFoundException;
}
