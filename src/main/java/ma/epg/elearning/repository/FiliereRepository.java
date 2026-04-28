package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    Optional<Filiere> findByNom(String nom);
}
