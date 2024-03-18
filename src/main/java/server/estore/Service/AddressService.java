package server.estore.Service;

import server.estore.Model.Address.Address;
import server.estore.Model.Address.Dto.AddressDto;
import server.estore.Model.Address.Dto.AddressRes;

public interface AddressService {
    AddressRes create(AddressDto dto);

    AddressRes update(AddressDto dto, Address address);

    Address findById(Long addressId);

    void delete(Address currentAddress);
}
