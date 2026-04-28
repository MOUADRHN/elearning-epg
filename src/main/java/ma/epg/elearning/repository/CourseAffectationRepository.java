package ma.epg.elearning.repository;
import ma.epg.elearning.entity.CourseAffectation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CourseAffectationRepository extends JpaRepository<CourseAffectation, Long> {
    List<CourseAffectation> findByCourseId(Long courseId);
    boolean existsByCourseIdAndFiliereIdAndSemestre(Long courseId, Long filiereId, int semestre);
}
