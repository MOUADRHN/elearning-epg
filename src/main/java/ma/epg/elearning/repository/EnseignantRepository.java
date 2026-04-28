package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    List<Enseignant> findBySpecialite(String specialite);
}
