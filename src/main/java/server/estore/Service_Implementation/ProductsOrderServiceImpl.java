package server.estore.Service_Implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.estore.Model.Order.Order;
import server.estore.Model.Product.Product;
import server.estore.Model.Product_Order.ProductsOrder;
import server.estore.Repository.ProductsOrderRepo;
import server.estore.Service.ProductsOrderService;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductsOrderServiceImpl implements ProductsOrderService {

    private final ProductsOrderRepo productsOrderRepo;
    
    @Override
    public ProductsOrder create(Product product, int quantity, Order order) {
        
        ProductsOrder po = ProductsOrder
                .builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .purchasePrice(product.getPrice())
                .build();

        return productsOrderRepo.save(po);
    }
}
