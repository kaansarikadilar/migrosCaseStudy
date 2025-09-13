package com.kaan.categoryService;

import com.kaan.category.entity.Category;
import com.kaan.category.repository.CategoryRepository;
import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import com.kaan.category.service.Impl.CategoryServiceImpl;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryUnitTest {

    @Mock
    private CategoryRepository categoryRepository;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }
    @Test
    void listCategories_returnsCategories() {
        List<Category> mockCategories = List.of(new Category(1L, "Test", "MY"));
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        var result = categoryService.listCategory();

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getCategoryName());
        verify(categoryRepository).findAll();
    }
    @Test
    void getCategoryById_returnsCategory() {
        ResponseCategory mockCategory = new ResponseCategory(1L, "Test", "MY");
        Category category = new Category();
        BeanUtils.copyProperties(mockCategory, category);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        ResponseCategory result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getCategoryName());
        assertEquals("MY", result.getCategoryCode());
        verify(categoryRepository).findById(1L);
    }
    @Test
    void deleteCategoryById_returnsCategory() {
        ResponseCategory mockCategory = new ResponseCategory(1L, "Test", "MY");
        Category category = new Category();
        BeanUtils.copyProperties(mockCategory, category);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        categoryService.deleteCategory(1L);

        assertNotNull(category);
    }
    @Test
    void saveCategory_savesAndReturnsCategory() {
        // Arrange
        RequestCategory request = new RequestCategory();
        request.setCategoryName("Test");
        request.setCategoryCode("MY");

        Category categoryEntity = new Category();
        categoryEntity.setId(1L);
        categoryEntity.setCategoryName("Test");
        categoryEntity.setCategoryCode("MY");

        when(categoryRepository.save(any(Category.class))).thenReturn(categoryEntity);

        ResponseCategory saved = categoryService.saveCategory(request);

        assertNotNull(saved);
        assertEquals("Test", saved.getCategoryName());
        assertEquals("MY", saved.getCategoryCode());

        verify(categoryRepository).save(any(Category.class));
    }
    @Test
    void updateCategory_savesAndReturnsCategory() {
        RequestCategory updating = new RequestCategory();
        updating.setCategoryName("Updated");
        updating.setCategoryCode("UP");
        Category existing = new Category();
        existing.setId(1L);
        existing.setCategoryName("Existing");
        existing.setCategoryCode("EX");
        Category updated = new Category();
        updated.setId(1L);
        updated.setCategoryName(updating.getCategoryName());
        updated.setCategoryCode(updating.getCategoryCode());

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(any(Category.class))).thenReturn(updated);

        ResponseCategory result = categoryService.updateCategory(1L, updating);
        assertNotNull(result);
        assertEquals("Updated", result.getCategoryName());
        assertEquals("UP", result.getCategoryCode());
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(any(Category.class));


    }
}
