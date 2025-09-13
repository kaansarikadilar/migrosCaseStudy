package com.kaan.barcode.service;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.entity.Barcode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBarcodeService {

    public RequestBarcode generateBarcodeCode();

    public RequestBarcode generateCashCode();

    public Barcode getBarcodeById(@PathVariable(name = "id") Long id);

    public ResponceBarcode generateBarcode(@RequestBody RequestBarcode request);

    public ResponceBarcode updateBarcode(@PathVariable(name = "id") Long id,@RequestBody RequestBarcode request);

    public boolean deleteBarcodeById(@PathVariable(name = "id") Long id);

    public Barcode findByProductId(Long productId);

    public Barcode getBarcodeByCode(@PathVariable(name = "Code") String Code);

    public List<Barcode> getAllBarcodes();

    public RequestBarcode generateScaleCode();

    public RequestBarcode saveBarcode(@RequestBody RequestBarcode barcodeRequest);
}
