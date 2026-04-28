package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Table(name = "videos")
@Data @NoArgsConstructor @AllArgsConstructor
public class Video {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    private String cheminFichier;
    private int duree;
    private int ordre;
    @ManyToOne @JoinColumn(name = "chapter_id", nullable = false) private Chapter chapter;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL) private List<Question> questions;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL) private List<VideoProgress> progresses;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL) private List<VideoNote> notes;
}
