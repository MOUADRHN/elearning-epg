package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByChapterId(Long chapterId);
}
