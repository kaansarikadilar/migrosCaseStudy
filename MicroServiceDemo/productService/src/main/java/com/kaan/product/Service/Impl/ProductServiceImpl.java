package com.kaan.product.Service.Impl;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.entity.Barcode;
import com.kaan.category.exeption.BaseException;
import com.kaan.category.exeption.ErrorMessage;
import com.kaan.category.exeption.messageType;
import com.kaan.category.response.ResponseCategory;
import com.kaan.product.Entity.Product;
import com.kaan.product.Feign.BarcodeInterface;
import com.kaan.product.Mapper.BarcodeToResponce;
import com.kaan.product.Mapper.ProductRequestToProduct;
import com.kaan.product.Mapper.ProductToProductResponse;
import com.kaan.product.ProductResponce.ProductRequest;
import com.kaan.product.ProductResponce.ProductResponce;
import com.kaan.product.Repository.ProductRepository;
import com.kaan.product.Feign.CategoryInterface;
import com.kaan.product.Service.IProductService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryInterface categoryInterface;
    private final BarcodeInterface barcodeInterface;

    @Override
    public ProductResponce saveProduct(ProductRequest productRequest){
        try {
            Product product = new Product();
            ResponseCategory responseCategory = categoryInterface.getCategoryById(productRequest.getCategoryId());

            new ProductRequestToProduct(productRequest,product);
            String randomPart = String.format("%03d", new Random().nextInt(899) + 100);
            product.setProductCode(responseCategory.getCategoryCode() + randomPart);

            Product savedProduct = productRepository.save(product);

            RequestBarcode request = new RequestBarcode();
            request.setProductId(savedProduct.getId());
            request.setCategoryCode(responseCategory.getCategoryCode());

            ResponceBarcode responceBarcode = barcodeInterface.generateBarcode(request);
            savedProduct.setBarcodeId(responceBarcode.getCode());

            productRepository.save(savedProduct);

            ProductResponce productResponce = new ProductResponce();
            new ProductToProductResponse(product,productResponce);
            Barcode barcodeOptional = barcodeInterface.findByProductId(product.getId());
            new BarcodeToResponce(barcodeOptional,productResponce);
            return productResponce;
        }
        catch (FeignException e) {
            if (e.status() == 400) {
                List<String> availableCategories = categoryInterface.listCategory()
                        .stream()
                        .map(ResponseCategory::getCategoryName)
                        .toList();
                throw new com.kaan.product.exception.BaseException.NoCategoryFoundException("Category not found: " + productRequest.getCategoryId()
                        + ". Available categories: " + availableCategories);
            }
            throw e;}}
    @Override
    public ProductResponce updateProductById(Long id, ProductRequest productRequest) {
        try {
            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isEmpty()) {
                throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST, id.toString()));
            }
            Product product = productOptional.get();

            new ProductRequestToProduct(productRequest, product);
            String randomPart = String.format("%03d", new Random().nextInt(899) + 100);
            ResponseCategory responseCategory = categoryInterface.getCategoryById(productRequest.getCategoryId());
            product.setProductCode(responseCategory.getCategoryCode() + randomPart);

            Product savedProduct = productRepository.save(product);

            RequestBarcode requestBarcode = new RequestBarcode();
            requestBarcode.setProductId(product.getId());
            requestBarcode.setCategoryCode(responseCategory.getCategoryCode());

            Barcode existingBarcode = barcodeInterface.findByProductId(product.getId());
            if (existingBarcode != null) {
                BeanUtils.copyProperties(existingBarcode, requestBarcode);
            }

            Long barcodeId = existingBarcode != null ? existingBarcode.getId() : null;
            ResponceBarcode updatedBarcode = barcodeInterface.updateBarcode(barcodeId, requestBarcode);

            savedProduct.setBarcodeId(updatedBarcode.getCode());
            productRepository.save(savedProduct);

            ProductResponce productResponce = new ProductResponce();//response dönüştürme
            new ProductToProductResponse(savedProduct,productResponce);
            new BarcodeToResponce(barcodeInterface.findByProductId(product.getId()), productResponce);

            return productResponce;
        }
        catch (FeignException e) {
            if (e.status() == 400) {
                List<String> availableCategories = categoryInterface.listCategory()
                        .stream()
                        .map(ResponseCategory::getCategoryName)
                        .toList();
                throw new com.kaan.product.exception.BaseException.NoCategoryFoundException("Category not found: " + productRequest.getCategoryId()
                        + ". Available categories: " + availableCategories);
            }throw e;}
    }
    @Override//paging ile sayfa sayfa data vermek çok daha verimli
    public List<ProductResponce> getAllProducts() {
        List<Product> productOptional = productRepository.findAll();
        List<Barcode> barcodeOptional = barcodeInterface.getAllBarcodes();
        List<ProductResponce> productResponceList = new ArrayList<>();

        if (productOptional.isEmpty()) {
            throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST));
        }
        for (Product product : productOptional) {
            ProductResponce productResponce = new ProductResponce();
            new ProductToProductResponse(product, productResponce);
            for (Barcode barcode : barcodeOptional) {//tek for a dönüştürülebilir
                if (barcode.getCode().equals(product.getBarcodeId())) {
                    new BarcodeToResponce(barcode,productResponce);
                    break;}}
            productResponceList.add(productResponce);
        }
        return productResponceList;
    }
    @Override
    public ProductResponce getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        ProductResponce productResponce = new ProductResponce();

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            Barcode barcodeOptional = barcodeInterface.findByProductId(product.getId());
            if (barcodeOptional == null) {
                barcodeOptional = new Barcode();
            }
            new ProductToProductResponse(product,productResponce);//product to productResponse
            new BarcodeToResponce(barcodeOptional,productResponce);//barcode to productResponse
        }
        if(productOptional.isEmpty()){
            throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST,id.toString()));
        }
        return productResponce;
    }
    @Override
    public String deleteProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Barcode barcode = barcodeInterface.findByProductId(product.getId());
            if (barcode != null) {
                barcodeInterface.deleteBarcodeById(barcode.getId());
            }
            productRepository.deleteById(product.getId());
            return "Product Deleted Successfully";
        }
        throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST,id.toString()));
    }
}
