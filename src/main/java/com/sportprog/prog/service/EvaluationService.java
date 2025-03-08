package com.sportprog.prog.service;

import com.sportprog.prog.model.Evaluation;
import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.model.Activity;

import java.util.List;
import java.util.Optional;

public interface EvaluationService {
    List<Evaluation> findByUtilisateur(Utilisateur utilisateur);
    List<Evaluation> findByActivity(Activity activity);
    Optional<Evaluation> findByUtilisateurAndActivity(Utilisateur utilisateur, Activity activity);
}