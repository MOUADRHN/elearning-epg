package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "module_scores", uniqueConstraints = @UniqueConstraint(columnNames = {"etudiant_id","course_id"}))
@Data @NoArgsConstructor @AllArgsConstructor
public class ModuleScore {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private int    pointsPlateforme;
    private Double noteExamen;
    private Double noteFinale;
    public void calculerNoteFinale(int maxPoints) {
        if (noteExamen != null && maxPoints > 0) {
            double ratio = Math.min(1.0, (double) pointsPlateforme / maxPoints);
            this.noteFinale = 0.8 * noteExamen + 0.2 * (ratio * 20);
        }
    }
    @ManyToOne @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
    @ManyToOne @JoinColumn(name = "course_id", nullable = false) private Course course;
}
