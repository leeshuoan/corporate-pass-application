package com.oopproject.corporatepass.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.ResponseHandler;
import com.oopproject.corporatepass.repository.MaxLimitsRepository;

@RestController
@RequestMapping("/api/v1/limits")
@CrossOrigin(origins = "*")
@Transactional
public class MaxLimitsController {
    @Autowired
    private MaxLimitsRepository maxLimitsRepository;

    @GetMapping("/bookings")
    public ResponseEntity<Object> getMaxBookings() {
        return ResponseHandler.generateIntResponse("Max number of bookings per loan", HttpStatus.OK, maxLimitsRepository.getMaxBookings());
    }

    @GetMapping("/loans")
    public ResponseEntity<Object> getMaxLoans() {
        return ResponseHandler.generateIntResponse("Max number of loans per month", HttpStatus.OK, maxLimitsRepository.getMaxLoans());
    }

    @PutMapping("/update/bookings/{bookingNum}")
    public ResponseEntity<String> updateMaxBookings(@PathVariable("bookingNum") int bookings) {
        maxLimitsRepository.updateMaxBookings(bookings);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/loans/{loanNum}")
    public ResponseEntity<String> updateMaxLoans(@PathVariable("loanNum") int loans) {
        maxLimitsRepository.updateMaxLoans(loans);
        return ResponseEntity.ok().build();
    }
}
