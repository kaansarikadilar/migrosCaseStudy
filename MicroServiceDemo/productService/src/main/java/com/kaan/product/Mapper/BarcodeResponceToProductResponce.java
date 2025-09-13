package com.kaan.product.Mapper;

import com.kaan.product.ProductResponce.BarcodeResponce;
import com.kaan.product.ProductResponce.ProductResponce;

public class BarcodeResponceToProductResponce extends BarcodeResponce {
    public BarcodeResponceToProductResponce(BarcodeResponce barcodeResponce, ProductResponce productResponce) {
        productResponce.setBarcodeCode(barcodeResponce.getCode());
        productResponce.setExtraBarcode(barcodeResponce.getExtraBarcode());
        productResponce.setBarcodeType(barcodeResponce.getType());
    }
}
