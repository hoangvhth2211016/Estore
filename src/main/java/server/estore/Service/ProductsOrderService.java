package server.estore.Service;

import server.estore.Model.Order.Order;
import server.estore.Model.Product.Product;
import server.estore.Model.Product_Order.ProductsOrder;


public interface ProductsOrderService {
    ProductsOrder create(Product product, int quantity, Order order);
}
