package ma.epg.elearning.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.dto.EtudiantRegisterDTO;
import ma.epg.elearning.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() { return "auth/login"; }

    @GetMapping("/auth/inscription")
    public String inscriptionForm(Model model) {
        model.addAttribute("dto", new EtudiantRegisterDTO());
        return "auth/inscription";
    }

    @PostMapping("/auth/inscription")
    public String inscrire(@Valid @ModelAttribute("dto") EtudiantRegisterDTO dto,
                           BindingResult result, Model model) {
        if (result.hasErrors()) return "auth/inscription";
        try {
            userService.inscrireEtudiant(dto);
            return "redirect:/auth/inscription?emailEnvoye=true";
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("erreur", e.getMessage());
            return "auth/inscription";
        }
    }

    @GetMapping("/auth/activer")
    public String activer(@RequestParam String token, Model model) {
        try {
            userService.activerCompte(token);
            return "redirect:/login?compteActive=true";
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("erreur", e.getMessage());
            return "auth/activation-erreur";
        }
    }
}
