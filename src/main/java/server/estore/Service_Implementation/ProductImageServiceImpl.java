package server.estore.Service_Implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.estore.Exception.NotFoundException;
import server.estore.Model.Product.Product;
import server.estore.Model.Product_Image.ProductImage;
import server.estore.Repository.ProductImageRepo;
import server.estore.Service.CloudService;
import server.estore.Service.ProductImageService;
import server.estore.Utils.AppUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepo productImageRepo;

    private final CloudService cloudService;

    @Override
    public void delete(Long productId, Long imageId) {
        ProductImage image = productImageRepo.findById(imageId).orElseThrow(() -> new NotFoundException("Image"));
        AppUtil.verifyObj(productId, image.getProduct().getId());
        cloudService.delete(image.getName());
        productImageRepo.delete(image);
    }

    @Override
    public List<ProductImage> uploadImages(Product currentProduct, List<MultipartFile> files) {
        String name = currentProduct.getTitle().replace(" ", "_")+"_"+currentProduct.getId();
        List<ProductImage> images = cloudService.uploadProductImgs(files, name);
        images.forEach(img ->{
            img.setProduct(currentProduct);
        });
        return productImageRepo.saveAll(images);
    }
}
