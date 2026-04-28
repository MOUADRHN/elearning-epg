package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name = "shop_purchases")
@Data @NoArgsConstructor @AllArgsConstructor
public class ShopPurchase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private LocalDateTime acheteLe;
    @ManyToOne @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
    @ManyToOne @JoinColumn(name = "item_id", nullable = false) private ShopItem item;
}
