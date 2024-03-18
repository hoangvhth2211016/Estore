package server.estore.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.estore.Model.Product.Dto.ProductDto;
import server.estore.Model.Product.Dto.ProductRes;
import server.estore.Model.Product.Product;

import java.util.List;
import java.util.Set;


public interface ProductService {

    Page<ProductRes> getAll(Pageable pageable);

    Page<ProductRes> searchProducts(String[] search, Pageable pageable);

    Page<ProductRes> getProductsByFilter(Set<Long> catIdList, Set<Long> brandIdList, Pageable pageable);

    List<Product> getByIdList(Set<Long> idList);
    
    List<ProductRes> getProductResByIdList(Set<Long> idList);

    Product findById(Long productId);

    ProductRes getById(Long productId);

    ProductRes create(ProductDto dto);

    ProductRes update(ProductDto dto, Long ProductId);

    void delete(Long productId);

    Product updateStock(Product product, int quantity, String action);

}
