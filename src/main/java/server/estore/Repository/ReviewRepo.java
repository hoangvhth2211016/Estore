package server.estore.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.Review.Review;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    Page<Review> findByProduct_Id(Long id, Pageable pageable);

}