package server.estore.Service_Implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import server.estore.Exception.AlreadyExistException;
import server.estore.Exception.NotFoundException;
import server.estore.Model.Brand.Brand;
import server.estore.Model.Brand.BrandMapper;
import server.estore.Model.Brand.Dto.BrandDto;
import server.estore.Model.Brand.Dto.BrandRes;
import server.estore.Repository.BrandRepo;
import server.estore.Service.BrandService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@CacheConfig(cacheNames = "brands")
public class  BrandServiceImpl implements BrandService {

    private final BrandRepo brandRepo;

    private final BrandMapper brandMapper;

    @Override
    @Cacheable(key = "'list'")
    public List<BrandRes> getAll() {
        return brandRepo.findAll().stream().map(brandMapper::fromBrandToBrandRes).toList();
    }
    
    @Override
    public Brand getById(Long id) {
        return brandRepo.findById(id).orElseThrow(() -> new NotFoundException("Brand"));
    }
    
    @Override
    public BrandRes getBrandResById(Long id) {
        return brandMapper.fromBrandToBrandRes(getById(id));
    }
    
    @Override
    @CacheEvict(key = "'list'")
    public BrandRes create(BrandDto dto) {
        Brand newBrand = brandRepo.save(brandMapper.fromDtoToBrand(dto));
        return brandMapper.fromBrandToBrandRes(newBrand);
    }
    
    @Override
    @CacheEvict(key = "'list'")
    public BrandRes update(BrandDto dto, Long id) {
        Brand brand = getById(id);
        if(brandRepo.existsByName(dto.getName())){
           throw new AlreadyExistException("Brand");
        }
        brand.setName(dto.getName());
        Brand updatedBrand = brandRepo.save(brand);
        return brandMapper.fromBrandToBrandRes(updatedBrand);
    }
    
    @Override
    @CacheEvict(key = "'list'")
    public void delete(Long id) {
        Brand brand = getById(id);
        brandRepo.delete(brand);
    }
}
