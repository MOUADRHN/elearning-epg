package ma.epg.elearning.service;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.enums.Role;
import ma.epg.elearning.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EtudiantRepository etudiantRepository;
    private final EnseignantRepository enseignantRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() { return userRepository.findAll(); }
    public User findById(Long id) { return userRepository.findById(id).orElseThrow(); }
    public User findByEmail(String email) { return userRepository.findByEmail(email).orElseThrow(); }

    @Transactional
    public void creerEtudiant(Etudiant e) {
        e.setMotDePasse(passwordEncoder.encode(e.getMotDePasse()));
        e.setRole(Role.ETUDIANT);
        etudiantRepository.save(e);
    }
    @Transactional
    public void creerEnseignant(Enseignant e) {
        e.setMotDePasse(passwordEncoder.encode(e.getMotDePasse()));
        e.setRole(Role.ENSEIGNANT);
        enseignantRepository.save(e);
    }
    @Transactional
    public void desactiverCompte(Long id) {
        User u = findById(id); u.setActif(false); userRepository.save(u);
    }
    @Transactional
    public void changerMotDePasse(Long id, String nouveauMdp) {
        User u = findById(id); u.setMotDePasse(passwordEncoder.encode(nouveauMdp)); userRepository.save(u);
    }
    @Transactional
    public void updateProfil(Long id, String nom, String prenom) {
        User u = findById(id);
        u.setNom(nom);
        u.setPrenom(prenom);
        userRepository.save(u);
    }
}
