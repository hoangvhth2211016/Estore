package server.estore.Model.Cart;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RedisHash("carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    
    @Id
    private String id;
    
    @Indexed
    private Long userId;
    
    private Map<Long, Integer> products = new HashMap<>();
    
}
