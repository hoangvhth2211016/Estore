package server.estore.Model.Review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.estore.Model.BaseEntity;
import server.estore.Model.Product.Product;
import server.estore.Model.User.User;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Column(name = "content")
    private String content;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}