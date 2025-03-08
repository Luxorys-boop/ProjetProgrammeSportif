package com.sportprog.prog.service;

import com.sportprog.prog.model.Evaluation;
import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.model.Activity;
import com.sportprog.prog.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public Optional<Evaluation> findByUtilisateurAndActivity(Utilisateur utilisateur, Activity activity) {
        return evaluationRepository.findByUtilisateurAndActivity(utilisateur, activity);
    }

    @Override
    public List<Evaluation> findByUtilisateur(Utilisateur utilisateur) {
        return evaluationRepository.findByUtilisateur(utilisateur);
    }

    @Override
    public List<Evaluation> findByActivity(Activity activity) {
        return evaluationRepository.findByActivity(activity);
    }
}