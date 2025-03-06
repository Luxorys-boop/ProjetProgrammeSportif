package com.sportprog.prog.dto;

public class CategoryDTO {
    private Long id;
    private String categorie;

    public CategoryDTO(Long id, String categorie) {
        this.id = id;
        this.categorie = categorie;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}