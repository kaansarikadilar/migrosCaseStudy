package com.kaan.product.Mapper;

import com.kaan.product.Entity.Product;
import com.kaan.product.ProductResponce.ProductRequest;

public class ProductRequestToProduct extends Product {
    public ProductRequestToProduct(ProductRequest productRequest , Product product) {
        product.setProductName(productRequest.getProductName());
        product.setCategoryId(productRequest.getCategoryId());
        product.setBrand(productRequest.getBrand());
        product.setUnit(productRequest.getUnit());
    }
}
