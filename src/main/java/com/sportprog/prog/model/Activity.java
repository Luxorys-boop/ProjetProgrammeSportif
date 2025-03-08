package com.sportprog.prog.model;



import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

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
        joinColumns = @JoinColumn(name = "activity_id"), // Colonne de cette entité (Activity)
        inverseJoinColumns = @JoinColumn(name = "user_id") // Colonne de l'autre entité (User)
    )
    private Set<Utilisateur> users;

    @ManyToMany(mappedBy = "activity")  // Assurez-vous que le champ 'activity' existe dans Evaluation
    private Set<Evaluation> reco;


    private String description;

    private String pathologie;

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
    public String getPathologie() {
        return pathologie;
    }
    public void setPathologie(String pathologie) {
        this.pathologie = pathologie;
    }
    public Set<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }
}