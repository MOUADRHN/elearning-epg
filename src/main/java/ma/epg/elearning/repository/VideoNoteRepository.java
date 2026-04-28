package ma.epg.elearning.repository;
import ma.epg.elearning.entity.VideoNote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VideoNoteRepository extends JpaRepository<VideoNote, Long> {
    List<VideoNote> findByEtudiantIdAndVideoId(Long etudiantId, Long videoId);
}
