package server.estore.Service_Implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.estore.Exception.ServerErrorException;
import server.estore.Model.Product_Image.ProductImage;
import server.estore.Service.CloudService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudServiceImpl implements CloudService {

    @Value("${cloudinary.root-folder}")
    private String rootFolder;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file, Map<?,?> options) {
        try {
            var uploadResult = cloudinary
                    .uploader()
                    .upload(file.getBytes(), options);
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new ServerErrorException("Image upload fail");
        }
    }

    @Override
    public String uploadProfileImg(MultipartFile file, String name) {
        Map<?,?> options = ObjectUtils.asMap(
                "public_id", name,
                "folder", rootFolder + "users/",
                "faces", "true",
                "overwrite", "true");
        return upload(file, options);
    }

    @Override
    public List<ProductImage> uploadProductImgs(List<MultipartFile> files, String name) {
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            LocalDateTime createdDate = LocalDateTime.now();
            String publicId = name.toLowerCase() + "_" + createdDate.toEpochSecond(ZoneOffset.UTC);
            Map<?,?> options = ObjectUtils.asMap(
                    "public_id", publicId,
                    "folder", rootFolder + "products/",
                    "overwrite", "false");
            String url = upload(file, options);
            ProductImage img = new ProductImage();
            img.setName(rootFolder + "products/" + publicId);
            img.setUrl(url);
            productImages.add(img);
        }
        return productImages;
    }

    @Override
    public void delete(String name) {
        try {
            cloudinary.uploader().destroy(name, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ServerErrorException("Unable to delete image from cloud service");
        }
    }
}
