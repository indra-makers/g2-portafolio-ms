package com.co.indra.coinmarketcap.portafolio.model.responses;

import java.io.Serializable;

public class UserResponse implements Serializable {
    private String mail;
    private String username;
    private String displayName;
    private long idCategoryUser;

    public UserResponse() {
    }

    public UserResponse(String username, String mail, String displayName, long idCategoryUser) {
        this.username = username;
        this.mail = mail;
        this.displayName = displayName;
        this.idCategoryUser = idCategoryUser;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getIdCategoryUser() {
        return idCategoryUser;
    }

    public void setIdCategoryUser(long idCategoryUser) {
        this.idCategoryUser = idCategoryUser;
    }
}
