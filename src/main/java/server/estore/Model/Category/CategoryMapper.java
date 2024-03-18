package server.estore.Model.Category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import server.estore.Model.Category.Dto.CategoryDto;
import server.estore.Model.Category.Dto.CategoryRes;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category fromIdToCategory(Long id);

    @Mapping(target = "parentCategory", source = "parentId")
    Category fromCategoryDtoToCategory(CategoryDto dto);

    List<Category> fromIdsToCategories(List<Long> ids);

    @Mapping(target = "parentId", source = "parentCategory.id")
    CategoryRes fromEntityToCatRes(Category category);

    void updateFromDto(CategoryDto dto, @MappingTarget Category category);

}
