package com.kaan.barcode.barcodeMapper;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.entity.Barcode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarcodeToRequest extends Barcode {
    public BarcodeToRequest(Barcode barcode, RequestBarcode requestBarcode) {
        requestBarcode.setCode(barcode.getCode());
        requestBarcode.setExtraBarcode(barcode.getExtraBarcode());
        requestBarcode.setProductId(barcode.getProductId());
        requestBarcode.setBarcodeType(barcode.getType());
    }
}
