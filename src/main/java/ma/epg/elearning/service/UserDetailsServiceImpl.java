package ma.epg.elearning.service;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.entity.User;
import ma.epg.elearning.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + email));
        if (!user.isActif()) throw new UsernameNotFoundException("Compte desactive");
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getMotDePasse(),
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
