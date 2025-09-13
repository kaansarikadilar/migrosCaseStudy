package com.kaan.category.service.Impl;

import com.kaan.category.entity.Category;
import com.kaan.category.exeption.BaseException;
import com.kaan.category.exeption.ErrorMessage;
import com.kaan.category.exeption.messageType;
import com.kaan.category.repository.CategoryRepository;
import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import com.kaan.category.service.ICategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseCategory saveCategory(RequestCategory requestCategory) {
        Category category = new Category();
        ResponseCategory responseCategory = new ResponseCategory();
        BeanUtils.copyProperties(requestCategory,category);
        categoryRepository.save(category);

        BeanUtils.copyProperties(category,responseCategory);
        return responseCategory;
    }

    @Override
    public List<ResponseCategory> listCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<ResponseCategory> responseCategoryList = new ArrayList<>();
        if (categoryList.isEmpty()) {
            throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST));
        }
        for (Category category : categoryList) {
            ResponseCategory responseCategory = new ResponseCategory();
            BeanUtils.copyProperties(category,responseCategory);
            responseCategoryList.add(responseCategory);
        }
        return responseCategoryList;
    }

    @Override
    public ResponseCategory getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        ResponseCategory responseCategory = new ResponseCategory();
        if (category.isPresent()) {
            BeanUtils.copyProperties(category.get(),responseCategory);
            return responseCategory;
        }
        throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST,id.toString()));
    }

    @Override
    public String deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()){
            throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST,id.toString()));
        }
        Category category = optionalCategory.get();
        categoryRepository.delete(category);
        return "The category with the id : "+id+" has been deleted";
    }

    @Override
    public ResponseCategory updateCategory(Long id, RequestCategory requestCategory) {
      Optional<Category> optionalCategory = categoryRepository.findById(id);
      ResponseCategory updatedCategory = new ResponseCategory();
      if(!optionalCategory.isPresent()){
          throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST,id.toString()));
      }
          Category updatingCategory1 = optionalCategory.get();
          BeanUtils.copyProperties(requestCategory,updatingCategory1);
          Category updatingCategory2 = categoryRepository.save(updatingCategory1);
          BeanUtils.copyProperties(updatingCategory2,updatedCategory);
          return updatedCategory;

    }
}
