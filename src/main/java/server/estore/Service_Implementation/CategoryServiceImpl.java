package server.estore.Service_Implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import server.estore.Exception.AlreadyExistException;
import server.estore.Exception.NotFoundException;
import server.estore.Model.Category.Category;
import server.estore.Model.Category.CategoryMapper;
import server.estore.Model.Category.Dto.CategoryDto;
import server.estore.Model.Category.Dto.CategoryRes;
import server.estore.Model.Category.Dto.CategoryUpdateDto;
import server.estore.Repository.CategoryRepo;
import server.estore.Service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@CacheConfig(cacheNames = "categories")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    private final CategoryMapper categoryMapper;
    
    
    @Override
    public Category getById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new NotFoundException("Category"));
    }
    
    @Override
    public CategoryRes getCategoryResById(Long id) {
        Category cat = getById(id);
        return categoryMapper.fromEntityToCatRes(cat);
    }

    @Override
    @CacheEvict(key = "'list'")
    public CategoryRes create(CategoryDto dto) {
        if(categoryRepo.existsByName(dto.getName())){
            throw new AlreadyExistException("Category");
        }
        
        Category newCat = categoryMapper.fromCategoryDtoToCategory(dto);
        if(dto.getParentId() == null){
            newCat.setPath("");
        }else{
            Category parentCat = getById(dto.getParentId());
            newCat.setPath(parentCat.getPath()+parentCat.getId()+"-");
        }
        return categoryMapper.fromEntityToCatRes(categoryRepo.save(newCat));
    }

    @Override
    @Cacheable(key = "'list'")
    public List<CategoryRes> getAllByParent() {
        return categoryRepo.findByParentCategory_Id(null).stream().map(categoryMapper::fromEntityToCatRes).toList();
    }
    
    @Override
    @CacheEvict(key = "'list'")
    public CategoryRes update(CategoryUpdateDto dto, Long id) {
        Category cat = getById(id);
        if(categoryRepo.existsByName(dto.getName())){
            throw new AlreadyExistException("Category");
        }
        cat.setName(dto.getName());
        Category updatedCat = categoryRepo.save(cat);
        return categoryMapper.fromEntityToCatRes(updatedCat);
    }
    
    @Override
    @CacheEvict(key = "'list'")
    public void delete(Long id) {
        Category cat = getById(id);
        categoryRepo.delete(cat);
    }
}
