package server.estore.Service_Implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.estore.Exception.NotFoundException;
import server.estore.Model.Cart.Cart;
import server.estore.Model.Cart.Dto.CartDto;
import server.estore.Repository.CartRepo;
import server.estore.Service.CartService;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    
    private final CartRepo cartRepo;
    
    @Override
    public Cart create(Long userId, CartDto dto) {
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        if (dto != null)
            newCart.getProducts().put(dto.getProductId(), dto.getQuantity());
        return cartRepo.save(newCart);
    }
    
    @Override
    public Cart getByUserId(Long userId) {
        return cartRepo.findByUserId(userId).orElse(null);
    }
    
    @Override
    public Cart update(Long userId, CartDto dto) {
        Cart userCart = getByUserId(userId);
        if(userCart != null){
            userCart.getProducts().put(dto.getProductId(), dto.getQuantity());
            return cartRepo.save(userCart);
        }else{
            return create(userId, dto);
        }
    }
    
    @Override
    public Cart remove(Long userId, Long productId) {
        if(!cartRepo.existsByUserId(userId)){
            throw new NotFoundException("Cart");
        }
        Cart userCart = getByUserId(userId);
        if(!userCart.getProducts().containsKey(productId)){
            throw new NotFoundException("Cart's item");
        }
        userCart.getProducts().remove(productId);
        return cartRepo.save(userCart);
    }
    
    @Override
    public Cart clear(Cart cart) {
        cart.getProducts().clear();
        return cartRepo.save(cart);
    }
    
}
