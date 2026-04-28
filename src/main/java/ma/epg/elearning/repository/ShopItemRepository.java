package ma.epg.elearning.repository;
import ma.epg.elearning.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {
    List<ShopItem> findByCourseId(Long courseId);
}
