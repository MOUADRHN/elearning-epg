package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByCourseId(Long courseId);
    @Query("SELECT a FROM Announcement a WHERE a.course IS NULL ORDER BY a.creeLe DESC")
    List<Announcement> findAnnoncesGlobales();
}
