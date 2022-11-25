package com.oopproject.corporatepass.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Attractions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attractionId;
    private String name;
    private String address;
    private double replacementFee;
    private String cardType;
    private String cardName;
    private String passType;

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public Attractions() {
    }

    public Attractions(String name) {
        this.name = name;
    }

    public Attractions(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getReplacementFee() {
        return replacementFee;
    }

    public void setReplacementFee(double replacementFee) {
        this.replacementFee = replacementFee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
