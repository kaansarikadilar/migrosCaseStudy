package com.kaan.category.exeption;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class CategoryValidException {

    //request te hata olunca postman de o hatayı göstermek için kullanılır

    private List<String> addMapValue(List<String> list,String newValue){
        list.add(newValue);
        return list;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultErrorValidation> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String,List<String>> errorsMap = new HashMap<>();

     for(ObjectError objError : ex.getBindingResult().getAllErrors()){
         String fieldName =((FieldError)objError).getField();
         if(errorsMap.containsKey(fieldName)){
             errorsMap.put(fieldName,addMapValue(errorsMap.get(fieldName),objError.getDefaultMessage()));
         }
        else {
            errorsMap.put(fieldName,addMapValue(new ArrayList<>(),objError.getDefaultMessage()));
            }
        }
     return ResponseEntity.badRequest().body(createDefaultErrors(errorsMap));
    }

    private <T> DefaultErrorValidation<T> createDefaultErrors(T errorsMap){
        DefaultErrorValidation<T> defaultError = new DefaultErrorValidation<T>();
        defaultError.setId(UUID.randomUUID().toString());
        defaultError.setErrorTime(new Date());
        defaultError.setErrors(errorsMap);
        return defaultError;
    }
}
