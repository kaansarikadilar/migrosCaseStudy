package com.kaan.category.response;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCategory {


    @NotBlank(message = "Category Name cant be blank")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$", message = "Characters Only !")
    @Size(min = 2, max = 20,message = "Category Name must be longer than 2 and shorter than 20 characters !")
    private String categoryName;

    @NotBlank(message = "Category Code cant be blank")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$", message = "Characters Only !")
    @Size(min = 2, max = 2 , message = "Category Name must be 2 characters !")
    private String categoryCode;
}
