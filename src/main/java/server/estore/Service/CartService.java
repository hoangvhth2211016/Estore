package server.estore.Service;

import server.estore.Model.Cart.Cart;
import server.estore.Model.Cart.Dto.CartDto;

import java.util.Set;

public interface CartService {
    
    Cart create(Long userId, CartDto dto);
    
    Cart getByUserId(Long userId);
    
    Cart update(Long userId, CartDto dto);
    
    Cart remove(Long userId, Long productId);
    
    Cart clear(Cart cart);
    
}
