package com.devhub.api.domain.usuario;

public enum UserRole {
    ADMIN("freelancer"),
    USER("contratante");


    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
