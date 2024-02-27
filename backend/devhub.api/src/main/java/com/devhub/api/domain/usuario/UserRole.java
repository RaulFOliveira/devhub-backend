package com.devhub.api.domain.usuario;

public enum UserRole {
    FREELANCER("FREELANCER"),
    CONTRATANTE("CONTRATANTE");


    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
