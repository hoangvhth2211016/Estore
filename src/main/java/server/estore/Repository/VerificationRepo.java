package server.estore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.Verification.Verification;

import java.util.Optional;

@Repository
public interface VerificationRepo extends JpaRepository<Verification, Long> {
    Optional<Verification> findByKey(String key);
}