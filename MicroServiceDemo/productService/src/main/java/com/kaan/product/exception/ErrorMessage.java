package com.kaan.product.exception;

import com.kaan.category.exeption.messageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private static messageType messageType;

    private static String ofStatic;

    public ErrorMessage(messageType messageType) {
        this.messageType = messageType;
    }
    public ErrorMessage(messageType messageType, String ofStatic) {
        this.messageType = messageType;
        this.ofStatic = ofStatic;
    }


    public static String prepareErrorMessage(){
        StringBuilder Builder = new StringBuilder();
        Builder.append(messageType.getMessage());
        if(ofStatic!=null){
            Builder.append( " : " + ofStatic);
        }
        return Builder.toString();
    }
}