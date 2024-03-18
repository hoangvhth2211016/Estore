package server.estore.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.estore.Exception.BadRequestException;
import server.estore.Exception.NotFoundException;
import server.estore.Model.Address.Address;
import server.estore.Model.Address.Dto.AddressDto;
import server.estore.Model.Address.Dto.AddressRes;
import server.estore.Model.Cart.Cart;
import server.estore.Model.Cart.Dto.CartDto;
import server.estore.Model.Order.Dto.OrderDto;
import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Model.Order.Dto.OrderRes;
import server.estore.Model.Order.Order;
import server.estore.Model.Order.OrderMapper;
import server.estore.Model.Order.OrderStatus;
import server.estore.Model.User.Dto.ChangePasswordDto;
import server.estore.Model.User.Dto.UserRes;
import server.estore.Model.User.User;
import server.estore.Model.User.UserMapper;
import server.estore.Producer.OrderProcessingProducer;
import server.estore.Service.AddressService;
import server.estore.Service.CartService;
import server.estore.Service.OrderService;
import server.estore.Service.UserService;
import server.estore.Utils.AppUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final AddressService addressService;

    private final OrderService orderService;

    private final CartService cartService;
    
    private final OrderMapper orderMapper;
    
    private final UserMapper userMapper;
    
    private final OrderProcessingProducer orderProcessingProducer;
    
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PageResponse<UserRes> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return new PageResponse<>(userService.getAll(pageable));
    }
    
    
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public UserRes getCurrentUserProfile(@AuthenticationPrincipal User user){
        return userMapper.fromEntityToUserRes(user);
    }

    @PatchMapping("/password")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public void changeUserPassword(@AuthenticationPrincipal User user, @Valid @RequestBody ChangePasswordDto dto){
        userService.changePassword(user, dto);
    }

    @PostMapping("/addresses")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public AddressRes createUserAddress(@AuthenticationPrincipal User user, @Valid @RequestBody AddressDto dto){
        AppUtil.verifyObj(user.getId(), dto.getUserId());
        return addressService.create(dto);

    }

    @PutMapping("/addresses/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public AddressRes updateUserAddress(@AuthenticationPrincipal User user, @PathVariable("id") Long addressId, @Valid @RequestBody AddressDto dto){
        AppUtil.verifyObj(user.getId(), dto.getUserId());
        Address currentAddress = user.getAddresses()
                .stream()
                .filter(address -> addressId.equals(address.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Address"));
        return addressService.update(dto, currentAddress);
    }

    @DeleteMapping("/addresses/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public void deleteUserAddress(@AuthenticationPrincipal User user, @PathVariable("id") Long addressId){
        Address currentAddress = user.getAddresses()
                .stream()
                .filter(address -> addressId.equals(address.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Address"));
        addressService.delete(currentAddress);
    }

    @PostMapping(value = "/avatar", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public String uploadAvatar(@AuthenticationPrincipal User user, @RequestBody MultipartFile file){
        return userService.uploadAvatar(file, user);
    }

    @PostMapping("/orders")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public String createUserOrder(@AuthenticationPrincipal User user, @Valid @RequestBody OrderDto dto) {
        AppUtil.verifyObj(user.getId(), dto.getUserId());
        orderProcessingProducer.send(dto);
        return "order has been submitted";
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    public List<OrderRes> getUserOrders(@AuthenticationPrincipal User user){
        return orderService.getAllByUserId(user.getId());
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public OrderDetailRes getUserOrderById(@AuthenticationPrincipal User user, @PathVariable("id") Long orderId){
        Order currentOrder = user.getOrders()
                .stream()
                .filter(order -> orderId.equals(order.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Order"));
        return orderMapper.toOrderDetailRes(currentOrder);
    }

    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public OrderDetailRes updateUserOrder(@AuthenticationPrincipal User user, @PathVariable("id") Long orderId, @RequestParam("status") String status){
        if(user.getOrders().stream().anyMatch(order -> orderId.equals(order.getId()))){
            if(status.equals(OrderStatus.CANCEL.name()) || status.equals(OrderStatus.RETURN.name())){
                Order updatedOrder = orderService.updateOrderStatus(orderId, status);
                return orderMapper.toOrderDetailRes(updatedOrder);
            }else{
                throw new BadRequestException("Unrecognisable request");
            }
        }else{
            throw new NotFoundException("Order");
        }
    }
    
    @GetMapping("/carts")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public Cart getUserCart(@AuthenticationPrincipal User user){
        Cart userCart = cartService.getByUserId(user.getId());
        return userCart != null ? userCart : cartService.create(user.getId(), null);
    }
    
    @PostMapping("/carts")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public Cart createOrUpdateUserCart(@AuthenticationPrincipal User user, @RequestBody @Valid CartDto dto){
        return cartService.update(user.getId(), dto);
    }
    
    @PatchMapping("/carts")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public Cart removeItem(@AuthenticationPrincipal User user, @RequestBody Map<String, Long> body){
        return cartService.remove(user.getId(), body.get("productId"));
    }
    
    @DeleteMapping("/carts")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public Cart clearCart(@AuthenticationPrincipal User user){
        Cart userCart = cartService.getByUserId(user.getId());
        if(userCart == null) throw new NotFoundException("cart");
        return cartService.clear(userCart);
    }
    
}
