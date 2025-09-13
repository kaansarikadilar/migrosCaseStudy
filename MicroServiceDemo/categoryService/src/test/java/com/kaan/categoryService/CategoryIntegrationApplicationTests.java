package com.kaan.categoryService;

import com.kaan.category.CategoryServiceApplication;
import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import com.kaan.category.service.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CategoryServiceApplication.class)
class CategoryIntegrationApplicationTests {

    private final ICategoryService categoryService;

    @Autowired
    CategoryIntegrationApplicationTests(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @BeforeEach
    public void setUp() {
        System.out.println("Working");
    }
    @Test
    public void testGetCategoryById() {
        var responseCategory = categoryService.getCategoryById(4L) ;
        if (responseCategory != null) {
            System.out.println("This categories id : "+responseCategory.getId());
            System.out.print("This categories name : "+responseCategory.getCategoryName()+ "   ");
        }
    }
    @Test
    public void testListCategories() {
        List<ResponseCategory> optionalList = categoryService.listCategory();
        if (optionalList != null) {
            for (ResponseCategory responseCategory : optionalList) {
                System.out.println("This categories id : "+responseCategory.getId());
                System.out.println("This categories name : "+responseCategory.getCategoryName()+ "   ");
            }
        }
    }
    @Transactional
    @Test
    public void testSaveCategory() {
        var requestCategory = new RequestCategory();
        requestCategory.setCategoryName("Test");
        requestCategory.setCategoryCode("MY");
        ResponseCategory saved = categoryService.saveCategory(requestCategory);

        assertNotNull(saved.getId(),"Error saving category");
        assertEquals("Test",saved.getCategoryName());

        ResponseCategory fromDb = categoryService.getCategoryById(saved.getId());
        assertNotNull(fromDb,"Error getting category");
        assertEquals(saved.getCategoryName(),fromDb.getCategoryName());
    }
    @Transactional
    @Test
    public void testDeleteCategory() {
        var category = categoryService.getCategoryById(8L);
        categoryService.deleteCategory(category.getId());
        assertNotNull(category,"Error deleting category");
    }
    @Transactional
    @Test
    public void testUpdateCategory() {
        ResponseCategory original = categoryService.getCategoryById(5L);
        assertNotNull(original,"Error getting category");

        RequestCategory temp = new RequestCategory();
        temp.setCategoryName(original.getCategoryName() + "Updated");
        temp.setCategoryCode(original.getCategoryCode());
        ResponseCategory savedTemp = categoryService.updateCategory(original.getId(),temp);

        assertNotNull(savedTemp,"Error saving category");
        assertEquals(savedTemp.getCategoryName(),original.getCategoryName()+"Updated");
        assertEquals(savedTemp.getCategoryCode(),original.getCategoryCode());

        ResponseCategory fromDb = categoryService.getCategoryById(savedTemp.getId());
        assertNotNull(fromDb,"Error getting category");
        assertEquals(savedTemp.getCategoryName(),fromDb.getCategoryName());
    }
}