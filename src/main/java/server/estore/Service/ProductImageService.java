package server.estore.Service;

import org.springframework.web.multipart.MultipartFile;
import server.estore.Model.Product.Product;
import server.estore.Model.Product_Image.ProductImage;

import java.util.List;

public interface ProductImageService {
    void delete(Long productId, Long imageId);

    List<ProductImage> uploadImages(Product currentProduct, List<MultipartFile> files);
}
