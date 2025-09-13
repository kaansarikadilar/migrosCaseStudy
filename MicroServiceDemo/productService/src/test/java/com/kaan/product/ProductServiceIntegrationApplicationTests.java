package com.kaan.product;

import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import com.kaan.product.Entity.Product;
import com.kaan.product.Feign.BarcodeInterface;
import com.kaan.product.Feign.CategoryInterface;
import com.kaan.product.ProductResponce.ProductResponce;
import com.kaan.product.Repository.ProductRepository;
import com.kaan.product.Service.Impl.ProductServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Nested
@RequiredArgsConstructor
@SpringBootTest(properties = "spring.profiles.active=test")
@Transactional
class ProductServiceIntegrationApplicationTests {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @MockitoBean
    private BarcodeInterface barcodeInterface;

    @MockitoBean
    private CategoryInterface categoryInterface;

    @BeforeEach
    void setup() {
        RequestCategory rc1 = new RequestCategory();
        rc1.setCategoryName("SampleCategory");
        rc1.setCategoryCode("MY");
        rc1.setCategoryName("SampleCategory");

        when(categoryInterface.getCategoryById(Mockito.anyLong()))
                .thenReturn(new ResponseCategory(1L, "SampleCategory", "MY"));
    }
    @Test
    @Transactional
    void testGetAllProducts() {
        List<ProductResponce> products = productService.getAllProducts();
        for (ProductResponce productResponce : products) {
            productResponce.setProductName(productResponce.getProductName());
            productResponce.setBrand(productResponce.getBrand());
            productResponce.setUnit(productResponce.getUnit());
            productResponce.setProductCode(productResponce.getProductCode());
        }
        assertEquals(16, products.size());
        assertEquals("İstavrit", products.get(0).getProductName());
    }
    @Test
    void testGetProductById() {
        ProductResponce productResponce = productService.getProductById(654L);
        productResponce.setProductName("TestProduct");
        assertEquals("TestProduct", productResponce.getProductName());

    }
    @Test
    @Transactional
    void testDeleteProductDirectly() {
        Product product = new Product();
        product.setId(654L);
        product.setProductName("Test Product");
        product.setProductCode("TEST001");
        Product savedProduct = productRepository.save(product);
        productRepository.deleteById(savedProduct.getId());

        assertFalse(productRepository.existsById(product.getId()));
    }
    @Test
    @Transactional
    void testSaveProductWithoutBarcode() {
        Product request = new Product();
        request.setProductName("SampleProduct");
        request.setBrand("SampleBrand");
        request.setUnit(Enums.KILOGRAM);
        request.setCategoryId(1L);

        Product saved = productRepository.save(request);

        // Assert
        assertNotNull(saved.getId(), "Product DB'ye kaydedilmiş olmalı");
        assertEquals("SampleProduct", saved.getProductName());
        assertEquals("SampleBrand", saved.getBrand());
        assertEquals(Enums.KILOGRAM, saved.getUnit());
        assertEquals(1L, saved.getCategoryId());
    }
    @Test
    @Transactional
    void testUpdateProductWithoutBarcode() {
        Product request = new Product();
        request.setProductName("SampleProduct");
        request.setBrand("SampleBrand");
        request.setUnit(Enums.KILOGRAM);
        request.setCategoryId(1L);

        Product saved = productRepository.save(request);

        saved.setProductName("UpdatedProduct");
        saved.setBrand("UpdatedBrand");
        saved.setUnit(Enums.ADET); //
        Product updated = productRepository.save(saved);

        assertNotNull(updated.getId(), "Updated Product DB'de olmalı");
        assertEquals(saved.getId(), updated.getId(), "ID değişmemeli");
        assertEquals("UpdatedProduct", updated.getProductName());
        assertEquals("UpdatedBrand", updated.getBrand());
        assertEquals(Enums.ADET, updated.getUnit());
        assertEquals(1L, updated.getCategoryId());
    }
}