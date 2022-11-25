package com.oopproject.corporatepass.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopproject.corporatepass.controller.customClasses.EmailRequest;
import com.oopproject.corporatepass.service.MailService;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

	@Autowired
	private MailService mailService;

	@PostMapping("/epass")
	public ResponseEntity<String> sendEpassMail(@RequestBody EmailRequest emailRequest) {
		Map<String, Object> model = new HashMap<>();
		model.put("request", emailRequest);

		String response = mailService.sendEpassMail(emailRequest, model);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @PostMapping("/ppass")
    public ResponseEntity<String> sendPpassMail(@RequestBody EmailRequest emailRequest) {
		Map<String, Object> model = new HashMap<>();
		model.put("request", emailRequest);
		String response = mailService.sendPpassMail(emailRequest, model);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @PostMapping("/reminder")
    public ResponseEntity<String> sendReminderMail(@RequestBody EmailRequest emailRequest) {
		Map<String, Object> model = new HashMap<>();
		model.put("request", emailRequest);
		String response = mailService.sendReminderMail(emailRequest, model);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/collection")
	public ResponseEntity<String> sendCollectionMail(@RequestBody EmailRequest emailRequest) {
		Map<String, Object> model = new HashMap<>();
		model.put("request", emailRequest);
		String response = mailService.sendCollectionMail(emailRequest, model);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Need the corporate card number and the email address of the one that lost it 
	@PostMapping("/lost")
	public ResponseEntity<String> sendLostMail(@RequestBody EmailRequest emailRequest) {
		Map<String, Object> model = new HashMap<>();
		model.put("request", emailRequest);
		String response = mailService.sendLostMail(emailRequest, model);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Need email of user who collected the pass
	@PostMapping("/collected")
	public ResponseEntity<String> sendCollectedMail(@RequestBody EmailRequest emailRequest) {
		Map<String, Object> model = new HashMap<>();
		model.put("request", emailRequest);
		String response = mailService.sendCollectedMail(emailRequest, model);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}