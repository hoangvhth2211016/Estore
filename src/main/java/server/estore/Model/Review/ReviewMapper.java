package server.estore.Model.Review;

import org.mapstruct.*;
import server.estore.Model.Product.ProductMapper;
import server.estore.Model.Review.Dto.ReviewDto;
import server.estore.Model.Review.Dto.ReviewRes;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductMapper.class})
public interface ReviewMapper {
    Review fromDtoToEntity(ReviewDto reviewDto);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userId", source = "user.id")
    ReviewRes toReviewRes(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Review partialUpdate(ReviewDto reviewDto, @MappingTarget Review review);
}