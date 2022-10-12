package com.zn.cms.email.service;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.util.List;
public interface EmailService {
    void sendMail(String to, String subject, String body) throws MessagingException;
    void sendPasswordResetEmail(String email, String subject);

}
