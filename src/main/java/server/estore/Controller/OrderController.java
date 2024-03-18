package server.estore.Controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Model.Order.Dto.OrderRes;
import server.estore.Model.Order.OrderMapper;
import server.estore.Model.Order.OrderStatus;
import server.estore.Service.OrderService;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    
    private final OrderMapper orderMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PageResponse<OrderRes> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new PageResponse<>(orderService.getAll(pageable));
    }

    @GetMapping("/public/status")
    public OrderStatus[] getOrderStatus () {
        return OrderStatus.values();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public OrderDetailRes getById(@PathVariable("id") Long orderId){
        return orderService.getById(orderId);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public OrderRes updateOrderStatus(@PathVariable("id") Long orderId, @RequestBody Map<String, String> body) {
        return orderMapper.toOrderRes(orderService.updateOrderStatus(orderId, body.get("status")));
    }
}
