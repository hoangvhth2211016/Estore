package server.estore.Model.Order;

import org.mapstruct.*;
import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Model.Order.Dto.OrderRes;
import server.estore.Model.Product_Order.ProductsOrderMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductsOrderMapper.class})
public interface OrderMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstname", source = "user.firstname")
    @Mapping(target = "lastname", source = "user.lastname")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phone", source = "user.phone")
    OrderDetailRes toOrderDetailRes (Order order);

    @Mapping(target = "username", source = "user.username")
    OrderRes toOrderRes (Order order);
}