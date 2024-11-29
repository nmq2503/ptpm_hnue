package com.nmq.foodninjaver2.core.modelBase;

public class UserModel {
    private String userName;
    private String email;
    private String role;

    public UserModel() {}

    public UserModel(String userName, String email, String role) {
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

