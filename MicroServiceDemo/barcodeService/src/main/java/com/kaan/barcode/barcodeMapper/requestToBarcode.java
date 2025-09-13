package com.kaan.barcode.barcodeMapper;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.entity.Barcode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class requestToBarcode extends Barcode {
    public requestToBarcode(RequestBarcode requestBarcode,Barcode barcode) {
        barcode.setCode(requestBarcode.getCode());
        barcode.setExtraBarcode(requestBarcode.getExtraBarcode());
        barcode.setType(requestBarcode.getBarcodeType());
        barcode.setProductId(requestBarcode.getProductId());
    }
}