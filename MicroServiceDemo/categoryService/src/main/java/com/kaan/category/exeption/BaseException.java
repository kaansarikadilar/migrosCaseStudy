package com.kaan.category.exeption;

public class BaseException extends RuntimeException {


    public BaseException(){

    }
    public BaseException(ErrorMessage errorMessage){
        super(errorMessage.prepareErrorMessage());
    }
}
