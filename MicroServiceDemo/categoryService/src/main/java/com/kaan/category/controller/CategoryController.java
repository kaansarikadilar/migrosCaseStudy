package com.kaan.category.controller;

import com.kaan.category.entity.Category;
import com.kaan.category.repository.CategoryRepository;
import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import com.kaan.category.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/api/category")
public class CategoryController {

    private final ICategoryService categoryService;
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //dto larda prefix i sağa al
    @PostMapping(path = "/save")
    public ResponseCategory saveCategory(@RequestBody @Valid RequestCategory requestCategory) {
        return categoryService.saveCategory(requestCategory);
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteCategory(@PathVariable (name = "id")Long id) {
        return categoryService.deleteCategory(id);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseCategory updateCategory(@PathVariable(name = "id")Long id,@Valid @RequestBody RequestCategory requestCategory) {
        return categoryService.updateCategory(id,requestCategory);
    }


}
