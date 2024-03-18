package server.estore.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.Cart.Cart;

import java.util.Optional;

@Repository
public interface CartRepo extends CrudRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
    
    boolean existsByUserId(Long userId);
}
