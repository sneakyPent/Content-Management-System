package com.zn.cms.utils;


import com.zn.cms.email.model.MailServerProperties;


public interface Mailer {
    MailServerProperties GMAIL = new MailServerProperties(
            "smtp.gmail.com ",
            587,
            true,
            true,
            "smtp",
            true
    );

    MailServerProperties YAHOO = new MailServerProperties(
            "smtp.mail.yahoo.com",
            465,
            true,
            true,
            "smtp",
            true
    );

    MailServerProperties OUTLOOK = new MailServerProperties(
            "smtp-mail.outlook.com",
            587,
            true,
            true,
            "smtp",
            true
    );

}
