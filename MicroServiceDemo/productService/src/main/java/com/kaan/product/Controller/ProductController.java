package com.kaan.product.Controller;

import com.kaan.product.ProductResponce.ProductRequest;
import com.kaan.product.ProductResponce.ProductResponce;
import com.kaan.product.Service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping(path = "/save")
    public ProductResponce saveProduct(@RequestBody @Valid ProductRequest productRequest){
        return productService.saveProduct(productRequest);
    }
    @GetMapping(path = "/list")
    public List <ProductResponce> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping(path = "/list/{id}")
    public ProductResponce getProductById(@PathVariable(name = "id") Long id){
        return productService.getProductById(id);
    }
    @DeleteMapping(path = "/delete/{id}")
    public String deleteProductById(@PathVariable(name = "id") Long id){
        return productService.deleteProductById(id);
    }
    @PutMapping(path = "/update/{id}")
    public ProductResponce updateProductById(@PathVariable(name= "id")Long id,@RequestBody @Valid ProductRequest productRequest){
        return productService.updateProductById(id,productRequest);
    }
}
