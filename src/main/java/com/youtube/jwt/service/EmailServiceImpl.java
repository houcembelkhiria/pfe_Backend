package com.youtube.jwt.service;

import com.youtube.jwt.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/*import com.mailgun.api.Mailgun;
import com.mailgun.api.Message;
import com.mailgun.api.exceptions.MailgunException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;*/
/*
@Service
public class EmailServiceImpl {

    @Autowired
    private Mailgun mailgun;

    public void sendEmail(String from, String to, String subject, String body) throws MailgunException {
        Message message = new Message.Builder()
                .setFromAddress(from)
                .setToAddress(to)
                .setSubject(subject)
                .setTextBody(body)
                .build();

        mailgun.messages().send(message);
    }
}
*/
/*@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public String sendSimpleMail(Email details)
    {
        // try {
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
      // }

       // catch (Exception e) {
           // return "Error while Sending Mail";
        //}
    }


    public String sendMailWithAttachment(Email details) {
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }
}*/


