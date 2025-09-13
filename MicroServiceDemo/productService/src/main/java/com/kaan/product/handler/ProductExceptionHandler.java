package com.kaan.product.handler;

import com.kaan.category.exeption.BaseException;
import com.kaan.category.handler.ApiError;
import com.kaan.category.handler.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError> handleBaseException(BaseException exception, WebRequest request){


        return ResponseEntity.badRequest().body(createApiError(exception.getMessage(),request));
    }


    private String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        }catch (UnknownHostException e){
            System.out.println("Hata oluştu "+ e.getMessage());
        }
        return null;
    }

    public <E> com.kaan.category.handler.ApiError<E> createApiError(E message, WebRequest request){
        com.kaan.category.handler.ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());

        com.kaan.category.handler.Exception<E> exception = new Exception<>();
        exception.setHostName(getHostName());
        exception.setCreateTime(new Date());
        exception.setPath(request.getDescription(false).substring(4));
        exception.setMessage(message);

        apiError.setException(exception);
        return apiError;
    }
}
