package com.kaan.product.Mapper;

import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.product.ProductResponce.ProductResponce;

public class ResponceBarcodeToProductResponce extends ResponceBarcode{
    public ResponceBarcodeToProductResponce(ResponceBarcode optionalBarcode, ProductResponce productResponce) {
        productResponce.setBarcodeCode(optionalBarcode.getCode());
        productResponce.setBarcodeType(optionalBarcode.getType());
        productResponce.setExtraBarcode(optionalBarcode.getExtraBarcode());
    }
}
