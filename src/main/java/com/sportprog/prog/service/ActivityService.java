package com.sportprog.prog.service;

import com.sportprog.prog.model.Activity;
import com.sportprog.prog.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll(); // Utilisation de findAll()
    }
}