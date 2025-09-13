package com.kaan.barcode.entity;

import com.kaan.barcode.barcodeTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "barcode")
public class Barcode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "barcode_code")
    private String Code;

    @Column(name = "barcode_extra")
    private String extraBarcode;

    @Column(name = "barcode_type")
    @Enumerated(EnumType.STRING)
    private barcodeTypes Type;

    @Column(name = "product_id")
    private Long productId;
}
