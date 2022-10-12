package com.zn.cms.email.model;

import com.zn.cms.utils.MailClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailServerProperties {
    private String host;
    private Integer port;
    private boolean smtpAuth;
    private boolean starttls;
    private String transportProtocol;
    private boolean ssl;
}
