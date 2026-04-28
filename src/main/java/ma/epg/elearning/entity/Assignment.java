package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity @Table(name = "assignments")
@Data @NoArgsConstructor @AllArgsConstructor
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    @Column(columnDefinition = "TEXT") private String consigne;
    private LocalDateTime dateLimite;
    @ManyToOne @JoinColumn(name = "chapter_id", nullable = false) private Chapter chapter;
    @ManyToOne @JoinColumn(name = "enseignant_id", nullable = false) private Enseignant enseignant;
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL) private List<AssignmentSubmission> submissions;
}
