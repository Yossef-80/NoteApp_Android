package com.example.note_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  DeleteMessage
{
    @SerializedName("message")
    @Expose
    String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public DeleteMessage(String message) {
        Message = message;
    }
}
