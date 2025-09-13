package com.kaan.product.Feign;


import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "categoryService",url = "http://localhost:8082")
public interface CategoryInterface {

    @PostMapping("/rest/api/category/save")
    ResponseCategory saveCategory(@RequestBody RequestCategory requestCategory);

    @GetMapping("/rest/api/category/list")
    List<ResponseCategory> listCategory();

    @GetMapping("/rest/api/category/list/{id}")
    ResponseCategory getCategoryById(@PathVariable(name ="id") Long id);

    @DeleteMapping("/rest/api/category/delete/{id}")
    String deleteCategory(@PathVariable("id") Long id);

    @PutMapping("/rest/api/category/update/{id}")
    ResponseCategory updateCategory(@PathVariable("id") Long id, @RequestBody RequestCategory requestCategory);
}
