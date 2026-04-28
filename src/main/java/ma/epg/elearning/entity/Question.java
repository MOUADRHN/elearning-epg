package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import ma.epg.elearning.enums.QuestionMode;
@Entity @Table(name = "questions")
@Data @NoArgsConstructor @AllArgsConstructor
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(columnDefinition = "TEXT", nullable = false) private String contenu;
    private String bonneReponse;
    private int timestamp;
    private int points;
    @Enumerated(EnumType.STRING) private QuestionMode mode;
    @ManyToOne @JoinColumn(name = "video_id") private Video video;
    @ManyToOne @JoinColumn(name = "quiz_id") private Quiz quiz;
}
