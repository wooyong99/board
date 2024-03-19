package com.bordserver.jwy.exception;

public class AlreadyLoginException extends RuntimeException {
    public AlreadyLoginException(String msg) {
        super(msg);
    }
}
