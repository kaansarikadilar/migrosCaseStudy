package com.kaan.barcode.barcodeMapper;

import com.kaan.barcode.entity.Barcode;
import com.kaan.product.ProductResponce.BarcodeResponce;

public class BarcodeToProductBarcodeResponce extends BarcodeResponce {
    public BarcodeToProductBarcodeResponce(Barcode barcode, BarcodeResponce barcodeResponce) {
        barcodeResponce.setId(barcode.getId());
        barcodeResponce.setCode(barcode.getCode());
        barcodeResponce.setExtraBarcode(barcode.getExtraBarcode());
        barcodeResponce.setType(barcode.getType());
    }

}
