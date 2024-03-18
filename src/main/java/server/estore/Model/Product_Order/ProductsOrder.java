package server.estore.Model.Product_Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import server.estore.Model.BaseEntity;
import server.estore.Model.Order.Order;
import server.estore.Model.Product.Product;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_orders")
public class ProductsOrder extends BaseEntity {

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "purchase_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal purchasePrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}