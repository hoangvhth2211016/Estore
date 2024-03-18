package server.estore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.estore.Model.Address.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
}