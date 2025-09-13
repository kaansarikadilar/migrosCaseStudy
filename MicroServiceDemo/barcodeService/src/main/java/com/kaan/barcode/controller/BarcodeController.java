package com.kaan.barcode.controller;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.entity.Barcode;
import com.kaan.barcode.service.IBarcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/rest/api/controller")
public class BarcodeController {
    private final IBarcodeService barcodeService;

    @GetMapping(path = "/list")
    public List<Barcode> getAllBarcodes() {
        return barcodeService.getAllBarcodes();
    }

    @GetMapping(path = "/list/{id}")
    public Barcode getBarcodeById(@PathVariable(name = "id") Long id) {
        return barcodeService.getBarcodeById(id);
    }

    @GetMapping(path = "/list/{Code}")
    public Barcode getBarcodeByCode(@PathVariable(name = "Code") String Code) {
        return barcodeService.getBarcodeByCode(Code);
    }

    @DeleteMapping(path = "/delete/{id}")
    public boolean deleteBarcodeById(@PathVariable(name = "id") Long id) {
        return barcodeService.deleteBarcodeById(id);
    }

    @GetMapping(path = "/generateNormal")
    public RequestBarcode generateBarcodeCode() {
        return barcodeService.generateBarcodeCode();
    }

    @GetMapping("/findByProduct/{productId}")
    Barcode findByProductId(@PathVariable("productId") Long productId) {
        return barcodeService.findByProductId(productId);
    }

    @GetMapping(path = "/generateCash")
    public RequestBarcode generateCashCode() {
        return barcodeService.generateCashCode();
    }

    @GetMapping(path = "/generateScale")
    public RequestBarcode generateScaleCode() {
        return barcodeService.generateScaleCode();
    }

    @PostMapping(path = "/save")
    public RequestBarcode saveBarcode(@RequestBody RequestBarcode barcodeRequest) {
        return barcodeService.saveBarcode(barcodeRequest);
    }

    @PostMapping(path = "/update/{id}")
    public ResponceBarcode updateBarcode(@PathVariable(name = "id") Long id,@RequestBody RequestBarcode request) {
        return barcodeService.updateBarcode(id,request);
    }

    @PostMapping("/generate")
    public ResponceBarcode generateBarcode(@RequestBody RequestBarcode request) {
        return barcodeService.generateBarcode(request);
    }
}


