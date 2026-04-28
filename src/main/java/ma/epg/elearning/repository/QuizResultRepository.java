package ma.epg.elearning.repository;
import ma.epg.elearning.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByEtudiantId(Long etudiantId);
    Optional<QuizResult> findByEtudiantIdAndQuizId(Long etudiantId, Long quizId);
}
