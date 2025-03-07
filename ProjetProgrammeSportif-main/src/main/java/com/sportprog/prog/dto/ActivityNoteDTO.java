package com.sportprog.prog.dto;

import com.sportprog.prog.model.Activity;

public class ActivityNoteDTO {
    private Activity activity;
    private Integer note;

    public ActivityNoteDTO(Activity activity, Integer note) {
        this.activity = activity;
        this.note = note;
    }

    // Getters et setters
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
}