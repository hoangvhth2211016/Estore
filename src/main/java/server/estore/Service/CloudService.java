package server.estore.Service;

import org.springframework.web.multipart.MultipartFile;
import server.estore.Model.Product_Image.ProductImage;

import java.util.List;
import java.util.Map;

public interface CloudService {
    String upload(MultipartFile file, Map<?,?> options);

    String uploadProfileImg(MultipartFile file, String name);

    List<ProductImage> uploadProductImgs(List<MultipartFile> files, String name);

    void delete(String name);
}
