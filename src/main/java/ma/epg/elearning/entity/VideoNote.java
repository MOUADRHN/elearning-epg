package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name = "video_notes")
@Data @NoArgsConstructor @AllArgsConstructor
public class VideoNote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(columnDefinition = "TEXT") private String contenu;
    private int timestamp;
    @ManyToOne @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
    @ManyToOne @JoinColumn(name = "video_id", nullable = false) private Video video;
}
