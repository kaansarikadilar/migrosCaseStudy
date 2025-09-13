package com.kaan.barcode.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import com.kaan.product.ProductResponce.ProductResponce;

@FeignClient(name = "productService",url = "http://localhost:8083")
public interface productInterface {
    @GetMapping(path = "/rest/api/product/list/{id}")
    ProductResponce getProductById(@PathVariable(name = "id") Long id);
}
