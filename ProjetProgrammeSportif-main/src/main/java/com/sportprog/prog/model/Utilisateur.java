package com.sportprog.prog.model;



import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;


@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nom;
    private Integer age;
    private String email;
    private String genre;
    private String pathologie;

    @ManyToMany(mappedBy = "users") // "users" est le nom de l'attribut dans Activity
    private Set<Activity> activities;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        // TODO Auto-generated method stub
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // TODO Auto-generated method stub 
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        // TODO Auto-generated method stub
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        // TODO Auto-generated method stub
        this.genre = genre;
    }

    public String getPathologie() {
        return pathologie;
    }
    public void setPathologie(String pathologie) {
        // TODO Auto-generated method stub
        this.pathologie = pathologie;
    }

    public Set<Activity> getActivities() {
        return activities;
    }
    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

}