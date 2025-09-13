package com.kaan.product.exception;

import com.kaan.category.entity.Category;
import com.kaan.category.exeption.DefaultErrorValidation;
import com.kaan.category.repository.CategoryRepository;
import com.kaan.product.Feign.CategoryInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.kaan.category.response.ResponseCategory;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.*;
import java.util.List;

@ControllerAdvice
public class ProductValidException {

    private final CategoryInterface categoryInterface;

    public ProductValidException(CategoryInterface categoryInterface) {
        this.categoryInterface = categoryInterface;
    }


    //request te hata olunca postman de o hatayı göstermek için kullanılır

    private List<String> addMapValue(List<String> list, String newValue){
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
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<DefaultErrorValidation> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        Map<String,List<String>> errorsMap = new HashMap<>();

        String errorMessage = ex.getMostSpecificCause().getMessage();

        if (errorMessage != null && errorMessage.contains("not one of the values accepted for Enum class")) {
            errorsMap.put("enumValue", List.of("You can only enter these values from the enum class: KILOGRAM, ADET"));
        } else {
            errorsMap.put("requestBody", List.of("Geçersiz JSON formatı"));
        }
        return ResponseEntity.badRequest().body(createDefaultErrors(errorsMap));
    }
    @ExceptionHandler(BaseException.NoCategoryFoundException.class)
    public ResponseEntity<DefaultErrorValidation> handleCategoryNotFound(BaseException.NoCategoryFoundException ex) {
        Map<String, List<String>> errorsMap = Map.of("categoryId", List.of(ex.getMessage()));
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
