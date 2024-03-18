package server.estore.Service_Implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import server.estore.Exception.BadRequestException;
import server.estore.Exception.NotFoundException;
import server.estore.Model.Cart.Cart;
import server.estore.Model.Order.Dto.OrderDto;
import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Model.Order.Dto.OrderRes;
import server.estore.Model.Order.Order;
import server.estore.Model.Order.OrderMapper;
import server.estore.Model.Order.OrderStatus;
import server.estore.Model.Product.Product;
import server.estore.Model.Product_Order.ProductsOrder;
import server.estore.Model.User.User;
import server.estore.Repository.OrderRepo;
import server.estore.Service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    private final ProductsOrderService productsOrderService;
    
    private final ProductService productService;
    
    private final CartService cartService;

    private final OrderMapper orderMapper;
    
    private final UserService userService;

    @Override
    public OrderDetailRes create(OrderDto dto) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User user = userService.findById(dto.getUserId());
        
        Order newOrder = orderRepo.save(Order
                .builder()
                .orderNr(UUID.randomUUID().toString())
                .user(user)
                .street(dto.getStreet())
                .city(dto.getCity())
                .country(dto.getCountry())
                .zipcode(dto.getZipcode())
                .status(OrderStatus.PENDING)
                .build());
        
        Cart userCart = cartService.getByUserId(user.getId());
        
        if(userCart == null) throw new NotFoundException("User's cart");

        List<Product> productList = productService.getByIdList(userCart.getProducts().keySet());
        
        List<ProductsOrder> productsOrderList = new ArrayList<>();
        
        productList.forEach( product -> {
            int purchaseQuantity = userCart.getProducts().get(product.getId());
            Product updatedProduct = productService.updateStock(product, purchaseQuantity, "decrease");
            ProductsOrder po = productsOrderService.create(updatedProduct, purchaseQuantity, newOrder);
            productsOrderList.add(po);
        });
        
        newOrder.setProductsOrders(productsOrderList);
        
        cartService.clear(userCart);
        
        return orderMapper.toOrderDetailRes(newOrder);
    }

    @Override
    public OrderDetailRes getById(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException("Order"));
        return orderMapper.toOrderDetailRes(order);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String orderStatus) {
        Order currentOrder = orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException("Order"));
        int currentStatus = currentOrder.getStatus().ordinal();
        int reqStatus = OrderStatus.valueOf(orderStatus).ordinal();
        if(currentStatus == reqStatus){
            return currentOrder;
        }
        if(reqStatus < currentStatus){
            throw new BadRequestException("Unable to rollback order status");
        }
        if(reqStatus == 3 && currentStatus >0) {
            throw new BadRequestException("Unable to cancel order");
        }
        currentOrder.setStatus(OrderStatus.valueOf(orderStatus));
        return orderRepo.save(currentOrder);
    }

    @Override
    public Page<OrderRes> getAll(Pageable pageable) {
        return orderRepo.findAll(pageable).map(orderMapper::toOrderRes);
    }

    @Override
    public List<OrderRes> getAllByUserId(Long userId) {
        return orderRepo.findByUser_Id(userId).stream().map(orderMapper::toOrderRes).toList();
    }


}
