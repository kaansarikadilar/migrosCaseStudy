package com.kaan.product.ProductResponce;

import com.kaan.barcode.barcodeTypes;
import com.kaan.product.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponce {

    private Long id;

    private String productName;

    private String productCode;

    private Enums unit;

    private String brand;

    private String barcodeCode;

    private barcodeTypes barcodeType;

    private String extraBarcode;
}
