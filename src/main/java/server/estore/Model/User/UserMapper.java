package server.estore.Model.User;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import server.estore.Model.User.Dto.RegisterDto;
import server.estore.Model.User.Dto.UserRes;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User fromIdToUser(Long id);

    User fromRegisterDtoToUser(RegisterDto dto);

    UserRes fromEntityToUserRes(User user);

}
