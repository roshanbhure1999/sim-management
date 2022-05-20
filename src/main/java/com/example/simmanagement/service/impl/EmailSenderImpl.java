package com.example.simmanagement.service.impl;

import com.example.simmanagement.dto.Mail;
import com.example.simmanagement.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service("mailService")
public class EmailSenderImpl implements EmailSenderService {

    @Autowired
    JavaMailSender mailSender;

    @Override
    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            log.info("sending Mail");
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            log.info("Mails sent.");
            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            log.error("Mails not sent..");
            e.printStackTrace();
        }
    }


}
