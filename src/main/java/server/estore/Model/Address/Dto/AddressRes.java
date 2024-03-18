package server.estore.Model.Address.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRes {
    private Long id;

    private String street;

    private String city;

    private String country;

    private String zipcode;
}
