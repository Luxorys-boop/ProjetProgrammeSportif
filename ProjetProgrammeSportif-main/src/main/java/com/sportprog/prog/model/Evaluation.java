package com.sportprog.prog.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false) // Clé étrangère vers Utilisateur
    private Utilisateur utilisateur;

    @ManyToMany
    @JoinTable(
    name = "evaluation", 
    joinColumns = @JoinColumn(name = "activity_id"), 
    inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    private Set<Activity> activity;


    private String commentaire;
    private int note;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<Activity> getActivity() {
        return activity;
    }

    public void addActivity(Activity activity) {
        this.activity.add(activity);
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
