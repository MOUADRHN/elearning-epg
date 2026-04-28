package ma.epg.elearning.repository;
import ma.epg.elearning.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByEnseignantId(Long enseignantId);
    @Query("SELECT c FROM Course c JOIN c.affectations a WHERE a.filiere.id = :filiereId AND a.semestre = :semestre")
    List<Course> findByFiliereAndSemestre(@Param("filiereId") Long filiereId, @Param("semestre") int semestre);
}
