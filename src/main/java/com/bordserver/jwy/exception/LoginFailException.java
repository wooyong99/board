package com.bordserver.jwy.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException(String msg) {
        super(msg);
    }
}
