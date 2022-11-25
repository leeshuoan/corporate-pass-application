package com.oopproject.corporatepass.service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.oopproject.corporatepass.controller.customClasses.EmailRequest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailService {
    @Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Configuration configuration;

	public String sendEpassMail(EmailRequest request, Map<String, Object> model) {

		String response;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

            // need to add in logic to get different barcode depending on the attraction and the pdf attachment depending on user

			ClassPathResource pdf = new ClassPathResource("static/pdf/corporateMembershipLetter"+ request.getLoanId() +".pdf");
			Template template = configuration.getTemplate("epass_template/email.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			helper.setSubject(request.getSubject());
			helper.setText(html, true);
			helper.addAttachment("corporateMembershipLetter" + request.getLoanId() + ".pdf", pdf);

			mailSender.send(message);
			response = "Email has been sent to :" + request.getTo();
		} catch (MessagingException | IOException | TemplateException e) {
			response = "Email send failure to :" + request.getTo();
		}
		return response;
	}


    public String sendPpassMail(EmailRequest request, Map<String, Object> model) {

		String response;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			ClassPathResource pdf = new ClassPathResource("static/pdf/authorizationletter"+ request.getLoanId() +".pdf");
			Template template = configuration.getTemplate("ppass_template/email.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			helper.setSubject(request.getSubject());
			helper.setText(html, true);

			helper.addAttachment("authorizationletter" + request.getLoanId() + ".pdf", pdf);

			mailSender.send(message);
			response = "Email has been sent to :" + request.getTo();
			return response;
		} catch (MessagingException | IOException | TemplateException e) {
			response = "Email send failure to :" + request.getTo();
		} catch (Exception e) {
		}
		return null;
	}

    public String sendReminderMail(EmailRequest request, Map<String, Object> model) {

		String response;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Template template = configuration.getTemplate("reminder_template/return_email.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			helper.setSubject(request.getSubject());
			helper.setText(html, true);

			mailSender.send(message);
			response = "Email has been sent to :" + request.getTo();
		} catch (MessagingException | IOException | TemplateException e) {
			response = "Email send failure to :" + request.getTo();
		}
		return response;
	}

	public String sendCollectionMail(EmailRequest request, Map<String, Object> model){
		String response;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Template template = configuration.getTemplate("reminder_template/collect_email.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			helper.setSubject(request.getSubject());
			helper.setText(html, true);

			mailSender.send(message);
			response = "Email has been sent to :" + request.getTo();
		} catch (MessagingException | IOException | TemplateException e) {
			response = "Email send failure to :" + request.getTo();
		}
		return response;
	}

	public String sendLostMail(EmailRequest request, Map<String,Object> model){
		String response;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Template template = configuration.getTemplate("lost_template/email.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			helper.setSubject(request.getSubject());
			helper.setText(html, true);

			mailSender.send(message);
			response = "Email has been sent to :" + request.getTo();
		} catch (MessagingException | IOException | TemplateException e) {
			response = "Email send failure to :" + request.getTo();
		}
		return response;
	}


    public String sendCollectedMail(EmailRequest request, Map<String, Object> model) {
		String response;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Template template = configuration.getTemplate("collect_template/email.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			helper.setSubject(request.getSubject());
			helper.setText(html, true);

			mailSender.send(message);
			response = "Email has been sent to :" + request.getTo();
		} catch (MessagingException | IOException | TemplateException e) {
			response = "Email send failure to :" + request.getTo();
		}
		return response;
    }
}
