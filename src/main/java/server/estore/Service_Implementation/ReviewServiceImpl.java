package server.estore.Service_Implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import server.estore.Model.Product.Product;
import server.estore.Model.Review.Dto.ReviewDto;
import server.estore.Model.Review.Review;
import server.estore.Model.Review.ReviewMapper;
import server.estore.Model.User.User;
import server.estore.Repository.ReviewRepo;
import server.estore.Service.ReviewService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;

    private final ReviewMapper reviewMapper;


    @Override
    public Review create(ReviewDto dto, User currentUser, Product currentProduct) {
        Review newReview = reviewMapper.fromDtoToEntity(dto);
        newReview.setUser(currentUser);
        newReview.setProduct(currentProduct);
        return reviewRepo.save(newReview);
    }

    @Override
    public Page<Review> getReviews(Long productId, Pageable pageable) {
        return reviewRepo.findByProduct_Id(productId, pageable);
    }
}
