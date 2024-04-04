package server.estore.Model.Product;

import org.mapstruct.*;
import server.estore.Model.Brand.BrandMapper;
import server.estore.Model.Category.CategoryMapper;
import server.estore.Model.Product.Dto.ProductDto;
import server.estore.Model.Product.Dto.ProductRes;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CategoryMapper.class, BrandMapper.class})
public interface ProductMapper {

    Product fromIdToEntity(Long productId);

    @Mapping(target = "category", source = "catId")
    @Mapping(target = "brand", source = "brandId")
    Product fromDtoToEntity(ProductDto productDto);

    ProductRes toProductRes(Product product);

    List<ProductDto> toDtoList(List<Product> products);
    
    
    @Mapping(target = "brand", source = "brandId")
    @Mapping(target = "category", source = "catId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateEntity(ProductDto productDto, @MappingTarget Product product);
}

