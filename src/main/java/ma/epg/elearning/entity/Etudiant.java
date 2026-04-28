package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @DiscriminatorValue("ETUDIANT")
@Data @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Etudiant extends User {
    @Column(unique = true) private String cne;
    private int semestre;
    private int pointsSolde = 0;
    @ManyToOne @JoinColumn(name = "filiere_id") private Filiere filiere;
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL) private List<VideoProgress> videoProgresses;
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL) private List<QuizResult> quizResults;
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL) private List<ModuleScore> moduleScores;
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL) private List<AssignmentSubmission> submissions;
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL) private List<ShopPurchase> purchases;
    @OneToOne(mappedBy = "etudiant", cascade = CascadeType.ALL) private Certificate certificate;
}
