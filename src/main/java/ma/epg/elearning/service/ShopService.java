package ma.epg.elearning.service;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class ShopService {
    private final ShopItemRepository shopItemRepository;
    private final ShopPurchaseRepository shopPurchaseRepository;
    private final EtudiantRepository etudiantRepository;

    public List<ShopItem> findByCourse(Long courseId) { return shopItemRepository.findByCourseId(courseId); }

    @Transactional
    public void acheter(Long etudiantId, Long itemId) {
        if (shopPurchaseRepository.existsByEtudiantIdAndItemId(etudiantId, itemId))
            throw new RuntimeException("Item deja achete");
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElseThrow();
        ShopItem item = shopItemRepository.findById(itemId).orElseThrow();
        if (etudiant.getPointsSolde() < item.getCoutPoints())
            throw new RuntimeException("Solde insuffisant");
        etudiant.setPointsSolde(etudiant.getPointsSolde() - item.getCoutPoints());
        etudiantRepository.save(etudiant);
        ShopPurchase purchase = new ShopPurchase();
        purchase.setEtudiant(etudiant); purchase.setItem(item); purchase.setAcheteLe(LocalDateTime.now());
        shopPurchaseRepository.save(purchase);
    }
}
