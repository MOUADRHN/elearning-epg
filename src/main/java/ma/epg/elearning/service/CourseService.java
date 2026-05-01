package ma.epg.elearning.service;

import lombok.RequiredArgsConstructor;
import ma.epg.elearning.dto.CourseAffectationDTO;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseAffectationRepository affectationRepository;
    private final ChapterRepository chapterRepository;
    private final FiliereRepository filiereRepository;

    public List<Course> findAll() { return courseRepository.findAll(); }
    public Course findById(Long id) { return courseRepository.findById(id).orElseThrow(); }
    public List<Course> findByEnseignant(Long id) { return courseRepository.findByEnseignantId(id); }
    public List<Course> findCoursEtudiant(Long filiereId, int semestre) {
        return courseRepository.findByFiliereAndSemestre(filiereId, semestre);
    }

    @Transactional public Course creer(Course c) { return courseRepository.save(c); }
    @Transactional public Course modifier(Course c) { return courseRepository.save(c); }
    @Transactional public void supprimer(Long id) { courseRepository.deleteById(id); }

    // BUG CORRIGE : setFiliere() manquait dans l'ancienne version
    @Transactional
    public void affecter(Long courseId, Long filiereId, int semestre) {
        if (!affectationRepository.existsByCourseIdAndFiliereIdAndSemestre(courseId, filiereId, semestre)) {
            CourseAffectation a = new CourseAffectation();
            a.setCourse(courseRepository.findById(courseId).orElseThrow());
            a.setFiliere(filiereRepository.findById(filiereId).orElseThrow()); // CORRECTION
            a.setSemestre(semestre);
            affectationRepository.save(a);
        }
    }

    // NOUVEAU : affectation multiple filieres/semestres en une requete
    @Transactional
    public void affecterMultiple(Long courseId, List<CourseAffectationDTO> liste) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        for (CourseAffectationDTO dto : liste) {
            if (!affectationRepository.existsByCourseIdAndFiliereIdAndSemestre(
                    courseId, dto.getFiliereId(), dto.getSemestre())) {
                CourseAffectation a = new CourseAffectation();
                a.setCourse(course);
                a.setFiliere(filiereRepository.findById(dto.getFiliereId()).orElseThrow());
                a.setSemestre(dto.getSemestre());
                affectationRepository.save(a);
            }
        }
    }

    @Transactional
    public Chapter ajouterChapitre(Chapter chapter) {
        int ordre = chapterRepository.findByCourseIdOrderByOrdreAsc(chapter.getCourse().getId()).size() + 1;
        chapter.setOrdre(ordre);
        return chapterRepository.save(chapter);
    }
}
