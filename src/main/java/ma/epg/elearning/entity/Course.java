package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Table(name = "courses")
@Data @NoArgsConstructor @AllArgsConstructor
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    @Column(columnDefinition = "TEXT") private String description;
    @ManyToOne @JoinColumn(name = "enseignant_id") private Enseignant enseignant;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) private List<CourseAffectation> affectations;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true) @OrderBy("ordre ASC") private List<Chapter> chapters;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) private List<ModuleScore> moduleScores;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) private List<ForumPost> forumPosts;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) private List<Announcement> announcements;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) private List<ShopItem> shopItems;
}
