package server.estore.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.Order.Dto.OrderRes;
import server.estore.Model.Order.Order;
import server.estore.Model.Order.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    List<Order> findByUser_Id(Long id);

}