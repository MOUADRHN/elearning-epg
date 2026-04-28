package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name = "assignment_submissions")
@Data @NoArgsConstructor @AllArgsConstructor
public class AssignmentSubmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String cheminFichier;
    private Double note;
    @Column(columnDefinition = "TEXT") private String feedback;
    private LocalDateTime soumisLe;
    @ManyToOne @JoinColumn(name = "assignment_id", nullable = false) private Assignment assignment;
    @ManyToOne @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
}
