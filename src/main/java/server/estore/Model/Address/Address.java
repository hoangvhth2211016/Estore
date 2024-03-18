package server.estore.Model.Address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.estore.Model.BaseEntity;
import server.estore.Model.User.User;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "zipcode", length = 10)
    private String zipcode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}