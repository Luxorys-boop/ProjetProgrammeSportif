package com.sportprog.prog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.repository.ActivityRepository;
import com.sportprog.prog.repository.UtilisateurRepository;
import com.sportprog.prog.service.ActivityService;

import jakarta.transaction.Transactional;

@Controller
public class MyActivitiesController {
    
    
    @Autowired
    private ActivityRepository activityRepository; // Injection de ActivityRepository

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Transactional
    @GetMapping("/desinscrireUser/{activityId}")
    public String desinscrireUtilisateur(@PathVariable Long activityId, @CookieValue(value = "user_email", required = false) String userEmail) {
        if (userEmail == null) {
            return "redirect:/login"; // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        activityService.desinscrireUtilisateur(utilisateur.getId(), activityId);

        return "mesactivites"; // Rediriger vers la page des activités
    }
}
