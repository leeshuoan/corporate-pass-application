package com.oopproject.corporatepass.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.ResponseHandler;
import com.oopproject.corporatepass.model.Attractions;
import com.oopproject.corporatepass.repository.AttractionsRepository;
import com.oopproject.corporatepass.service.AttractionsService;

@RestController
@RequestMapping("/api/v1/attractions")
@CrossOrigin(origins = "*")
public class AttractionsController {
    @Autowired
    private AttractionsRepository attractionsRepository;

    @Autowired
    private AttractionsService attractionsService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllAttractions() {
        List<Object> objectList = new ArrayList<Object>(attractionsService.getAllAttractions());
        return ResponseHandler.generateListResponse("Successfully retrieved all attractions", HttpStatus.OK,
                objectList);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAttraction(@Valid @RequestBody Attractions attractions) {
        attractionsRepository.save(attractions);
        return ResponseEntity.ok().body(attractions);
    }

    @PutMapping("/updateAttraction")
    
    public ResponseEntity<Object> updateAttraction(@RequestBody Attractions attraction) {
        Attractions updatedAttraction = attractionsService.updateAttraction(attraction.getAttractionId(), attraction.getName(), attraction.getAddress(), attraction.getCardName(), attraction.getPassType(), attraction.getReplacementFee());
        return ResponseHandler.generateObjectResponse("Successfully updated" + attraction.getName(), HttpStatus.OK, updatedAttraction);
    }
    // public String updateAttraction(@RequestBody Attractions attraction) {
    //     attractionsService.updateAttraction(attraction.getName(), attraction.getReplacementFee());
    //     return "Successfully updated " + attraction.getName() + "'s replacement fee to $"
    //             + attraction.getReplacementFee();
}
