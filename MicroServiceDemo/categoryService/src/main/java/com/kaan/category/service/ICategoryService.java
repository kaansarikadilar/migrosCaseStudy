package com.kaan.category.service;

import com.kaan.category.entity.Category;
import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ICategoryService {

    public ResponseCategory saveCategory(@RequestBody RequestCategory requestCategory);

    public List<ResponseCategory> listCategory();

    public ResponseCategory getCategoryById(@PathVariable(name ="id") Long id);

    public String deleteCategory(@PathVariable Long id);

    public ResponseCategory updateCategory(@PathVariable (name = "id")Long id, @RequestBody RequestCategory requestCategory);

    }
