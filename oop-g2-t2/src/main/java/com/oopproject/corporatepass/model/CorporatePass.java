package com.oopproject.corporatepass.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CorporatePass {
    @Id
    private String corporatePassId;    // card Id of the corp pass
    private String status;
    private int attractionId;
    private String email;

    public String getCorporatePassId() {
        return corporatePassId;
    }
    public void setCorporatePassId(String corporatePassId) {
        this.corporatePassId = corporatePassId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getAttractionId() {
        return attractionId;
    }
    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
