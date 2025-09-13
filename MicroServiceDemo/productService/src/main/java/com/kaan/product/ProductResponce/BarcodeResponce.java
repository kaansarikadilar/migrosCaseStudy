package com.kaan.product.ProductResponce;

import com.kaan.barcode.barcodeTypes;
import com.kaan.product.Entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeResponce {

    private Long id;

    private String Code;

    @Enumerated(EnumType.STRING)
    private barcodeTypes Type;

    private String extraBarcode;

}
