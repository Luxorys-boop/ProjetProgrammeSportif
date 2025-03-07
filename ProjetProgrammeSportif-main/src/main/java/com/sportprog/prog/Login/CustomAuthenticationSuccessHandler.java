package com.sportprog.prog.Login;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.Cookie;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        String email = authentication.getName(); 
        System.out.println("User " + email + " est connecté");

        // Crée un cookie avec l'email de l'utilisateur
        Cookie cookie = new Cookie("user_email", email);
        cookie.setPath("/"); // Accessible sur tout le site
        cookie.setMaxAge(24 * 60 * 60); // Durée de vie du cookie (1 jour)
        response.addCookie(cookie);
        
        response.sendRedirect("/index"); // Redirect user to /index after successful login
    }
}
