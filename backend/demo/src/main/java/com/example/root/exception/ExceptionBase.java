package com.example.root.exception;

public class ExceptionBase extends RuntimeException{
    private final String message;
    private final int code;

    public ExceptionBase(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
