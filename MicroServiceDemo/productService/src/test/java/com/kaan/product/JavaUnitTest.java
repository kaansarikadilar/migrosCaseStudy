//package com.kaan.product;
//
//
//
//import com.kaan.category.response.ResponseCategory;
//import com.kaan.product.Entity.Product;
//import com.kaan.product.Feign.CategoryInterface;
//import com.kaan.product.ProductResponce.ProductRequest;
//import com.kaan.product.ProductResponce.ProductResponce;
//import com.kaan.product.Repository.ProductRepository;
//import com.kaan.product.Service.Impl.ProductServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class JavaUnitTest{
//
//        private ProductRepository productRepository;
//        private ProductServiceImpl productService;
//        private CategoryInterface categoryInterface;
//
//        @BeforeEach
//        void setup() {
//            productRepository = Mockito.mock(ProductRepository.class);
//            categoryInterface = Mockito.mock(CategoryInterface.class);
//            productService = new ProductServiceImpl(productRepository, categoryInterface, null); // Barcode ve Category feign null
//        }
//        @Test
//        void testSaveProductWithoutBarcode() {
//
//        ProductRequest request = new ProductRequest("SampleProduct", Enums.KILOGRAM, "SampleBrand", 1L);
//
//        // CategoryInterface mock
//        ResponseCategory mockCategory = new ResponseCategory(1L, "SampleCategory", "MY");
//        when(categoryInterface.getCategoryById(any(Long.class))).thenReturn(mockCategory);
//
//        // ProductRepository mock
//        Product savedProduct = new Product();
//        savedProduct.setId(1L);
//        savedProduct.setProductName("SampleProduct");
//        savedProduct.setBrand("SampleBrand");
//        savedProduct.setUnit(Enums.KILOGRAM);
//        savedProduct.setCategoryId(1L);
//
//        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
//
//        // Act
//        ProductResponce result = productService.saveProduct(request);
//
//        // Assert
//        assertNotNull(result.getId());
//        assertEquals("SampleProduct", result.getProductName());
//        assertEquals("SampleBrand", result.getBrand());
//        assertEquals(Enums.KILOGRAM, result.getUnit());
//    }
//
//        @Test
//        void testGetAllProducts() {
//            Product p1 = new Product();
//            p1.setId(1L);
//            p1.setProductName("Product1");
//            Product p2 = new Product();
//            p2.setId(2L);
//            p2.setProductName("Product2");
//
//            when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
//
//            List<ProductResponce> products = productService.getAllProducts();
//
//            assertEquals(2, products.size());
//            assertEquals("Product1", products.get(0).getProductName());
//            assertEquals("Product2", products.get(1).getProductName());
//        }
//
//        @Test
//        void testGetProductById() {
//            Product p1 = new Product();
//            p1.setId(1L);
//            p1.setProductName("SampleProduct");
//
//            when(productRepository.findById(1L)).thenReturn(Optional.of(p1));
//
//            ProductResponce result = productService.getProductById(1L);
//
//            assertNotNull(result);
//            assertEquals("SampleProduct", result.getProductName());
//        }
//    @Test
//    void testUpdateProduct() {
//        ProductRequest saved = new ProductRequest();
//        saved.setProductName("SampleProduct");
//        saved.setBrand("SampleBrand");
//        saved.setUnit(Enums.KILOGRAM);
//
//        Product updated = new Product();
//        updated.setId(1L);
//        updated.setProductName("UpdatedProduct");
//        updated.setBrand("UpdatedBrand");
//        updated.setUnit(Enums.ADET);
//
//        when(productRepository.save(any(Product.class))).thenReturn(updated);
//
//        ProductResponce result = productService.updateProductById(1L,saved);
//
//        assertEquals(1L, result.getId());
//        assertEquals("UpdatedProduct", result.getProductName());
//        assertEquals("UpdatedBrand", result.getBrand());
//        assertEquals(Enums.ADET, result.getUnit());
//    }
//        @Test
//        void testDeleteProduct() {
//            Long id = 1L;
//
//            doNothing().when(productRepository).deleteById(id);
//
//            productService.deleteProductById(id);
//
//            verify(productRepository, times(1)).deleteById(id);
//        }
//    }
