package server.estore.Model.User.Dto;

import lombok.Getter;
import lombok.Setter;
import server.estore.Model.Address.Dto.AddressRes;
import server.estore.Model.User.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserRes {

    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String phone;

    private LocalDate dob;

    private String imgUrl;

    private Role role;

    private List<AddressRes> addresses = new ArrayList<>();
}
