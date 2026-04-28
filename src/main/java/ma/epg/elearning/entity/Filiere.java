package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Table(name = "filieres")
@Data @NoArgsConstructor @AllArgsConstructor
public class Filiere {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true) private String nom;
    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL) private List<Etudiant> etudiants;
    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL) private List<CourseAffectation> affectations;
}
