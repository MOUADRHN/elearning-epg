package ma.epg.elearning.controller;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.repository.*;
import ma.epg.elearning.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/enseignant")
@PreAuthorize("hasRole('ENSEIGNANT')")
@RequiredArgsConstructor
public class EnseignantController {
    private final CourseService courseService;
    private final QuizService quizService;
    private final ScoreService scoreService;
    private final UserService userService;
    private final ChapterRepository chapterRepository;
    private final VideoRepository videoRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final AnnouncementRepository announcementRepository;
    private final ShopItemRepository shopItemRepository;

    private Enseignant getEnseignant(UserDetails ud) {
        return (Enseignant) userService.findByEmail(ud.getUsername());
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails ud, Model model) {
        model.addAttribute("courses", courseService.findByEnseignant(getEnseignant(ud).getId()));
        return "enseignant/dashboard";
    }

    @GetMapping("/cours/{courseId}/chapitres")
    public String chapitres(@PathVariable Long courseId, Model model) {
        model.addAttribute("course",   courseService.findById(courseId));
        model.addAttribute("chapters", chapterRepository.findByCourseIdOrderByOrdreAsc(courseId));
        return "enseignant/chapitres";
    }

    @PostMapping("/cours/{courseId}/chapitres")
    public String ajouterChapitre(@PathVariable Long courseId, @ModelAttribute Chapter chapter) {
        chapter.setCourse(courseService.findById(courseId));
        courseService.ajouterChapitre(chapter);
        return "redirect:/enseignant/cours/" + courseId + "/chapitres";
    }

    @PostMapping("/chapitres/{chapterId}/videos")
    public String ajouterVideo(@PathVariable Long chapterId, @ModelAttribute Video video,
                               @RequestParam MultipartFile fichier) throws IOException {
        if (!fichier.isEmpty()) {
            String nom = System.currentTimeMillis() + "_" + fichier.getOriginalFilename();
            Path dest = Paths.get("uploads/videos/" + nom);
            Files.createDirectories(dest.getParent());
            Files.copy(fichier.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
            video.setCheminFichier("uploads/videos/" + nom);
        }
        Chapter ch = chapterRepository.findById(chapterId).orElseThrow();
        video.setChapter(ch);
        video.setOrdre(videoRepository.findByChapterIdOrderByOrdreAsc(chapterId).size() + 1);
        videoRepository.save(video);
        return "redirect:/enseignant/cours/" + ch.getCourse().getId() + "/chapitres";
    }

    @PostMapping("/chapitres/{chapterId}/quiz")
    public String creerQuiz(@PathVariable Long chapterId, @ModelAttribute Quiz quiz) {
        Chapter ch = chapterRepository.findById(chapterId).orElseThrow();
        quiz.setChapter(ch); quizRepository.save(quiz);
        return "redirect:/enseignant/cours/" + ch.getCourse().getId() + "/chapitres";
    }

    @PostMapping("/quiz/{quizId}/questions")
    public String ajouterQuestion(@PathVariable Long quizId, @ModelAttribute Question question) {
        question.setQuiz(quizRepository.findById(quizId).orElseThrow());
        questionRepository.save(question);
        return "redirect:/enseignant/cours/1/chapitres";
    }

    @PostMapping("/chapitres/{chapterId}/devoirs")
    public String creerDevoir(@PathVariable Long chapterId, @ModelAttribute Assignment assignment,
                              @AuthenticationPrincipal UserDetails ud) {
        Chapter ch = chapterRepository.findById(chapterId).orElseThrow();
        assignment.setChapter(ch); assignment.setEnseignant(getEnseignant(ud));
        assignmentRepository.save(assignment);
        return "redirect:/enseignant/cours/" + ch.getCourse().getId() + "/chapitres";
    }

    @GetMapping("/devoirs/{assignmentId}/soumissions")
    public String soumissions(@PathVariable Long assignmentId, Model model) {
        model.addAttribute("assignment",  assignmentRepository.findById(assignmentId).orElseThrow());
        model.addAttribute("submissions", submissionRepository.findByAssignmentId(assignmentId));
        return "enseignant/soumissions";
    }

    @PostMapping("/soumissions/{id}/corriger")
    public String corriger(@PathVariable Long id, @RequestParam Double note, @RequestParam String feedback) {
        AssignmentSubmission sub = submissionRepository.findById(id).orElseThrow();
        sub.setNote(note); sub.setFeedback(feedback); submissionRepository.save(sub);
        scoreService.saisirNoteExamen(sub.getEtudiant().getId(),
            sub.getAssignment().getChapter().getCourse().getId(), note);
        return "redirect:/enseignant/devoirs/" + sub.getAssignment().getId() + "/soumissions";
    }

    @PostMapping("/cours/{courseId}/annonces")
    public String publierAnnonce(@PathVariable Long courseId, @ModelAttribute Announcement annonce,
                                 @AuthenticationPrincipal UserDetails ud) {
        annonce.setCourse(courseService.findById(courseId));
        annonce.setAuteur(getEnseignant(ud));
        announcementRepository.save(annonce);
        return "redirect:/enseignant/cours/" + courseId + "/chapitres";
    }

    @PostMapping("/cours/{courseId}/shop")
    public String ajouterItem(@PathVariable Long courseId, @ModelAttribute ShopItem item,
                              @AuthenticationPrincipal UserDetails ud) {
        item.setCourse(courseService.findById(courseId));
        item.setEnseignant(getEnseignant(ud));
        shopItemRepository.save(item);
        return "redirect:/enseignant/cours/" + courseId + "/chapitres";
    }
}
