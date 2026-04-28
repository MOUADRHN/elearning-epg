package ma.epg.elearning.repository;
import ma.epg.elearning.entity.ShopPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ShopPurchaseRepository extends JpaRepository<ShopPurchase, Long> {
    List<ShopPurchase> findByEtudiantId(Long etudiantId);
    boolean existsByEtudiantIdAndItemId(Long etudiantId, Long itemId);
}
