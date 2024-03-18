package server.estore.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.estore.Model.Order.Dto.OrderDto;
import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Model.Order.Dto.OrderRes;
import server.estore.Model.Order.Order;

import java.util.List;

public interface OrderService {
    OrderDetailRes create(OrderDto dto);

    OrderDetailRes getById(Long orderId);

    Order updateOrderStatus(Long orderId, String orderStatus);

    Page<OrderRes> getAll(Pageable pageable);

    List<OrderRes> getAllByUserId(Long userId);
}
