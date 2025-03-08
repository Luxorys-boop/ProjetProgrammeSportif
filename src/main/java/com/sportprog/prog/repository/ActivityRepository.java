package com.sportprog.prog.repository;

import com.sportprog.prog.model.Activity;
import com.sportprog.prog.dto.CategoryDTO;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a JOIN a.categories c WHERE c.id = :categoryId")
    List<Activity> findActivitiesByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT NEW com.sportprog.prog.dto.CategoryDTO(c.id, c.nom) FROM Categorie c ORDER BY c.id")
    List<CategoryDTO> findDistinctCategories();

    @Query("SELECT a FROM Activity a JOIN a.utilisateurs u WHERE u.id = :userId")
    List<Activity> findActivitiesByUserId(@Param("userId") Long userId);
    
}