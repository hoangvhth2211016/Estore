package server.estore.Service;

import server.estore.Model.Brand.Brand;
import server.estore.Model.Brand.Dto.BrandDto;
import server.estore.Model.Brand.Dto.BrandRes;

import java.util.List;

public interface BrandService {
    List<BrandRes> getAll();
    
    Brand getById(Long id);
    
    BrandRes getBrandResById(Long id);

    BrandRes create(BrandDto dto);
    
    BrandRes update(BrandDto dto, Long id);
    
    void delete(Long id);
}
