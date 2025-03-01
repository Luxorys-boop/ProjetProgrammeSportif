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
                .requestMatchers("/index", "/login", "/register", "/activities").permitAll() // Public endpoints
                .requestMatchers("/my_profile").authenticated() // Secure /index for authenticated users
                .anyRequest().authenticated() // Secure all other endpoints
            )
            .formLogin(form -> form
                .loginPage("/login") // login page
                .defaultSuccessUrl("/index", true) // redirect after successful login
                .usernameParameter("email") // email as username
                .passwordParameter("password")
                .failureUrl("/login?error=true")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true) // Invalidate session
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout") // redirect after logout
                
            )
            .sessionManagement(session -> session
                .sessionFixation().migrateSession() // Migrate session on login
                .maximumSessions(1) // Allow only one session per user
                .expiredUrl("/login?expired") // Redirect to login page if session expires
            )
            .securityContext(context -> context
                .requireExplicitSave(true) // Explicitly save the SecurityContext
            );

        return http.build();
    }

    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
