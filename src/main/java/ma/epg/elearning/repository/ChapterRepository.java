package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByCourseIdOrderByOrdreAsc(Long courseId);
}
