package com.oopproject.corporatepass.controller;

import com.oopproject.corporatepass.model.CorporatePass;
import com.oopproject.corporatepass.repository.CorporatePassRepository;
import com.oopproject.corporatepass.service.CorporatePassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.oopproject.corporatepass.exception.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class CorporatePassController {

    @Autowired
    private CorporatePassRepository corporatePassRepository;

    @Autowired
    private CorporatePassService corporatePassService;

    @GetMapping("/corporatePass")
    public List<CorporatePass> getAllCorporatePasses() {
        return corporatePassRepository.getAllCorporatePasses();
    }

    @GetMapping("/corporatePass/{id}")
    public ResponseEntity<CorporatePass> getCorporatePassById(
            @PathVariable(value = "id") String corporatePassId) throws ResourceNotFoundException {
        CorporatePass corporatePass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId)
                .orElseThrow(() -> new ResourceNotFoundException("Corporate Pass not found :: " + corporatePassId));
        return ResponseEntity.ok().body(corporatePass);
    }

    @PostMapping("/corporatePass")
    public ResponseEntity<CorporatePass> addCorporatePass(@Valid @RequestBody CorporatePass corporatePass) {
        return ResponseEntity.ok(corporatePassRepository.save(corporatePass));
    }

    // @PostMapping("/corporatePass/{quantity}")
    // public ResponseEntity<String> addCorporatePass(@Valid @RequestBody
    // CorporatePass corporatePass,
    // @PathVariable(value = "quantity") Integer quantity) {
    // int i = 0;
    // for (; i < quantity; i++) {
    // // create new corporate pass for each iteration
    // CorporatePass newCorporatePass = new CorporatePass();
    // newCorporatePass.setEmail(corporatePass.getEmail());
    // newCorporatePass.setStatus(corporatePass.getStatus());
    // newCorporatePass.setAttractionId(corporatePass.getAttractionId());
    // corporatePassRepository.save(newCorporatePass);
    // }
    // System.out.println("Quantity: " + quantity);
    // return i == 1 ? new ResponseEntity<String>("Successfully added " + i + "
    // corporate pass", HttpStatus.OK)
    // : new ResponseEntity<String>("Successfully added " + i + " corporate passes",
    // HttpStatus.OK);
    // }

    @PutMapping("/corporatePass/{id}")
    public ResponseEntity<CorporatePass> updateCorporatePass(
            @PathVariable(value = "id") String corporatePassId,
            @Valid @RequestBody CorporatePass corporatePassDetails) {
        try {
            CorporatePass updatedCorporatePass = corporatePassService.updateCorparatePass(corporatePassId,
                    corporatePassDetails);
            return ResponseEntity.ok(updatedCorporatePass);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();

    }
}
