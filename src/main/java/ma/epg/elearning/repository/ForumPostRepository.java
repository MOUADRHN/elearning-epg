package ma.epg.elearning.repository;
import ma.epg.elearning.entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    List<ForumPost> findByCourseIdOrderByCreeLe(Long courseId);
}
