package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByChapterIdOrderByOrdreAsc(Long chapterId);
}
