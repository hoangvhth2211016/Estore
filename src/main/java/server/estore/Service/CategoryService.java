package server.estore.Service;

import server.estore.Model.Category.Category;
import server.estore.Model.Category.Dto.CategoryDto;
import server.estore.Model.Category.Dto.CategoryRes;
import server.estore.Model.Category.Dto.CategoryUpdateDto;

import java.util.List;

public interface CategoryService {

    Category getById(Long id);
    
     CategoryRes getCategoryResById(Long id);

     CategoryRes create(CategoryDto dto);

     List<CategoryRes> getAllByParent();
    
     CategoryRes update(CategoryUpdateDto dto, Long id);
    
    void delete(Long id);
}
