package com.sportprog.prog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.sportprog.prog.model.Activity;
import com.sportprog.prog.service.ActivityService;

@Controller
public class IndexController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/index")
    public String index(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        model.addAttribute("activities", activities);
        return "index";
    }

    @GetMapping("/")
        public String i2() {
        return "redirect:index";
    }

}
