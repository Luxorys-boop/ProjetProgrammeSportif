package com.sportprog.prog.controller;
import com.sportprog.prog.model.Evaluation;
import com.sportprog.prog.repository.ActivityRepository;
import com.sportprog.prog.repository.EvaluationRepository;

import com.sportprog.prog.model.Activity;
import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.repository.UtilisateurRepository;
import com.sportprog.prog.service.ActivityService;
import com.sportprog.prog.service.EvaluationService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import java.util.Optional;
import java.util.Set;

@Controller
public class MyProfileController {

    @Autowired
    private ActivityRepository activityRepository; // Injection de ActivityRepository

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired EvaluationService evaluationService;

    private Utilisateur getUtilisateurConnecte(HttpServletRequest request) {
        String user_email = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
        for (Cookie cookie : cookies) {
            user_email = cookie.getName();
            if ("user_email".equals(cookie.getName())) {
                user_email = cookie.getValue();
                if (user_email != null) {
                    Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(user_email);
                    if (utilisateurOpt.isPresent()) {
                        return utilisateurOpt.get();
                    } else {
                        return null;
                    }}}}
                }
                return null;
    }

    @GetMapping("/my_profile")
    public String myProfile(HttpServletRequest request, Model model) {
    String user_email = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            user_email = cookie.getName();
            if ("user_email".equals(cookie.getName())) {
                user_email = cookie.getValue(); // Récupération du user_email
                if (user_email != null) {
                    // Recherche de l'utilisateur par son ID dans la base de données
                    Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(user_email);
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

    @GetMapping("/mesactivites")
    public String mesActivites(Model model, HttpServletRequest request) {
        // Récupérer l'utilisateur connecté
        
        Utilisateur utilisateur = getUtilisateurConnecte(request);
        if (utilisateur == null) {
            return "redirect:/login"; // Redirige vers la connexion si l'utilisateur n'est pas trouvé
        }

        // Récupérer les activités de l'utilisateur via la table utilisateur_activite
        List<Activity> activites = activityService.findActivitiesByUserId(utilisateur.getId());

        // Créer une map pour stocker les activités et leurs évaluations
        Map<Activity, Evaluation> activitesAvecEvaluations = new HashMap<>();
        // Pour chaque activité, récupérer l'évaluation de l'utilisateur (s'il y en a une)
        for (Activity activite : activites) {            
            Optional<Evaluation> evaluationOpt = evaluationService.findByUtilisateurAndActivity(utilisateur, activite);
            evaluationOpt.ifPresent(evaluation -> activitesAvecEvaluations.put(activite, evaluation));
            System.out.println(activite.getNom() + " : " +evaluationOpt.get().getNote() + "FDPPPPPPPPPPPPPPPPPPPPP");
        }

        // Ajouter les données au modèle
        model.addAttribute("activitesAvecEvaluations", activitesAvecEvaluations);
        model.addAttribute("utilisateur", utilisateur);

        return "mesactivites"; // Affichage dans la vue Thymeleaf
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
                        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(user_email);
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
            Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Met à jour les informations de l'utilisateur (sauf l'email)
            utilisateur.setNom(nom);
            utilisateur.setAge(age);
            utilisateur.setGenre(genre);
            utilisateur.setPathologie(pathologie);

            // Sauvegarde les modifications dans la base de données
            utilisateurRepository.save(utilisateur);

            // Redirige vers la page de profil
            return "redirect:/my_profile";
        } catch (Exception e) {
            // Log l'erreur et redirige vers une page d'erreur
            System.err.println("Erreur lors de la mise à jour du profil : " + e.getMessage());
            return "redirect:/error"; // Redirige vers une page d'erreur personnalisée
        }
    }
    
}
