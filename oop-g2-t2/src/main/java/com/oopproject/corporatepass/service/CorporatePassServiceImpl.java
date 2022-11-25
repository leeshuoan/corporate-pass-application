package com.oopproject.corporatepass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopproject.corporatepass.exception.ResourceNotFoundException;
import com.oopproject.corporatepass.model.CorporatePass;
import com.oopproject.corporatepass.repository.CorporatePassRepository;

@Service
public class CorporatePassServiceImpl implements CorporatePassService{
    
    @Autowired CorporatePassRepository corporatePassRepository;

    @Override
    public CorporatePass updateCorparatePass(
            String corporatePassId, CorporatePass corporatePass) throws ResourceNotFoundException {
        corporatePass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId)
                .orElseThrow(() -> new ResourceNotFoundException("Corporate Pass not found ::" + corporatePassId));
        corporatePass.setCorporatePassId(corporatePassId);
        corporatePass.setStatus(corporatePass.getStatus());
        corporatePass.setAttractionId(corporatePass.getAttractionId());
        corporatePass.setEmail(corporatePass.getEmail());
        return corporatePassRepository.save(corporatePass);

    }
}
