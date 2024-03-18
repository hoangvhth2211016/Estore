package server.estore.Model.Order.Dto;

import lombok.Getter;
import lombok.Setter;
import server.estore.Model.Order.OrderStatus;
import server.estore.Model.Product_Order.Dto.ProductsOrderRes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDetailRes {
    
    private Long id;

    private String orderNr;
    
    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private String street;

    private String city;

    private String country;

    private String zipcode;

    private OrderStatus status;

    private List<ProductsOrderRes> productsOrders = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
