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
                .requestMatchers("/index", "/login", "/register", "/activities").permitAll()
                .requestMatchers("/my_profile").authenticated() 
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .usernameParameter("email") 
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true) 
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout") 
                
            )
            .sessionManagement(session -> session
                .sessionFixation().migrateSession() 
                .maximumSessions(1) 
                .expiredUrl("/login?expired") 
            )
            .securityContext(context -> context
                .requireExplicitSave(true) 
            );

        return http.build();
    }

    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
