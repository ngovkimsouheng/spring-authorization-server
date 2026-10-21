package kh.edu.istad.co.auth_server.repository;

import java.util.Optional;
import kh.edu.istad.co.auth_server.entity.AuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {
Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
