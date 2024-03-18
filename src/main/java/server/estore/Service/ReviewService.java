package server.estore.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.estore.Model.Product.Product;
import server.estore.Model.Review.Dto.ReviewDto;
import server.estore.Model.Review.Review;
import server.estore.Model.User.User;

public interface ReviewService {
    Review create(ReviewDto dto, User user, Product product);

    Page<Review> getReviews(Long productId, Pageable pageable);
}
