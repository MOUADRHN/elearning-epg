package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Question;
import ma.epg.elearning.enums.QuestionMode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByVideoId(Long videoId);
    List<Question> findByQuizId(Long quizId);
    List<Question> findByVideoIdAndMode(Long videoId, QuestionMode mode);
}
