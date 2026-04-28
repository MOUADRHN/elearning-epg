package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByEtudiantId(Long etudiantId);
}
