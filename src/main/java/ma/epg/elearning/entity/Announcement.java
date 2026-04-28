package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name = "announcements")
@Data @NoArgsConstructor @AllArgsConstructor
public class Announcement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    @Column(columnDefinition = "TEXT") private String contenu;
    private LocalDateTime creeLe;
    @ManyToOne @JoinColumn(name = "course_id") private Course course;
    @ManyToOne @JoinColumn(name = "auteur_id", nullable = false) private User auteur;
}
