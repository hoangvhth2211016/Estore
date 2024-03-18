package server.estore.Model.Product.Dto;

import lombok.Getter;
import lombok.Setter;
import server.estore.Model.Brand.Dto.BrandRes;
import server.estore.Model.Category.Dto.CategoryRes;
import server.estore.Model.Product.ProductStatus;
import server.estore.Model.Product_Image.ProductImage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductRes {
    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private BrandRes brand;

    private ProductStatus status;

    private List<ProductImage> productImages = new ArrayList<>();

    private CategoryRes category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
