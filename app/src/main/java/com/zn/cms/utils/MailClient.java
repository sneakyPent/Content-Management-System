package com.zn.cms.utils;

import com.zn.cms.email.model.MailServerProperties;

public enum MailClient {
    GMAIL,
    YAHOO,
    OUTLOOK;


    public MailServerProperties getMailClientProperties(MailClient mailClient) {

        switch (mailClient){
            case GMAIL:
                break;
            case YAHOO:
                break;
            case OUTLOOK:
                break;
        }
        return null;
    }

}