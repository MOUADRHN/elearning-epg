package ma.epg.elearning.repository;
import ma.epg.elearning.entity.ForumReply;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ForumReplyRepository extends JpaRepository<ForumReply, Long> {
    List<ForumReply> findByPostIdOrderByCreeLe(Long postId);
}
