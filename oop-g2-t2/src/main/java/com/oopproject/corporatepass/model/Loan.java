package com.oopproject.corporatepass.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;
    private Date date; // yyyy-mm-dd (java.sql.Date object)
    private String email;

    public Loan() {
    }
    public Loan(Date date, String email) {
        this.date = date;
        this.email = email;
    }
    public int getLoanId() {
        return loanId;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
