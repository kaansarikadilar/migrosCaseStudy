package com.kaan.product.Mapper;

import com.kaan.product.Entity.Product;
import com.kaan.product.ProductResponce.ProductResponce;

public class ProductToProductResponse extends Product {
    public ProductToProductResponse(Product product , ProductResponce productResponce) {
        productResponce.setProductName(product.getProductName());
        productResponce.setProductCode(product.getProductCode());
        productResponce.setId(product.getId());
        productResponce.setBrand(product.getBrand());
        productResponce.setUnit(product.getUnit());
    }
}
