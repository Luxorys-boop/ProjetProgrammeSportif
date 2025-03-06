package com.sportprog.prog.controller;

import com.sportprog.prog.dto.CategoryDTO;
import com.sportprog.prog.model.Activity;
import com.sportprog.prog.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;

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
        model.addAttribute("categorie", activites.get(0).getCategories().iterator().next().getNom()); // Nom de la catégorie
        return "activites-par-categorie";
    }

    @GetMapping("/inscrire/{programmeId}")
    public String inscrireUtilisateur(@PathVariable Long programmeId, Model model) {
        // Code pour inscrire l'utilisateur
        return "redirect:/activities";
    }

    @GetMapping("/desinscrire/{programmeId}")
    public String desinscrireUtilisateur(@PathVariable Long programmeId, Model model) {
        // Code pour désinscrire l'utilisateur
        return "redirect:/activities";
    }
}
