package server.estore.Model.Product_Order.Dto;

import lombok.Getter;
import lombok.Setter;
import server.estore.Model.Product.Dto.ProductRes;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductsOrderRes {
    private Long id;

    private Long orderId;

    private ProductRes product;

    private Integer quantity;

    private BigDecimal purchasePrice;

}
