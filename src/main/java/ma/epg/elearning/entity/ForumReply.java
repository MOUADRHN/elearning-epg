package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name = "forum_replies")
@Data @NoArgsConstructor @AllArgsConstructor
public class ForumReply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(columnDefinition = "TEXT", nullable = false) private String contenu;
    private LocalDateTime creeLe;
    @ManyToOne @JoinColumn(name = "auteur_id", nullable = false) private User auteur;
    @ManyToOne @JoinColumn(name = "post_id", nullable = false) private ForumPost post;
}
