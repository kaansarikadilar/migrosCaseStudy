package com.kaan.barcode.barcodeMapper;

import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.entity.Barcode;
import lombok.Getter;

@Getter
public class BarcodeToResponce extends ResponceBarcode {
    public BarcodeToResponce(Barcode barcode , ResponceBarcode responceBarcode) {
        responceBarcode.setId(barcode.getId());
        responceBarcode.setType(barcode.getType());
        responceBarcode.setExtraBarcode(barcode.getExtraBarcode());
        responceBarcode.setCode(barcode.getCode());
    }
}
