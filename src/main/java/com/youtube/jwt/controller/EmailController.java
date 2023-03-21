package com.youtube.jwt.controller;
/*
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.youtube.jwt.entity.Email;
import com.youtube.jwt.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

// Annotation
@RestController
public class EmailController {

   @Autowired private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody Email details) {
        String status = emailService.sendSimpleMail(details);

        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody Email details)
    {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }

  /* @PostMapping("/sendMail")
    String sendEmailMessage() {
        this.emailService.sendMessage(
                 "gturnquist@vmware.com",
                 "Greetings YouTube community!",
                 "I hope you're nejoying this live coding session." );
        return "Message sent";
}*/
//}