package com.sportprog.prog.controller;

import com.sportprog.prog.dto.ActivityNoteDTO;
import com.sportprog.prog.model.Activity;
import com.sportprog.prog.model.Evaluation;
import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.repository.ActivityRepository;
import com.sportprog.prog.repository.EvaluationRepository;
import com.sportprog.prog.repository.UtilisateurRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class RecommandationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository; // Utilisation de l'instance injectée

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping("/recommendations")
public String mesActivites(Model model, HttpServletRequest request) {
    // Récupère l'utilisateur connecté
    Utilisateur utilisateur = getUtilisateurConnecte(request);
    if (utilisateur == null) {
        return "redirect:/login"; // Redirige vers la connexion si l'utilisateur n'est pas trouvé
    }

    // Récupère toutes les activités
    List<Activity> toutesLesActivites = activityRepository.findAll();

    // Récupère les évaluations de l'utilisateur connecté
    List<Evaluation> evaluations = evaluationRepository.findByUtilisateur(utilisateur);

    // Crée une liste de paires (Activity, Note) à partir des évaluations
    List<Pair<Activity, Integer>> activiteNotePairs = evaluations.stream()
        .flatMap(evaluation -> ((Collection<Activity>) evaluation.getActivity()).stream() // Pour chaque activité dans l'évaluation
            .map(activity -> Pair.of(activity, evaluation.getNote())) // Crée une paire (Activity, Note)
        )
        .collect(Collectors.toList());

    // Crée une liste de DTO pour afficher toutes les activités avec leurs notes
    List<ActivityNoteDTO> activitesAvecNotes = toutesLesActivites.stream()
        .map(activity -> {
            // Récupère la note de l'utilisateur pour cette activité (ou null si aucune note)
            Integer note = activiteNotePairs.stream()
                .filter(pair -> pair.getFirst().equals(activity)) // Trouve la paire correspondant à l'activité
                .map(Pair::getSecond) // Récupère la note
                .findFirst()
                .orElse(null); // Si aucune note n'est trouvée, retourne null

            return new ActivityNoteDTO(activity, note);
        })
        .sorted(Comparator.comparing(
            ActivityNoteDTO::getNote, 
            Comparator.nullsLast(Comparator.reverseOrder()) // Trie par note décroissante, les activités sans note à la fin
        ))
        .collect(Collectors.toList());

    // Ajoute les activités avec leurs notes au modèle pour l'affichage dans la vue
    model.addAttribute("activitesAvecNotes", activitesAvecNotes);
    return "recommendations"; 
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
