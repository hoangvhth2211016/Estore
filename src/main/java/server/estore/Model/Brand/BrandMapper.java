package server.estore.Model.Brand;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import server.estore.Model.Brand.Dto.BrandDto;
import server.estore.Model.Brand.Dto.BrandRes;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper {

    Brand fromIdToEntity(Long id);

    Brand fromDtoToBrand(BrandDto dto);

    BrandRes fromBrandToBrandRes(Brand brand);
}
