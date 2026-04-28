package ma.epg.elearning.repository;
import ma.epg.elearning.entity.VideoProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface VideoProgressRepository extends JpaRepository<VideoProgress, Long> {
    Optional<VideoProgress> findByEtudiantIdAndVideoId(Long etudiantId, Long videoId);
}
