package com.kaan.product.handler;

import com.kaan.category.handler.Exception;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError<E> {

    public Integer status;

    public Exception<E> exception;

}
