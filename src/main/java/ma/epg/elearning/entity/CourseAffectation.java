package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "course_affectations", uniqueConstraints = @UniqueConstraint(columnNames = {"course_id","filiere_id","semestre"}))
@Data @NoArgsConstructor @AllArgsConstructor
public class CourseAffectation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private int semestre;
    @ManyToOne @JoinColumn(name = "course_id", nullable = false) private Course course;
    @ManyToOne @JoinColumn(name = "filiere_id", nullable = false) private Filiere filiere;
}
