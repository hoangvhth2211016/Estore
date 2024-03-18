package server.estore.Model.Product_Order;

import org.mapstruct.*;
import server.estore.Model.Category.CategoryMapper;
import server.estore.Model.Product.ProductMapper;
import server.estore.Model.Product_Order.Dto.ProductsOrderDto;
import server.estore.Model.Product_Order.Dto.ProductsOrderRes;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductMapper.class})
public interface ProductsOrderMapper {
    ProductsOrder toEntity(ProductsOrderDto productsOrderDto);

    @Mapping(target = "orderId", source = "order.id")
    ProductsOrderRes toProductsOrderRes(ProductsOrder productsOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductsOrder partialUpdate(ProductsOrderDto productsOrderDto, @MappingTarget ProductsOrder productsOrder);
}