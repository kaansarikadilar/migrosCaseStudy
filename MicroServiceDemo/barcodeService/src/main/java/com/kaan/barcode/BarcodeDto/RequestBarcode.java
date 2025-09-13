package com.kaan.barcode.BarcodeDto;

import com.kaan.barcode.barcodeTypes;
import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBarcode {
    @Nullable
    private String code;
    @Nullable
    private barcodeTypes barcodeType;
    @Nullable
    private String extraBarcode;
    @Nullable
    private Long productId;
    @Nullable
    private  String categoryCode;
}
