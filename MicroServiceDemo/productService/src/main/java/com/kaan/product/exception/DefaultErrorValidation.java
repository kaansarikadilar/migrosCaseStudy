package com.kaan.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultErrorValidation<T> {

    private String id;

    private Date errorTime;

    private T errors;

}
