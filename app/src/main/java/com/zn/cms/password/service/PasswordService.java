package com.zn.cms.password.service;

public interface PasswordService {

    void resetPassword(String token, String password) throws ExpiredUUID;

    void sendForgotPasswordMail(String email);
}
