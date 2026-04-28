package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity @Table(name = "forum_posts")
@Data @NoArgsConstructor @AllArgsConstructor
public class ForumPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    @Column(columnDefinition = "TEXT") private String contenu;
    private LocalDateTime creeLe;
    @ManyToOne @JoinColumn(name = "auteur_id", nullable = false) private User auteur;
    @ManyToOne @JoinColumn(name = "course_id", nullable = false) private Course course;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) private List<ForumReply> replies;
}
