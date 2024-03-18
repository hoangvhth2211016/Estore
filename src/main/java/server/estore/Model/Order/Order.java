package server.estore.Model.Order;

import jakarta.persistence.*;
import lombok.*;
import server.estore.Model.BaseEntity;
import server.estore.Model.Product_Order.ProductsOrder;
import server.estore.Model.User.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {
    
    @Column(name = "nr", nullable = false, unique = true)
    private String orderNr;
    
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "zipcode", length = 5)
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductsOrder> productsOrders = new ArrayList<>();
    
}