package com.kaan.category.exeption;


import lombok.Getter;

@Getter
public enum messageType {

    NO_RECORD_EXIST("1001","We cannot find this id in our database"),
    NO_RECORD_FOUND("9999","General Error");

    private String code;

    private String message;

    messageType(String code, String message){
        this.code = code;
        this.message = message;
    }

}
