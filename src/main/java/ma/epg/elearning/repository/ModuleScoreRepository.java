package ma.epg.elearning.repository;
import ma.epg.elearning.entity.ModuleScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface ModuleScoreRepository extends JpaRepository<ModuleScore, Long> {
    Optional<ModuleScore> findByEtudiantIdAndCourseId(Long etudiantId, Long courseId);
    List<ModuleScore> findByEtudiantId(Long etudiantId);
    @Query("SELECT ms FROM ModuleScore ms WHERE ms.course.id = :courseId ORDER BY ms.noteFinale DESC")
    List<ModuleScore> findClassementByCourse(@Param("courseId") Long courseId);
}
