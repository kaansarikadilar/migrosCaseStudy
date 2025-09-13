package com.kaan.product.Feign;

import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.product.ProductResponce.BarcodeResponce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "barcodeService",url = "http://localhost:8081")
public interface BarcodeInterface {

    @GetMapping(path = "/rest/api/controller/generateNormal")
    RequestBarcode generateBarcodeCode();

    @GetMapping(path = "/rest/api/controller/list")
    List<ResponceBarcode> getAllBarcodes();

    @GetMapping(path = "/rest/api/controller/generateCash")
    RequestBarcode generateCashCode();

    @GetMapping("/rest/api/controller/findByProduct/{productId}")
    BarcodeResponce findByProductId(@PathVariable("productId") Long productId);

    @GetMapping(path = "/rest/api/controller/list/{id}")
    ResponceBarcode getBarcodeById(@PathVariable(name = "id") Long id);

    @DeleteMapping(path = "/rest/api/controller/delete/{id}")
    boolean deleteBarcodeById(@PathVariable(name = "id") Long id);

    @GetMapping(path = "/rest/api/controller/generateScale")
    RequestBarcode generateScaleCode();

    @PostMapping(path = "/rest/api/controller/save")
    ResponceBarcode saveBarcode(@RequestBody RequestBarcode barcodeRequest);

    @PostMapping(path ="/rest/api/controller/generate")
    ResponceBarcode generateBarcode(@RequestBody RequestBarcode request);

    @PostMapping(path = "/rest/api/controller/update/{id}")
    ResponceBarcode updateBarcode(@PathVariable(name = "id") Long id, @RequestBody RequestBarcode request);
}
