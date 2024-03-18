package server.estore.Model.Product.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.estore.Model.Product.ProductStatus;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Long brandId;

    @NotNull
    private Long catId;

    @NotNull
    private BigDecimal price;

    @NotNull
    private ProductStatus status;

    @NotNull
    private Integer stock;

}
