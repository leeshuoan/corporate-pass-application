package com.oopproject.corporatepass.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MaxLimits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private int bookings;
    private int loans;

    public MaxLimits(int bookings, int loans) {
        this.bookings = bookings;
        this.loans = loans;
    }
    
    public int getMaxBookings() {
        return bookings;
    }

    public void setMaxBookings(int bookings) {
        this.bookings = bookings;
    }

    public int getMaxLoans() {
        return loans;
    }

    public void setMaxLoans(int loans) {
        this.loans = loans;
    }

}
