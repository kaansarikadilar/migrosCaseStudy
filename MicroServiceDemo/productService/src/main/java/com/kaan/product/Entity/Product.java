package com.kaan.product.Entity;

import com.kaan.product.Enums;
import jakarta.persistence.*;
import com.kaan.category.entity.Category;
import com.kaan.barcode.entity.Barcode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private Enums unit;

    @Column(name ="brand")
    private String brand;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "barcode_id")
    private String barcodeId;
}
