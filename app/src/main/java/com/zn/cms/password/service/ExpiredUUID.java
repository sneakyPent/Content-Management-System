package com.zn.cms.password.service;

public class ExpiredUUID extends Exception {
    public ExpiredUUID(String s) {
        // Call constructor of parent Exception
        super(s);
    }
}
