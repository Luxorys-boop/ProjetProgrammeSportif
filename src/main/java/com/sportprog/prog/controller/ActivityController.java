package com.sportprog.prog.controller;

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
        List<Activity> categories = activityService.getAllCategories();
        model.addAttribute("categories", categories);
        return "activities";
    }

    @GetMapping("/activities/{id}")
    public String afficherProgrammes(@PathVariable Long id, Model model) {
        Activity activite = activityService.getActivityById(id);
        model.addAttribute("activite", activite);
        // Ajouter les programmes associés à l'activité
        return "activities";
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
