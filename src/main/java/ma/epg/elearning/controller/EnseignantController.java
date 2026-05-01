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
import java.time.LocalDateTime;

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
    private final ForumPostRepository forumPostRepository;
    private final ForumReplyRepository forumReplyRepository;

    private Enseignant getEnseignant(UserDetails ud) {
        return (Enseignant) userService.findByEmail(ud.getUsername());
    }

    // ── Dashboard ─────────────────────────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails ud, Model model) {
        model.addAttribute("courses", courseService.findByEnseignant(getEnseignant(ud).getId()));
        return "enseignant/dashboard";
    }

    // ── PROFIL (NOUVEAU) ──────────────────────────────────────────────────────
    @GetMapping("/profil")
    public String voirProfil(@AuthenticationPrincipal UserDetails ud, Model model) {
        model.addAttribute("enseignant", getEnseignant(ud));
        return "enseignant/profil";
    }

    @PostMapping("/profil")
    public String modifierProfil(@AuthenticationPrincipal UserDetails ud,
                                 @RequestParam String nom,
                                 @RequestParam String prenom,
                                 @RequestParam String specialite,
                                 @RequestParam String grade) {
        userService.updateProfilEnseignant(getEnseignant(ud).getId(), nom, prenom, specialite, grade);
        return "redirect:/enseignant/profil?success=true";
    }

    // ── Cours / Chapitres ─────────────────────────────────────────────────────
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

    // ── Vidéos ────────────────────────────────────────────────────────────────
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

    // ── Quiz / Questions ──────────────────────────────────────────────────────
    @PostMapping("/chapitres/{chapterId}/quiz")
    public String creerQuiz(@PathVariable Long chapterId, @ModelAttribute Quiz quiz) {
        Chapter ch = chapterRepository.findById(chapterId).orElseThrow();
        quiz.setChapter(ch);
        quizRepository.save(quiz);
        return "redirect:/enseignant/cours/" + ch.getCourse().getId() + "/chapitres";
    }

    // BUG CORRIGE : redirect vers le vrai courseId (avant : "/cours/1/chapitres" en dur)
    @PostMapping("/quiz/{quizId}/questions")
    public String ajouterQuestion(@PathVariable Long quizId, @ModelAttribute Question question) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        question.setQuiz(quiz);
        questionRepository.save(question);
        Long courseId = quiz.getChapter().getCourse().getId(); // ← CORRECTION ICI
        return "redirect:/enseignant/cours/" + courseId + "/chapitres";
    }

    // ── Devoirs ───────────────────────────────────────────────────────────────
    @PostMapping("/chapitres/{chapterId}/devoirs")
    public String creerDevoir(@PathVariable Long chapterId, @ModelAttribute Assignment assignment,
                              @AuthenticationPrincipal UserDetails ud) {
        Chapter ch = chapterRepository.findById(chapterId).orElseThrow();
        assignment.setChapter(ch);
        assignment.setEnseignant(getEnseignant(ud));
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
        sub.setNote(note);
        sub.setFeedback(feedback);
        submissionRepository.save(sub);
        scoreService.saisirNoteExamen(sub.getEtudiant().getId(),
            sub.getAssignment().getChapter().getCourse().getId(), note);
        return "redirect:/enseignant/devoirs/" + sub.getAssignment().getId() + "/soumissions";
    }

    // ── Annonces ──────────────────────────────────────────────────────────────
    @PostMapping("/cours/{courseId}/annonces")
    public String publierAnnonce(@PathVariable Long courseId, @ModelAttribute Announcement annonce,
                                 @AuthenticationPrincipal UserDetails ud) {
        annonce.setCourse(courseService.findById(courseId));
        annonce.setAuteur(getEnseignant(ud));
        announcementRepository.save(annonce);
        return "redirect:/enseignant/cours/" + courseId + "/chapitres";
    }

    // ── Boutique ──────────────────────────────────────────────────────────────
    @PostMapping("/cours/{courseId}/shop")
    public String ajouterItem(@PathVariable Long courseId, @ModelAttribute ShopItem item,
                              @AuthenticationPrincipal UserDetails ud) {
        item.setCourse(courseService.findById(courseId));
        item.setEnseignant(getEnseignant(ud));
        shopItemRepository.save(item);
        return "redirect:/enseignant/cours/" + courseId + "/chapitres";
    }

    // ── FORUM DU COURS (NOUVEAU) ──────────────────────────────────────────────
    @GetMapping("/cours/{courseId}/forum")
    public String voirForum(@PathVariable Long courseId, Model model) {
        model.addAttribute("course", courseService.findById(courseId));
        model.addAttribute("posts",  forumPostRepository.findByCourseIdOrderByCreeLe(courseId));
        return "enseignant/forum";
    }

    @PostMapping("/cours/{courseId}/forum")
    public String creerPost(@PathVariable Long courseId,
                            @RequestParam String titre,
                            @RequestParam String contenu,
                            @AuthenticationPrincipal UserDetails ud) {
        ForumPost post = new ForumPost();
        post.setTitre(titre);
        post.setContenu(contenu);
        post.setCreeLe(LocalDateTime.now());
        post.setCourse(courseService.findById(courseId));
        post.setAuteur(getEnseignant(ud));
        forumPostRepository.save(post);
        return "redirect:/enseignant/cours/" + courseId + "/forum";
    }

    @PostMapping("/forum/{postId}/repondre")
    public String repondre(@PathVariable Long postId,
                           @RequestParam String contenu,
                           @AuthenticationPrincipal UserDetails ud) {
        ForumPost post = forumPostRepository.findById(postId).orElseThrow();
        ForumReply reply = new ForumReply();
        reply.setContenu(contenu);
        reply.setCreeLe(LocalDateTime.now());
        reply.setPost(post);
        reply.setAuteur(getEnseignant(ud));
        forumReplyRepository.save(reply);
        return "redirect:/enseignant/cours/" + post.getCourse().getId() + "/forum";
    }
}
