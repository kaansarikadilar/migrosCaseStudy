package com.kaan.barcode.BarcodeDto;

import com.kaan.barcode.barcodeTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponceBarcode {

    private String Code;

    @Enumerated(EnumType.STRING)
    private barcodeTypes Type;

    private String extraBarcode;

}
