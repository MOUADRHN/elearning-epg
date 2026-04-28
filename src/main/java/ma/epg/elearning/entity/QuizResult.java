package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name = "quiz_results")
@Data @NoArgsConstructor @AllArgsConstructor
public class QuizResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private Double score;
    private int pointsGagnes;
    private LocalDateTime completeLe;
    @ManyToOne @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
    @ManyToOne @JoinColumn(name = "quiz_id", nullable = false) private Quiz quiz;
}
