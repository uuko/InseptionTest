package com.example.inseptiontest.ui.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    @SerializedName("AuthorizedId")
    private String AuthorizedId="";
    @SerializedName("NotesId")
    private String NotesId="";
    @SerializedName("Password")
    private String Password="";

    public String getAuthorizedId() {
        return AuthorizedId;
    }

    public void setAuthorizedId(String authorizedId) {
        AuthorizedId = authorizedId;
    }

    public String getNotesId() {
        return NotesId;
    }

    public void setNotesId(String notesId) {
        NotesId = notesId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
