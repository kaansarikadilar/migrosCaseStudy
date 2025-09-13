package com.kaan.product.Mapper;

import com.kaan.barcode.entity.Barcode;
import com.kaan.product.ProductResponce.ProductResponce;

public class BarcodeToResponce extends Barcode{
    public BarcodeToResponce(Barcode optionalBarcode, ProductResponce productResponce) {
        productResponce.setBarcodeCode(optionalBarcode.getCode());
        productResponce.setBarcodeType(optionalBarcode.getType());
        productResponce.setExtraBarcode(optionalBarcode.getExtraBarcode());
    }
}
