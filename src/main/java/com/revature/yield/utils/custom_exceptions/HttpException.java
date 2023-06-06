package com.revature.yield.utils.custom_exceptions;

public class HttpException extends RuntimeException {

    public HttpException(String message) {
        super(message);
    }
}
