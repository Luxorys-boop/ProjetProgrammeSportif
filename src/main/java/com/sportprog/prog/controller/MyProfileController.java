package com.sportprog.prog.controller;

import com.sportprog.prog.model.Activity;
import com.sportprog.prog.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MyProfileController {
    
    @GetMapping("/my_profile")
    public String login() {
        return "my_profile";
    }

}
