package server.estore.Model.Order.Dto;

import lombok.Getter;
import lombok.Setter;
import server.estore.Model.Order.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderRes {
    private Long id;
    
    private String orderNr;

    private String username;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
