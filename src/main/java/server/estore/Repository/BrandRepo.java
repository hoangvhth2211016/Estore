package server.estore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.Brand.Brand;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {
    
    boolean existsByName(String name);
}