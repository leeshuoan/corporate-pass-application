package com.oopproject.corporatepass.controller.customClasses;

public class LoanRequest {
    private int attractionId;
    private String attractionName;
    private long date;
    private int numPass;

    public int getNumPass() {
        return numPass;
    }

    public void setNumPass(int numPass) {
        this.numPass = numPass;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LoanRequest [attractionId=" + attractionId + ", attractionName=" + attractionName + ", date=" + date
                + ", numPass=" + numPass + "]";
    }
    
}
