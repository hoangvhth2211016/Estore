package server.estore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.Product_Image.ProductImage;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
}