package com.qunjie.crm.exception;

@SuppressWarnings("serial")
public class AppAccessTokenRequestException extends AccessTokenException {

    public AppAccessTokenRequestException(int code, String msg) {
        super(code, msg);
    }

}
