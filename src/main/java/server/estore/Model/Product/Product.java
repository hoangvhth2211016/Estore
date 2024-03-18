package server.estore.Model.Product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.estore.Model.BaseEntity;
import server.estore.Model.Brand.Brand;
import server.estore.Model.Category.Category;
import server.estore.Model.Product_Image.ProductImage;
import server.estore.Model.Review.Review;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;
    
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    

}