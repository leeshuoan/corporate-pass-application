package com.oopproject.corporatepass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopproject.corporatepass.model.Attractions;
import com.oopproject.corporatepass.repository.AttractionsRepository;

@Service
public class AttractionsServiceImp implements AttractionsService {
    @Autowired
    private AttractionsRepository attractionsRepository;

    @Override
    public List<Attractions> getAllAttractions() {
        return attractionsRepository.findAll();    
    }

    @Override 
    public Attractions updateAttraction(int attractionId, String name,  String address, String cardName, String passtype, double replacementFee) {
        Attractions attraction = attractionsRepository.findByAttractionIdEquals(attractionId).get();
        if(attraction==null){
            throw new RuntimeException("Attraction not found");
        }
        else{
            attraction.setName(name);
            attraction.setAddress(address);
            attraction.setCardName(cardName);
            attraction.setPassType(passtype);
            attraction.setReplacementFee(replacementFee);
            attractionsRepository.save(attraction);
            System.out.println(attraction);
            return attraction;
        }
    }

    @Override
    public Attractions getAttractionById(int attractionId) {
        return attractionsRepository.findByAttractionIdEquals(attractionId).get();
    }
}
