package com.kaan.category.exeption;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private messageType messageType;

    private String ofStatic;

    public ErrorMessage(messageType messageType) {
        this.messageType = messageType;
    }
    public ErrorMessage(messageType messageType, String ofStatic) {
        this.messageType = messageType;
        this.ofStatic = ofStatic;
    }

    public String prepareErrorMessage(){
        StringBuilder Builder = new StringBuilder();
        Builder.append(messageType.getMessage());
        if(ofStatic!=null){
            Builder.append( " : " + ofStatic);
        }
        return Builder.toString();
    }
}
