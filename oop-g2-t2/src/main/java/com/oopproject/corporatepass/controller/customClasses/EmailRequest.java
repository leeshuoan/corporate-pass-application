package com.oopproject.corporatepass.controller.customClasses;
import java.sql.Date;

import com.oopproject.corporatepass.model.Attractions;
import com.oopproject.corporatepass.model.CorporatePass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {
	private String from;
	private String to;
	private String subject;
	private String name;
    private Attractions attraction;
    private CorporatePass corporatePass;
    private String date;
	private int loanId;

	public EmailRequest(String from, String to, String subject, String name, Attractions attraction, CorporatePass corporatePass, Date date, int loanId) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.name = name;
		this.attraction = attraction;
		this.corporatePass = corporatePass;
		this.date = date.toString();
		this.loanId = loanId;
	}

	public EmailRequest(String from, String to, String subject, String name, Attractions attraction){
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.name = name;
		this.attraction = attraction;
	}

	public EmailRequest(String name) {
		this.name = name;
	}
}

