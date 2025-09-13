package com.kaan.product;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.product.Entity.Product;
import com.kaan.product.Feign.BarcodeInterface;
import com.kaan.product.Feign.CategoryInterface;
import com.kaan.product.ProductResponce.BarcodeResponce;
import com.kaan.product.ProductResponce.ProductResponce;
import com.kaan.product.Repository.ProductRepository;
import com.kaan.product.Service.Impl.ProductServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class JavaUnitTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @Mock
    private BarcodeInterface barcodeInterface;

    @BeforeEach
    void setup() {
        productRepository = Mockito.mock(ProductRepository.class);
        CategoryInterface categoryInterface = Mockito.mock(CategoryInterface.class);
        barcodeInterface = Mockito.mock(BarcodeInterface.class);
        productService = new ProductServiceImpl(productRepository, categoryInterface, barcodeInterface); // Barcode ve Category feign null
    }

    @Test
    void testSaveProductWithBarcode() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setProductName("test");

        RequestBarcode b1 = new RequestBarcode();
        b1.setCode("123");
        ResponceBarcode r1 = new ResponceBarcode();
        r1.setCode(b1.getCode());

        when(productRepository.save(p1)).thenReturn(p1);
        when(barcodeInterface.saveBarcode(b1)).thenReturn(r1);

        assertNotNull(p1.getId());
        assertEquals("test", p1.getProductName());
        assertEquals(b1.getCode(), r1.getCode());
    }
    @Test
    void testUpdateProduct() {
            Product existingProduct = new Product();
            existingProduct.setId(1L);
            existingProduct.setProductName("SampleProduct");
            existingProduct.setBrand("SampleBrand");
            existingProduct.setUnit(Enums.KILOGRAM);

            RequestBarcode barcodeRequest = new RequestBarcode();
            barcodeRequest.setCode("123456");

            ResponceBarcode barcodeResponse = new ResponceBarcode();
            barcodeResponse.setCode(barcodeRequest.getCode());

            when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
            when(barcodeInterface.saveBarcode(any(RequestBarcode.class))).thenReturn(barcodeResponse);

            existingProduct.setProductName("UpdatedProduct");
            existingProduct.setBrand("UpdatedBrand");
            existingProduct.setUnit(Enums.ADET);

            ResponceBarcode savedBarcode = barcodeInterface.saveBarcode(barcodeRequest);
            String barcodeCode = savedBarcode.getCode();

            assertEquals("UpdatedProduct", existingProduct.getProductName());
            assertEquals("UpdatedBrand", existingProduct.getBrand());
            assertEquals(Enums.ADET, existingProduct.getUnit());
            assertEquals("123456", barcodeCode);

            verify(barcodeInterface, times(1)).saveBarcode(any(RequestBarcode.class));
    }
    @Test
    void testGetAllProducts() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setProductName("Product1");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setProductName("Product2");
        List<Product> productList = List.of(p1, p2);
        when(productRepository.findAll()).thenReturn(productList);
        for (Product productResponce : productList) {
            productResponce.setProductName(productResponce.getProductName());
            productResponce.setBrand(productResponce.getBrand());
            productResponce.setUnit(productResponce.getUnit());
            productResponce.setProductCode(productResponce.getProductCode());
        }
        assertEquals(2, productList.size());
        assertEquals("Product1", productList.get(0).getProductName());
        assertEquals("Product2", productList.get(1).getProductName());
    }

    @Test
    void testGetProductById() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setProductName("SampleProduct");

        BarcodeResponce b1 = new BarcodeResponce();
        b1.setCode("123456");

        when(productRepository.findById(1L)).thenReturn(Optional.of(p1));
        when(barcodeInterface.findByProductId(1L)).thenReturn(b1);

        ProductResponce result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("SampleProduct", result.getProductName());
        assertEquals("123456", result.getBarcodeCode());
    }
    @Test
    @Transactional
    void testDeleteProduct() {
        // Arrange
        Product p1 = new Product();
        p1.setId(1L);
        Product p2 = new Product();
        p2.setId(2L);


        when(productRepository.save(any(Product.class))).thenReturn(p1);

        doNothing().when(productRepository).deleteById(p1.getId());

        when(productRepository.existsById(p1.getId())).thenReturn(true, false);

        Product saved = productRepository.save(p1);
        assertTrue(productRepository.existsById(saved.getId())); //error check için saved p1 den p2 yap
        productRepository.deleteById(saved.getId());
        assertFalse(productRepository.existsById(saved.getId()));

        verify(productRepository, times(1)).deleteById(saved.getId());
    }
}
