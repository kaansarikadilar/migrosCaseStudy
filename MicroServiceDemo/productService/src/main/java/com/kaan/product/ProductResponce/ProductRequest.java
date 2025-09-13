package com.kaan.product.ProductResponce;

import com.kaan.product.Enums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotNull(message = "Product Name cant be null !")
    @Size(min = 1, max = 25,message = "Characters must be between 1-25")
    private String productName;

    @NotNull(message = "Unit cant be null !")
    private Enums unit;

    @NotNull(message = "Brand Name cant be null !")
    @Size(min = 1, max = 25,message = "Characters must be between 1-25")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$", message = "Characters Only !")
    private String brand;


    private Long categoryId;


}
