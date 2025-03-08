package com.sportprog.prog.service;

import com.sportprog.prog.model.Activity;
import com.sportprog.prog.dto.CategoryDTO;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public interface ActivityService {

    

    /**
     * Récupère toutes les activités.
     *
     * @return Une liste de toutes les activités.
     */
    public List<Activity> getAllActivities();

    /**
     * Récupère une activité par son ID.
     *
     * @param id L'ID de l'activité.
     * @return L'activité correspondante, ou null si non trouvée.
     */
    public Activity getActivityById(Long id);



    /**
     * Ajoute une nouvelle activité.
     *
     * @param activity L'activité à ajouter.
     * @return L'activité ajoutée.
     */
    public Activity addActivity(Activity activity);

    /**
     * Met à jour une activité existante.
     *
     * @param id       L'ID de l'activité à mettre à jour.
     * @param activity Les nouvelles données de l'activité.
     * @return L'activité mise à jour, ou null si l'activité n'existe pas.
     */
    public Activity updateActivity(Long id, Activity activity);

    /**
     * Supprime une activité par son ID.
     *
     * @param id L'ID de l'activité à supprimer.
     * @return true si l'activité a été supprimée, false sinon.
     */
    public boolean deleteActivity(Long id);

    public List<Activity> getAllCategories();

    List<CategoryDTO> getDistinctCategories();

    List<Activity> findActivitiesByCategoryId(Long categoryId);

    public void inscrireUtilisateur(Long utilisateurId, Long activityId);

    public void desinscrireUtilisateur(Long utilisateurId, Long activityId);

    public List<Activity> findActivitiesByUserId(Long userId);

    
    

}