package server.estore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.Product_Order.ProductsOrder;

@Repository
public interface ProductsOrderRepo extends JpaRepository<ProductsOrder, Long> {
}