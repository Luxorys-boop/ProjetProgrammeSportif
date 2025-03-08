package com.sportprog.prog.controller;

import com.sportprog.prog.dto.CategoryDTO;
import com.sportprog.prog.model.Activity;
import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.repository.ActivityRepository;
import com.sportprog.prog.repository.UtilisateurRepository;
import com.sportprog.prog.service.ActivityService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository; // Injection de ActivityRepository

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/activities")
    public String afficherCategories(Model model) {
        // Récupérer les catégories distinctes sous forme de CategoryDTO
        List<CategoryDTO> categories = activityService.getDistinctCategories();
        
        // Ajouter les catégories au modèle
        model.addAttribute("categories", categories);
        
        // Retourner le nom de la vue Thymeleaf
        return "activities";
    }

    @GetMapping("/activites/categorie/{id}")
    public String afficherActivitesParCategorie(@PathVariable Long id, Model model) {
        List<Activity> activites = activityService.findActivitiesByCategoryId(id);
        model.addAttribute("activites", activites);
        if (activites.isEmpty()) {
            // Si la liste est vide, ajouter un message d'erreur ou rediriger
            model.addAttribute("errorMessage", "Aucune activité trouvée pour cette catégorie.");
            return "activites-par-categorie"; // Retourner la même vue avec un message d'erreur
        }
        
        // Ajouter les activités et le nom de la catégorie au modèle
        model.addAttribute("activites", activites);
        model.addAttribute("categorie", activites.get(0).getCategories().iterator().next().getNom()); // Nom de la catégorie
        return "activites-par-categorie";
    }

    @Transactional
    @GetMapping("/inscrire/{activityId}")
    public String inscrireUtilisateur(@PathVariable Long activityId, @CookieValue(value = "user_email", required = false) String userEmail) {
        if (userEmail == null) {
            return "redirect:/login"; // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        activityService.inscrireUtilisateur(utilisateur.getId(), activityId);

        return "redirect:/activities"; // Rediriger vers la page des activités
    }


    @Transactional
    @GetMapping("/desinscrire/{activityId}")
    public String desinscrireUtilisateur(@PathVariable Long activityId, @CookieValue(value = "user_email", required = false) String userEmail) {
        if (userEmail == null) {
            return "redirect:/login"; // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        activityService.desinscrireUtilisateur(utilisateur.getId(), activityId);

        return "redirect:/activities"; // Rediriger vers la page des activités
    }

    @GetMapping("/en-savoir-plus/{activityId}")
    public String enSavoirPlus(@PathVariable Long activityId, Model model) {
        // Récupérer l'activité par son ID
        Activity activite = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));

        // Ajouter l'activité et la catégorie au modèle
        model.addAttribute("activite", activite);
        model.addAttribute("categorieId", activite.getCategories().iterator().next().getId());

        return "en-savoir-plus"; // Retourner la vue Thymeleaf
    }
}
