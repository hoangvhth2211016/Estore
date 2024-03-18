package server.estore.Model.Product_Image;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import server.estore.Model.Product_Image.Dto.ProductImageDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductImageMapper {
    ProductImage fromDtoToEntity(ProductImageDto dto);

}
