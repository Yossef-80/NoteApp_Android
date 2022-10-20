package com.example.note_app.notes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  Id{
    @SerializedName("_id")
    @Expose
    String id;

    public Id(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
