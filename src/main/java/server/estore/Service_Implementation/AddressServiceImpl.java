package server.estore.Service_Implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.estore.Exception.NotFoundException;
import server.estore.Model.Address.Address;
import server.estore.Model.Address.AddressMapper;
import server.estore.Model.Address.Dto.AddressDto;
import server.estore.Model.Address.Dto.AddressRes;
import server.estore.Repository.AddressRepo;
import server.estore.Service.AddressService;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    private final AddressMapper addressMapper;

    @Override
    public AddressRes create(AddressDto dto) {
        Address newAddress = addressRepo.save(addressMapper.fromDtoToAddress(dto));
        return addressMapper.fromEntityToAddressRes(newAddress);
    }

    @Override
    public AddressRes update(AddressDto dto, Address currentAddress) {
        Address updatedAddress = addressRepo.save(addressMapper.updateFromDto(dto, currentAddress));
        return addressMapper.fromEntityToAddressRes(updatedAddress);
    }

    @Override
    public Address findById(Long addressId) {
        return addressRepo.findById(addressId).orElseThrow(() -> new NotFoundException("Address"));
    }

    @Override
    public void delete(Address currentAddress) {
        addressRepo.delete(currentAddress);
    }
}
