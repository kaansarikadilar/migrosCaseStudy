package com.kaan.product.Service;

import com.kaan.product.ProductResponce.ProductRequest;
import com.kaan.product.ProductResponce.ProductResponce;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProductService {

    public ProductResponce saveProduct(@RequestBody ProductRequest productRequest);

    public List<ProductResponce> getAllProducts();

    public ProductResponce getProductById(@PathVariable(name = "id") Long id);

    public String deleteProductById(@PathVariable(name = "id") Long id);

    public ProductResponce updateProductById(@PathVariable(name= "id")Long id, @RequestBody ProductRequest productRequest);
}
