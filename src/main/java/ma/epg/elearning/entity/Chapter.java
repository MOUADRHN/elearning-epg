package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Table(name = "chapters")
@Data @NoArgsConstructor @AllArgsConstructor
public class Chapter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    private int ordre;
    @ManyToOne @JoinColumn(name = "course_id", nullable = false) private Course course;
    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true) @OrderBy("ordre ASC") private List<Video> videos;
    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL) private List<Quiz> quizzes;
    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL) private List<Assignment> assignments;
}
