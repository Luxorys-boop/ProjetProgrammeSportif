package com.sportprog.prog.Login;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.repository.UtilisateurRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepo;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return User.builder()
            .username(utilisateur.getEmail())
            .password(utilisateur.getPassword())
            .build();
    }
}