package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Table(name = "shop_items")
@Data @NoArgsConstructor @AllArgsConstructor
public class ShopItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    private int coutPoints;
    private String cheminFichier;
    @ManyToOne @JoinColumn(name = "enseignant_id", nullable = false) private Enseignant enseignant;
    @ManyToOne @JoinColumn(name = "course_id", nullable = false) private Course course;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL) private List<ShopPurchase> purchases;
}
