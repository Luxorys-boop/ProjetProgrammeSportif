package com.sportprog.prog.model;

import java.util.Set;
import jakarta.persistence.*;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToMany
    @JoinTable(
        name = "activity_category", // Nom de la table de liaison
        joinColumns = @JoinColumn(name = "activity_id"), // Colonne de cette entité
        inverseJoinColumns = @JoinColumn(name = "categorie_id") // Colonne de l'autre entité
    )
    private Set<Categorie> categories;

    @ManyToMany
    @JoinTable(
        name = "utilisateur_activite", // Nom de la table de liaison
        joinColumns = @JoinColumn(name = "id_acti"), // Colonne de cette entité
        inverseJoinColumns = @JoinColumn(name = "id_user") // Colonne de l'autre entité
    )
    private Set<Utilisateur> utilisateurs;

    private String description;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public Set<Utilisateur> getUsers() {
        return utilisateurs;
    }

    public void setUsers(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}