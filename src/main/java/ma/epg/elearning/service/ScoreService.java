package ma.epg.elearning.service;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class ScoreService {
    private final ModuleScoreRepository moduleScoreRepository;
    private static final int MAX_POINTS = 1000;

    @Transactional
    public void ajouterPoints(Long etudiantId, Long courseId, int points) {
        ModuleScore ms = moduleScoreRepository.findByEtudiantIdAndCourseId(etudiantId, courseId)
            .orElseGet(() -> {
                ModuleScore n = new ModuleScore();
                Etudiant e = new Etudiant(); e.setId(etudiantId);
                Course c = new Course(); c.setId(courseId);
                n.setEtudiant(e); n.setCourse(c); return n;
            });
        ms.setPointsPlateforme(ms.getPointsPlateforme() + points);
        ms.calculerNoteFinale(MAX_POINTS);
        moduleScoreRepository.save(ms);
    }
    @Transactional
    public void saisirNoteExamen(Long etudiantId, Long courseId, Double note) {
        ModuleScore ms = moduleScoreRepository.findByEtudiantIdAndCourseId(etudiantId, courseId).orElseThrow();
        ms.setNoteExamen(note); ms.calculerNoteFinale(MAX_POINTS); moduleScoreRepository.save(ms);
    }
    public List<ModuleScore> getClassement(Long courseId) {
        return moduleScoreRepository.findClassementByCourse(courseId);
    }
}
