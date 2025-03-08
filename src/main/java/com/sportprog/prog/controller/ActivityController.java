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
    @GetMapping("/inscrire/{programmeId}")
    public String inscrireUtilisateur(@PathVariable Long programmeId, @CookieValue(value = "user_email", required = false) String userEmail) {
        if (userEmail == null) {
            return "redirect:/login"; // Redirect to login if the user is not logged in
        }

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(userEmail);
        Optional<Activity> activityOpt = activityRepository.findById(programmeId);

        if (utilisateurOpt.isPresent() && activityOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            Activity activity = activityOpt.get();

            // Check if the user is already registered for the activity
            if (!utilisateur.getActivities().contains(activity)) {
                utilisateur.getActivities().add(activity); // Register the user for the activity
                utilisateurRepository.save(utilisateur); // Save changes
            }
        }

        return "redirect:/activities"; // Redirect to recommendations page
    }

    @Transactional
    @GetMapping("/desinscrire/{programmeId}")
    public String desinscrireUtilisateur(@PathVariable Long programmeId, @CookieValue(value = "user_email", required = false) String userEmail) {
        if (userEmail == null) {
            return "redirect:/login"; // Redirect to login if the user is not logged in
        }

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(userEmail);
        Optional<Activity> activityOpt = activityRepository.findById(programmeId);

        if (utilisateurOpt.isPresent() && activityOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            Activity activity = activityOpt.get();

            // Check if the user is registered for the activity
            if (utilisateur.getActivities().contains(activity)) {
                utilisateur.getActivities().remove(activity); // Unregister the user from the activity
                utilisateurRepository.save(utilisateur); // Save changes
            }
        }

        return "redirect:/activities"; // Redirect to recommendations page
    }

}
