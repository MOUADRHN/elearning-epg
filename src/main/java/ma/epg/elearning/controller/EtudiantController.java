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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/etudiant")
@PreAuthorize("hasRole('ETUDIANT')")
@RequiredArgsConstructor
public class EtudiantController {
    private final CourseService courseService;
    private final QuizService quizService;
    private final ScoreService scoreService;
    private final ShopService shopService;
    private final UserService userService;
    private final VideoRepository videoRepository;
    private final VideoProgressRepository videoProgressRepository;
    private final VideoNoteRepository videoNoteRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final ForumPostRepository forumPostRepository;
    private final ForumReplyRepository forumReplyRepository;
    private final AnnouncementRepository announcementRepository;
    private final CertificateRepository certificateRepository;
    private final ModuleScoreRepository moduleScoreRepository;
    private final ChapterRepository chapterRepository;

    private Etudiant getEtudiant(UserDetails ud) {
        return (Etudiant) userService.findByEmail(ud.getUsername());
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails ud, Model model) {
        Etudiant etu = getEtudiant(ud);
        model.addAttribute("courses",  courseService.findCoursEtudiant(etu.getFiliere().getId(), etu.getSemestre()));
        model.addAttribute("annonces", announcementRepository.findAnnoncesGlobales());
        return "etudiant/dashboard";
    }

    @GetMapping("/videos/{videoId}")
    public String visionnerVideo(@PathVariable Long videoId, @AuthenticationPrincipal UserDetails ud, Model model) {
        Etudiant etu = getEtudiant(ud);
        Video video = videoRepository.findById(videoId).orElseThrow();
        VideoProgress progress = videoProgressRepository.findByEtudiantIdAndVideoId(etu.getId(), videoId)
            .orElseGet(() -> { VideoProgress vp = new VideoProgress(); vp.setEtudiant(etu); vp.setVideo(video); return vp; });
        model.addAttribute("video", video);
        model.addAttribute("progress", progress);
        model.addAttribute("notes", videoNoteRepository.findByEtudiantIdAndVideoId(etu.getId(), videoId));
        return "etudiant/video";
    }

    @PostMapping("/videos/{videoId}/progression")
    @ResponseBody
    public String mettreAJourProgression(@PathVariable Long videoId, @RequestParam int pourcentage,
                                         @AuthenticationPrincipal UserDetails ud) {
        Etudiant etu = getEtudiant(ud);
        VideoProgress vp = videoProgressRepository.findByEtudiantIdAndVideoId(etu.getId(), videoId)
            .orElseGet(() -> { VideoProgress n = new VideoProgress(); n.setEtudiant(etu); Video v = new Video(); v.setId(videoId); n.setVideo(v); return n; });
        if (pourcentage > vp.getPourcentageVu()) {
            vp.setPourcentageVu(pourcentage);
            if (pourcentage >= 90 && vp.getPointsGagnes() == 0) {
                vp.setPointsGagnes(10);
                Video video = videoRepository.findById(videoId).orElseThrow();
                scoreService.ajouterPoints(etu.getId(), video.getChapter().getCourse().getId(), 10);
            }
            videoProgressRepository.save(vp);
        }
        return "ok";
    }

    @PostMapping("/videos/{videoId}/notes")
    public String ajouterNote(@PathVariable Long videoId, @RequestParam String contenu,
                              @RequestParam int timestamp, @AuthenticationPrincipal UserDetails ud) {
        Etudiant etu = getEtudiant(ud);
        VideoNote note = new VideoNote();
        note.setContenu(contenu); note.setTimestamp(timestamp); note.setEtudiant(etu);
        Video v = new Video(); v.setId(videoId); note.setVideo(v);
        videoNoteRepository.save(note);
        return "redirect:/etudiant/videos/" + videoId;
    }

    @GetMapping("/quiz/{quizId}")
    public String voirQuiz(@PathVariable Long quizId, Model model) {
        model.addAttribute("quiz", quizService.findById(quizId));
        return "etudiant/quiz";
    }

    @PostMapping("/quiz/{quizId}/soumettre")
    public String soumettreQuiz(@PathVariable Long quizId, @RequestParam Map<String, String> params,
                                @AuthenticationPrincipal UserDetails ud, Model model) {
        Etudiant etu = getEtudiant(ud);
        Map<Long, String> reponses = new HashMap<>();
        params.forEach((k, v) -> { if (k.startsWith("q_")) reponses.put(Long.parseLong(k.substring(2)), v); });
        QuizResult result = quizService.soumettre(quizId, etu.getId(), reponses);
        scoreService.ajouterPoints(etu.getId(), quizService.findById(quizId).getChapter().getCourse().getId(), result.getPointsGagnes());
        model.addAttribute("result", result);
        return "etudiant/quiz-result";
    }

    @PostMapping("/devoirs/{assignmentId}/soumettre")
    public String soumettreDevoir(@PathVariable Long assignmentId, @RequestParam MultipartFile fichier,
                                  @AuthenticationPrincipal UserDetails ud) throws IOException {
        Etudiant etu = getEtudiant(ud);
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow();
        if (LocalDateTime.now().isAfter(assignment.getDateLimite()))
            return "redirect:/etudiant/dashboard?erreur=dateLimite";
        String nom = System.currentTimeMillis() + "_" + fichier.getOriginalFilename();
        Path dest = Paths.get("uploads/devoirs/" + nom);
        Files.createDirectories(dest.getParent()); Files.copy(fichier.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
        AssignmentSubmission sub = new AssignmentSubmission();
        sub.setAssignment(assignment); sub.setEtudiant(etu);
        sub.setCheminFichier("uploads/devoirs/" + nom); sub.setSoumisLe(LocalDateTime.now());
        submissionRepository.save(sub);
        return "redirect:/etudiant/dashboard";
    }

    @GetMapping("/scores")
    public String scores(@AuthenticationPrincipal UserDetails ud, Model model) {
        model.addAttribute("scores", moduleScoreRepository.findByEtudiantId(getEtudiant(ud).getId()));
        return "etudiant/scores";
    }

    @GetMapping("/cours/{courseId}/forum")
    public String forum(@PathVariable Long courseId, Model model) {
        model.addAttribute("posts",  forumPostRepository.findByCourseIdOrderByCreeLe(courseId));
        model.addAttribute("course", courseService.findById(courseId));
        return "etudiant/forum";
    }
    @GetMapping("/cours/{courseId}")
    public String voirCours(@PathVariable Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("chapters", chapterRepository.findByCourseIdOrderByOrdreAsc(courseId));
        return "etudiant/cours";
    }
    @GetMapping("/profil")
    public String profil(@AuthenticationPrincipal UserDetails ud, Model model) {
        model.addAttribute("user", userService.findByEmail(ud.getUsername()));
        return "etudiant/profil";
    }

    @PostMapping("/profil")
    public String updateProfil(@AuthenticationPrincipal UserDetails ud,
                               @RequestParam String nom,
                               @RequestParam String prenom,
                               @RequestParam(required = false) String nouveauMdp) {
        Etudiant etu = getEtudiant(ud);
        userService.updateProfil(etu.getId(), nom, prenom);
        if (nouveauMdp != null && !nouveauMdp.isBlank()) {
            userService.changerMotDePasse(etu.getId(), nouveauMdp);
        }
        return "redirect:/etudiant/profil?success=true";
    }
    @PostMapping("/cours/{courseId}/forum")
    public String creerPost(@PathVariable Long courseId, @RequestParam String titre,
                            @RequestParam String contenu, @AuthenticationPrincipal UserDetails ud) {
        Etudiant etu = getEtudiant(ud);
        ForumPost post = new ForumPost();
        post.setTitre(titre); post.setContenu(contenu);
        post.setCreeLe(LocalDateTime.now()); post.setAuteur(etu);
        post.setCourse(courseService.findById(courseId));
        forumPostRepository.save(post);
        return "redirect:/etudiant/cours/" + courseId + "/forum";
    }

    @PostMapping("/forum/{postId}/repondre")
    public String repondre(@PathVariable Long postId, @RequestParam String contenu,
                           @AuthenticationPrincipal UserDetails ud) {
        Etudiant etu = getEtudiant(ud);
        ForumPost post = forumPostRepository.findById(postId).orElseThrow();
        ForumReply reply = new ForumReply();
        reply.setContenu(contenu); reply.setCreeLe(LocalDateTime.now());
        reply.setAuteur(etu); reply.setPost(post);
        forumReplyRepository.save(reply);
        return "redirect:/etudiant/cours/" + post.getCourse().getId() + "/forum";
    }

    @GetMapping("/cours/{courseId}/boutique")
    public String boutique(@PathVariable Long courseId, @AuthenticationPrincipal UserDetails ud, Model model) {
        model.addAttribute("items",       shopService.findByCourse(courseId));
        model.addAttribute("pointsSolde", getEtudiant(ud).getPointsSolde());
        return "etudiant/boutique";
    }

    @PostMapping("/boutique/{itemId}/acheter")
    public String acheter(@PathVariable Long itemId, @AuthenticationPrincipal UserDetails ud) {
        shopService.acheter(getEtudiant(ud).getId(), itemId);
        return "redirect:/etudiant/dashboard";
    }

    @GetMapping("/certificat")
    public String certificat(@AuthenticationPrincipal UserDetails ud, Model model) {
        certificateRepository.findByEtudiantId(getEtudiant(ud).getId())
            .ifPresent(c -> model.addAttribute("certificat", c));
        return "etudiant/certificat";
    }
}
