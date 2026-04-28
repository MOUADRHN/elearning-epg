package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name = "certificates")
@Data @NoArgsConstructor @AllArgsConstructor
public class Certificate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String semestre;
    private String cheminPDF;
    private LocalDateTime genereLe;
    @OneToOne @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
}
