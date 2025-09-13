package com.kaan.category.controller;

import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import com.kaan.category.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/api/category")
public class CategoryLosdingController {

    private final ICategoryService categoryService;
    public CategoryLosdingController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/list")
    public List<ResponseCategory> listCategory() {
        return categoryService.listCategory();
    }

    @GetMapping(path = "/list/{id}")
    public ResponseCategory getCategoryById(@PathVariable(name ="id") Long id) {
        return categoryService.getCategoryById(id);
    }

}
