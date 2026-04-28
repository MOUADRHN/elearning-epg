package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "video_progress", uniqueConstraints = @UniqueConstraint(columnNames = {"etudiant_id","video_id"}))
@Data @NoArgsConstructor @AllArgsConstructor
public class VideoProgress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private int pourcentageVu;
    private int pointsGagnes;
    @ManyToOne @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
    @ManyToOne @JoinColumn(name = "video_id", nullable = false) private Video video;
}
