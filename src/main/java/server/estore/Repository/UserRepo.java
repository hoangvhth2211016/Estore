package server.estore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.User.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    

    <T> Optional<T> findByUsername (String username, Class<T> type);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}