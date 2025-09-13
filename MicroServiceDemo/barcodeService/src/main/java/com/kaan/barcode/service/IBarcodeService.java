package com.kaan.barcode.service;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.product.ProductResponce.BarcodeResponce;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBarcodeService {

    public List<ResponceBarcode> getAllBarcodes();

    public ResponceBarcode getBarcodeById(@PathVariable(name = "id") Long id);

    public RequestBarcode generateBarcodeCode();

    public RequestBarcode generateCashCode();

    public ResponceBarcode generateBarcode(@RequestBody RequestBarcode request);

    public ResponceBarcode updateBarcode(@PathVariable(name = "id") Long id, @RequestBody RequestBarcode request);

    public boolean deleteBarcodeById(@PathVariable(name = "id") Long id);

    public BarcodeResponce findByProductId(Long productId);

    public ResponceBarcode getBarcodeByCode(@PathVariable(name = "Code") String Code);

    public RequestBarcode generateScaleCode();

    public ResponceBarcode saveBarcode(@RequestBody RequestBarcode barcodeRequest);
}
