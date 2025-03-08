package com.sportprog.prog.controller;

import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.repository.UtilisateurRepository;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Affiche la page register.html
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String nom,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam int age,
            @RequestParam String genre,
            @RequestParam List<String> pathologies,

            Model model) {

        // Vérifie si l'utilisateur existe déjà
        if (utilisateurRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Cet email est déjà utilisé.");
            return "register";
        }

        // Crée un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setEmail(email);
        utilisateur.setUsername(username);
        utilisateur.setPassword(passwordEncoder.encode(password)); // Encode le mot de passe
        utilisateur.setAge(age);
        utilisateur.setGenre(genre);
        utilisateur.setPathologies(pathologies);

        // Enregistre l'utilisateur dans la base de données
        utilisateurRepository.save(utilisateur);

        return "redirect:/login"; // Redirige vers la page de login après l'inscription
    }
}