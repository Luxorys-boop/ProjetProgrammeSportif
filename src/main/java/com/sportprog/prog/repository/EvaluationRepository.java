package com.sportprog.prog.repository;

import com.sportprog.prog.model.Evaluation;
import com.sportprog.prog.model.Utilisateur;
import com.sportprog.prog.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByUtilisateur(Utilisateur utilisateur);
    List<Evaluation> findByActivity(Activity activity);
    Optional<Evaluation> findByUtilisateurAndActivity(Utilisateur utilisateur, Activity activity);
}
