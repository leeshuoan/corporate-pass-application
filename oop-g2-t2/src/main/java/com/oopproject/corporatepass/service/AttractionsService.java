package com.oopproject.corporatepass.service;

import java.util.List;
import com.oopproject.corporatepass.model.Attractions;

public interface AttractionsService {
    List<Attractions> getAllAttractions();
    Attractions updateAttraction(int attractionId, String name, String address, String cardName, String passType, double replacementFee);
    Attractions getAttractionById(int attractionId);
}
