package server.estore.Model.Product_Image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.estore.Model.BaseEntity;
import server.estore.Model.Product.Product;

@Getter
@Setter
@Entity
@Table(name = "product_images")
public class ProductImage extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}