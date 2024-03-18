package server.estore.Model.Address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import server.estore.Model.Address.Dto.AddressDto;
import server.estore.Model.Address.Dto.AddressRes;
import server.estore.Model.User.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface  AddressMapper {
    Address fromIdToAddress(Long id);

    List<Address> fromIdsToListAddresses(List<Long> ids);

    @Mapping(target = "user", source = "userId")
    Address fromDtoToAddress(AddressDto dto);

    AddressRes fromEntityToAddressRes(Address address);

    Address updateFromDto(AddressDto dto, @MappingTarget Address address);
}

