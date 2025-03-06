package com.sportprog.prog.controller;

import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.repository.UtilisateurRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import java.util.Optional;

@Controller
public class MyProfileController {

    @Autowired
    private UtilisateurRepository UtilisateurRepository; // Utilisation de l'instance injectée

    @GetMapping("/my_profile")
    public String myProfile(HttpServletRequest request, Model model) {
    String user_email = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            user_email += cookie.getName();
            if ("user_email".equals(cookie.getName())) {
                user_email = cookie.getValue(); // Récupération du user_email
                if (user_email != null) {
                    // Recherche de l'utilisateur par son ID dans la base de données
                    Optional<Utilisateur> utilisateurOpt = UtilisateurRepository.findByEmail(user_email);
                    if (utilisateurOpt.isPresent()) {
                        // Si l'utilisateur est trouvé, on l'ajoute au modèle
                        model.addAttribute("utilisateur", utilisateurOpt.get());
                        return "my_profile"; // Retourne la vue "my_profile"
                    } else {
                        // Si l'utilisateur n'est pas trouvé, afficher un message d'erreur
                        model.addAttribute("error", "Utilisateur non trouvé."+user_email);
                        return "error";
                    }
                }
                break;
            }
        }
    }
    model.addAttribute("error", "Pas de cookie de connection.");
    return "error";
    }


    @GetMapping("/update_profile")
    public String profile_update(HttpServletRequest request, Model model) {
        String user_email = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user_email".equals(cookie.getName())) {
                    user_email = cookie.getValue();
                    if (user_email != null) {
                        Optional<Utilisateur> utilisateurOpt = UtilisateurRepository.findByEmail(user_email);
                        if (utilisateurOpt.isPresent()) {
                            model.addAttribute("utilisateur", utilisateurOpt.get());
                            return "update_profile"; // Retourne la vue "my_profile"
                        }
                    }
                    break;
                }
            }
        }
        model.addAttribute("error", "Pas de cookie de connection.");
        return "error";
        }


    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    @PostMapping("/update_profile")
    public String updateProfile(@RequestParam Long userId,
                                @RequestParam String nom,
                                @RequestParam Integer age,
                                @RequestParam String genre,
                                @RequestParam String pathologie) {
        try {
            // Récupère l'utilisateur depuis la base de données
            Utilisateur utilisateur = UtilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Met à jour les informations de l'utilisateur (sauf l'email)
            utilisateur.setNom(nom);
            utilisateur.setAge(age);
            utilisateur.setGenre(genre);
            utilisateur.setPathologie(pathologie);

            // Sauvegarde les modifications dans la base de données
            UtilisateurRepository.save(utilisateur);

            // Redirige vers la page de profil
            return "redirect:/my_profile";
        } catch (Exception e) {
            // Log l'erreur et redirige vers une page d'erreur
            System.err.println("Erreur lors de la mise à jour du profil : " + e.getMessage());
            return "redirect:/error"; // Redirige vers une page d'erreur personnalisée
        }
    }
}
