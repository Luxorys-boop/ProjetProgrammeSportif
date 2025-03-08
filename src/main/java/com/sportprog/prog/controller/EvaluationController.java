package com.sportprog.prog.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sportprog.prog.model.Activity;
import com.sportprog.prog.model.Evaluation;
import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.repository.ActivityRepository;
import com.sportprog.prog.repository.EvaluationRepository;
import com.sportprog.prog.repository.UtilisateurRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @PostMapping("/evaluations/ajouter")
    public String ajouterEvaluation(
            @RequestParam("activityId") Long activityId,
            @RequestParam("note") int note,
            @RequestParam("commentaire") String commentaire,
            HttpServletRequest request) {

        // Récupérer l'utilisateur connecté
        Utilisateur utilisateur = getUtilisateurConnecte(request);
        if (utilisateur == null) {
            return "redirect:/login"; // Rediriger vers la connexion si l'utilisateur n'est pas trouvé
        }

        // Récupérer l'activité
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));

        // Créer une nouvelle évaluation
        Evaluation evaluation = new Evaluation();
        evaluation.setUtilisateur(utilisateur);
        evaluation.setActivity(activity);
        evaluation.setNote(note);
        evaluation.setCommentaire(commentaire);

        // Enregistrer l'évaluation
        evaluationRepository.save(evaluation);

        return "redirect:/mesactivites"; // Rediriger vers la page des activités
    }

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
}