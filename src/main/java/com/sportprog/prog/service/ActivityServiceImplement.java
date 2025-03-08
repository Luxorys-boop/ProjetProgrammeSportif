package com.sportprog.prog.service;

import com.sportprog.prog.dto.CategoryDTO;
import com.sportprog.prog.model.Activity;
import com.sportprog.prog.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActivityServiceImplement implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;


    /**
     * Récupère toutes les activités.
     *
     * @return Une liste de toutes les activités.
     */
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Récupère une activité par son ID.
     *
     * @param id L'ID de l'activité.
     * @return L'activité correspondante, ou null si non trouvée.
     */
    public Activity getActivityById(Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return activity.orElse(null); // Retourne null si l'activité n'est pas trouvée
    }



    /**
     * Ajoute une nouvelle activité.
     *
     * @param activity L'activité à ajouter.
     * @return L'activité ajoutée.
     */
    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    /**
     * Met à jour une activité existante.
     *
     * @param id       L'ID de l'activité à mettre à jour.
     * @param activity Les nouvelles données de l'activité.
     * @return L'activité mise à jour, ou null si l'activité n'existe pas.
     */
    public Activity updateActivity(Long id, Activity activity) {
        if (activityRepository.existsById(id)) {
            activity.setId(id); // Assure-toi que l'ID est correct
            return activityRepository.save(activity);
        }
        return null; // Retourne null si l'activité n'existe pas
    }

    /**
     * Supprime une activité par son ID.
     *
     * @param id L'ID de l'activité à supprimer.
     * @return true si l'activité a été supprimée, false sinon.
     */
    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false; // Retourne false si l'activité n'existe pas
    }

    @Override
    public List<Activity> getAllCategories() {
        return activityRepository.findAll();
    }

    @Override
    public List<CategoryDTO> getDistinctCategories() {
        return activityRepository.findDistinctCategories();
    }

    @Override
    public void inscrireUtilisateur(Long utilisateurId, Long activityId) {
        
    }

    @Override
    public void desinscrireUtilisateur(Long utilisateurId, Long activityId) {
        
    }

    @Override
    public List<Activity> findActivitiesByCategoryId(Long categoryId) {
        return activityRepository.findActivitiesByCategoryId(categoryId);
    }

    @Override
    public List<Activity> findActivitiesByUserId(Long userId) {
        return activityRepository.findActivitiesByUserId(userId);
    }
   
}