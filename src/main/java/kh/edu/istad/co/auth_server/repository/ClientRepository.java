package kh.edu.istad.co.auth_server.repository;

import java.util.Optional;
import kh.edu.istad.co.auth_server.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
Optional<Client> findByClientId(String clientId);
}
