package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByCne(String cne);
    List<Etudiant> findByFiliereId(Long filiereId);
    List<Etudiant> findBySemestre(int semestre);
    @Query("SELECT e FROM Etudiant e JOIN ModuleScore ms ON ms.etudiant = e WHERE ms.course.id = :courseId ORDER BY ms.noteFinale DESC")
    List<Etudiant> findClassementByCourse(@Param("courseId") Long courseId);
}
