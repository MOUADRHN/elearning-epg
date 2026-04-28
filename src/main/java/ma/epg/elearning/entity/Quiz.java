package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Table(name = "quizzes")
@Data @NoArgsConstructor @AllArgsConstructor
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    private int dureeMinutes;
    @ManyToOne @JoinColumn(name = "chapter_id", nullable = false) private Chapter chapter;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL) private List<Question> questions;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL) private List<QuizResult> results;
}
