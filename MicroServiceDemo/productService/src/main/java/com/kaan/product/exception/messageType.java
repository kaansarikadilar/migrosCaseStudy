package com.kaan.product.exception;
import com.kaan.category.repository.CategoryRepository;
import com.kaan.product.Feign.CategoryInterface;
import lombok.Getter;

@Getter
public enum messageType {

    NO_RECORD_EXIST("1001","We cannot find this id in our database"),
    NO_RECORD_FOUND("9999","General Error"),
    NO_CATEGORY_FOUND("1002","The CategoryId you have entered dosent exist"+
            "These are the avaible categories : ");

    private String code;

    private String message;

    messageType(String code, String message){
        this.code = code;
        this.message = message;
    }

}