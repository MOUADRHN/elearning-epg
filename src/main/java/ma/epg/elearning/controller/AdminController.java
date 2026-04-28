package ma.epg.elearning.controller;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.service.*;
import ma.epg.elearning.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final CourseService courseService;
    private final FiliereRepository filiereRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers",    userService.findAll().size());
        model.addAttribute("totalCourses",  courseService.findAll().size());
        model.addAttribute("totalFilieres", filiereRepository.count());
        return "admin/dashboard";
    }

    @GetMapping("/utilisateurs")
    public String utilisateurs(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/utilisateurs";
    }

    @PostMapping("/utilisateurs/etudiant")
    public String creerEtudiant(@ModelAttribute Etudiant e) {
        userService.creerEtudiant(e); return "redirect:/admin/utilisateurs";
    }

    @PostMapping("/utilisateurs/enseignant")
    public String creerEnseignant(@ModelAttribute Enseignant e) {
        userService.creerEnseignant(e); return "redirect:/admin/utilisateurs";
    }

    @PostMapping("/utilisateurs/{id}/desactiver")
    public String desactiver(@PathVariable Long id) {
        userService.desactiverCompte(id); return "redirect:/admin/utilisateurs";
    }

    @GetMapping("/filieres")
    public String filieres(Model model) {
        model.addAttribute("filieres", filiereRepository.findAll());
        model.addAttribute("filiere", new Filiere());
        return "admin/filieres";
    }

    @PostMapping("/filieres")
    public String creerFiliere(@ModelAttribute Filiere f) {
        filiereRepository.save(f); return "redirect:/admin/filieres";
    }

    @PostMapping("/filieres/{id}/supprimer")
    public String supprimerFiliere(@PathVariable Long id) {
        filiereRepository.deleteById(id); return "redirect:/admin/filieres";
    }

    @GetMapping("/cours")
    public String cours(Model model) {
        model.addAttribute("courses",  courseService.findAll());
        model.addAttribute("filieres", filiereRepository.findAll());
        model.addAttribute("course",   new Course());
        return "admin/cours";
    }

    @PostMapping("/cours")
    public String creerCours(@ModelAttribute Course course) {
        courseService.creer(course); return "redirect:/admin/cours";
    }

    @PostMapping("/cours/{id}/supprimer")
    public String supprimerCours(@PathVariable Long id) {
        courseService.supprimer(id); return "redirect:/admin/cours";
    }

    @PostMapping("/cours/{id}/affecter")
    public String affecter(@PathVariable Long id, @RequestParam Long filiereId, @RequestParam int semestre) {
        courseService.affecter(id, filiereId, semestre); return "redirect:/admin/cours";
    }
}
