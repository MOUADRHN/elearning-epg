package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByChapterId(Long chapterId);
    List<Assignment> findByEnseignantId(Long enseignantId);
}
