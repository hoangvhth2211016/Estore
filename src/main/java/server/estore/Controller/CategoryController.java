package server.estore.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import server.estore.Model.Category.Dto.CategoryDto;
import server.estore.Model.Category.Dto.CategoryRes;
import server.estore.Model.Category.Dto.CategoryUpdateDto;
import server.estore.Service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/public")
    public List<CategoryRes> getCategoryTree(){
        return categoryService.getAllByParent();
    }

    @GetMapping("/public/{id}")
    public CategoryRes getById(@PathVariable Long id){
        return categoryService.getCategoryResById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryRes create(@Valid @RequestBody CategoryDto dto){
        return categoryService.create(dto);
    }
    
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryRes update(@PathVariable Long id,@Valid @RequestBody CategoryUpdateDto dto){
        return categoryService.update(dto, id);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id){
        categoryService.delete(id);
    }
    
}
