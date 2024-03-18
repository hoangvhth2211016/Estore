package server.estore.Model.Product_Order.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsOrderDto {

    @NotNull
    private Long productId;

    @Size(min = 1)
    private Integer quantity;

}
