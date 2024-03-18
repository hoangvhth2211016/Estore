package server.estore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import server.estore.Model.Category.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    List<Category> findByParentCategory_Id(@Nullable Long id);


}