package ma.epg.elearning.service;

import lombok.RequiredArgsConstructor;
import ma.epg.elearning.dto.EtudiantRegisterDTO;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.enums.Role;
import ma.epg.elearning.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EtudiantRepository etudiantRepository;
    private final EnseignantRepository enseignantRepository;
    // NOUVEAU
    private final EtudiantPreInscritRepository preInscritRepository;
    private final ActivationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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
        User u = findById(id);
        u.setActif(false);
        userRepository.save(u);
    }

    @Transactional
    public void changerMotDePasse(Long id, String nouveauMdp) {
        User u = findById(id);
        u.setMotDePasse(passwordEncoder.encode(nouveauMdp));
        userRepository.save(u);
    }

    @Transactional
    public void updateProfil(Long id, String nom, String prenom) {
        User u = findById(id);
        u.setNom(nom);
        u.setPrenom(prenom);
        userRepository.save(u);
    }

    // NOUVEAU : met à jour spécialité et grade de l'enseignant
    @Transactional
    public void updateProfilEnseignant(Long id, String nom, String prenom,
                                       String specialite, String grade) {
        User u = findById(id);
        if (!(u instanceof Enseignant enseignant)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un enseignant");
        }
        enseignant.setNom(nom);
        enseignant.setPrenom(prenom);
        enseignant.setSpecialite(specialite);
        enseignant.setGrade(grade);
        enseignantRepository.save(enseignant);
    }

    // NOUVEAU : inscription étudiant avec vérification CNE + email activation
    @Transactional
    public void inscrireEtudiant(EtudiantRegisterDTO dto) {
        // 1. Vérifier le CNE dans la table de référence
        EtudiantPreInscrit preInscrit = preInscritRepository.findByCne(dto.getCne())
                .orElseThrow(() -> new IllegalArgumentException(
                        "CNE introuvable. Vérifiez votre numéro ou contactez l'administration."));

        if (preInscrit.isCompteCreee()) {
            throw new IllegalStateException("Un compte existe déjà pour ce CNE.");
        }

        // 2. Vérifier unicité email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Cet email est déjà utilisé.");
        }

        // 3. Créer le compte étudiant inactif (actif = false jusqu'à activation)
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(preInscrit.getNom());
        etudiant.setPrenom(preInscrit.getPrenom());
        etudiant.setCne(dto.getCne());
        etudiant.setEmail(dto.getEmail());
        etudiant.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        etudiant.setRole(Role.ETUDIANT);
        etudiant.setActif(false);
        etudiant.setFiliere(preInscrit.getFiliere());
        etudiant.setSemestre(preInscrit.getSemestre());
        Etudiant saved = etudiantRepository.save(etudiant);

        // 4. Générer token d'activation (valide 24h)
        String token = UUID.randomUUID().toString();
        ActivationToken at = new ActivationToken();
        at.setToken(token);
        at.setEtudiantId(saved.getId());
        at.setExpiresAt(LocalDateTime.now().plusHours(24));
        at.setUsed(false);
        tokenRepository.save(at);

        // 5. Marquer le pré-inscrit
        preInscrit.setCompteCreee(true);
        preInscritRepository.save(preInscrit);

        // 6. Envoyer l'email
        emailService.sendActivation(dto.getEmail(), token);
    }

    // NOUVEAU : activation du compte via le lien email
    @Transactional
    public void activerCompte(String token) {
        ActivationToken at = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Lien d'activation invalide."));

        if (at.isUsed()) {
            throw new IllegalStateException("Ce lien a déjà été utilisé.");
        }
        if (at.isExpire()) {
            throw new IllegalStateException("Ce lien a expiré. Veuillez vous réinscrire.");
        }

        User user = findById(at.getEtudiantId());
        user.setActif(true);
        userRepository.save(user);

        at.setUsed(true);
        tokenRepository.save(at);
    }
}
