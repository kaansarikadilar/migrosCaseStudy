package com.kaan.product;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.barcodeTypes;
import com.kaan.category.response.RequestCategory;
import com.kaan.category.response.ResponseCategory;
import com.kaan.product.Entity.Product;
import com.kaan.product.Feign.BarcodeInterface;
import com.kaan.product.Feign.CategoryInterface;
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

        Mockito.when(categoryInterface.getCategoryById(Mockito.anyLong()))
                .thenReturn(new ResponseCategory(1L, "SampleCategory", "MY"));
    }
    @Test
    @Transactional
    void testGetAllProducts() {
        Product request = new Product();
        request.setUnit(Enums.KILOGRAM);
        request.setCategoryId(1L);
        request.setProductName("SampleProduct");
        request.setBrand("SampleBrand");
        Product savedProduct = productRepository.save(request);
        RequestBarcode requestBarcode = new RequestBarcode();
        requestBarcode.setProductId(savedProduct.getId());
        requestBarcode.setCategoryCode(requestBarcode.getCategoryCode());
        ResponceBarcode responceBarcode = new ResponceBarcode("123", barcodeTypes.NORMAL,null);
        savedProduct.setBarcodeId(responceBarcode.getCode());
        productRepository.save(savedProduct);

        List<Product> products = productRepository.findAll();
        for (Product productResponce : products) {
            productResponce.setProductName(productResponce.getProductName());
            productResponce.setBrand(productResponce.getBrand());
            productResponce.setUnit(productResponce.getUnit());
            productResponce.setProductCode(productResponce.getProductCode());
            productResponce.setBarcodeId(responceBarcode.getCode());
        }
        assertEquals(1, products.size());
        assertEquals("SampleProduct", products.get(0).getProductName());
    }
    @Test
    @Transactional
    void testGetProductByIdFromRepository() {
        // Arrange
        Product request = new Product();
        request.setUnit(Enums.KILOGRAM);
        request.setCategoryId(1L);
        request.setProductName("SampleProduct");
        request.setBrand("SampleBrand");
        Product savedProduct = productRepository.save(request);

        // Act
        Product fetchedProduct = productRepository.findById(savedProduct.getId())
                .orElse(null);

        // Assert
        assertNotNull(fetchedProduct);
        assertEquals(savedProduct.getId(), fetchedProduct.getId());
        assertEquals("SampleProduct", fetchedProduct.getProductName());
    }
    @Test
    @Transactional
    void testDeleteProductByIdFromRepository() {
        // Arrange
        Product request = new Product();
        request.setUnit(Enums.KILOGRAM);
        request.setCategoryId(1L);
        request.setProductName("SampleProduct");
        request.setBrand("SampleBrand");
        Product savedProduct = productRepository.save(request);

        Long id = savedProduct.getId();
        assertTrue(productRepository.findById(id).isPresent());

        // Act - delete
        productRepository.deleteById(id);

        // Assert
        assertFalse(productRepository.findById(id).isPresent());
    }
    @Test
    @Transactional
    void testSaveProductWithoutBarcode() {
        // Arrange: Product nesnesi oluştur
        Product request = new Product();
        request.setProductName("SampleProduct");
        request.setBrand("SampleBrand");
        request.setUnit(Enums.KILOGRAM);
        request.setCategoryId(1L); // Category id veriyoruz, ama categoryInterface kullanmıyoruz

        // Act: Repository üzerinden kaydet
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
        // Arrange: Önce product kaydet
        Product request = new Product();
        request.setProductName("SampleProduct");
        request.setBrand("SampleBrand");
        request.setUnit(Enums.KILOGRAM);
        request.setCategoryId(1L);

        Product saved = productRepository.save(request);

        // Act: Product'u güncelle
        saved.setProductName("UpdatedProduct");
        saved.setBrand("UpdatedBrand");
        saved.setUnit(Enums.ADET); // Örnek farklı bir enum
        Product updated = productRepository.save(saved); // save tekrar update işlevi görür

        // Assert
        assertNotNull(updated.getId(), "Updated Product DB'de olmalı");
        assertEquals(saved.getId(), updated.getId(), "ID değişmemeli");
        assertEquals("UpdatedProduct", updated.getProductName());
        assertEquals("UpdatedBrand", updated.getBrand());
        assertEquals(Enums.ADET, updated.getUnit());
        assertEquals(1L, updated.getCategoryId());
    }
}