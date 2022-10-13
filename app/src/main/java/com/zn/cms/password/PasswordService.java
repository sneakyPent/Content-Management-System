package com.zn.cms.password;

public interface PasswordService {

    void resetPassword(String token, String password);

    void sendForgotPasswordMail(String email);
}
