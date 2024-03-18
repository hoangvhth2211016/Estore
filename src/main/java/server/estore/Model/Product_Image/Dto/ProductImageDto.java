package server.estore.Model.Product_Image.Dto;

import jakarta.validation.constraints.NotBlank;

public class ProductImageDto {

    @NotBlank
    private String name;

    @NotBlank
    private String url;

}
