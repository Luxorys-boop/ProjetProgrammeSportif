package com.sportprog.prog.securite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll() // pages publiques
                .anyRequest().authenticated() // toutes autres pages -> to connexion
            )
            .formLogin(form -> form
                .loginPage("/login") // page de login
                .defaultSuccessUrl("/index") // redirection après connexion réussie
                .usernameParameter("email") // email comme identifiant
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // redirection après déconnexion
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}