package com.oopproject.corporatepass.model;

public class GeneralOffice extends User {

    public GeneralOffice(User user) {
        super(user);
    }

    public GeneralOffice() {
    }

    public void sendEmail(String email) {
        String content = "You have collected your pass";
    }

    public void updatePassCollected(Loan loan) {
        
    }

    public void updatePassReturned(Loan loan) {
    }
}
