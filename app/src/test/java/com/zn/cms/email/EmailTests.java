package com.zn.cms.email;

import com.zn.cms.email.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class EmailTests {

    @Autowired
    private EmailService emailService;

    @Test
    void sendSimpleMailTest() {
        emailService.sendSimpleMessage("zaharioudakis@yahoo.com", "Testing", "Testing my mail client");
    }

    @Test
    void sendMailWithAttachmentTest() throws MessagingException {
        String path = System.getProperty("user.dir") + "/src/test/java/com/zn/cms/email/Invoice.txt";

        emailService.sendMessageWithAttachment(
                "zaharioudakis@yahoo.com",
                "Testing",
                "Testing my mail client",
                path
        );
    }

}
