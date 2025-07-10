package digicertify.repository;

import digicertify.entity.CertificateInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<CertificateInfo, String> {
}
