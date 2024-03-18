package server.estore.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.estore.Model.Product.Product;

import java.util.Collection;
import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findAllByIdIn(Collection<Long> idList);

    @Query(value = "SELECT * FROM products p " +
            "WHERE CONCAT(p.brand, p.title) ILIKE ALL(:search)",
            nativeQuery = true)
    Page<Product> searchProducts(@Param("search") String[] search, Pageable pageable);

    Page<Product> findByBrand_IdInAndCategory_IdIn(Collection<Long> brandIds, Collection<Long> catIds, Pageable pageable);

    Page<Product> findByCategory_IdIn(Collection<Long> ids, Pageable pageable);

    Page<Product> findByBrand_IdIn(Collection<Long> ids, Pageable pageable);
}