package server.estore.Model.Category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.estore.Model.BaseEntity;
import server.estore.Model.Product.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    private Category parentCategory;
    
    @Column(name = "path", nullable = false, updatable = false)
    private String path;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentCategory", orphanRemoval = true)
    private List<Category> subCategories = new ArrayList<>();

    
}