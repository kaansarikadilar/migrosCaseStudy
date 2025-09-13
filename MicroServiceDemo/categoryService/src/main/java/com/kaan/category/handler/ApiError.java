package com.kaan.category.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError<E> {

    public Integer status;

    public Exception<E> exception;

}
