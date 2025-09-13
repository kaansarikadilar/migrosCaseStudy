package com.kaan.product.ProductResponce;


import com.kaan.product.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponce {


    private Long id;

    private String categoryName;

    private String categoryCode;

    private Product product;

}
