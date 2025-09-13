package com.kaan.product.exception;

public class BaseException extends RuntimeException {
    public BaseException() {
    }
    public BaseException(String message) {
        super(ErrorMessage.prepareErrorMessage());
    }

    public static class NoCategoryFoundException extends RuntimeException {
        public NoCategoryFoundException(String message) {
            super(message);
        }
    }
}
