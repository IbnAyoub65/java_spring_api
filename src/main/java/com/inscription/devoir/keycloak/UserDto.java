package com.inscription.devoir.keycloak;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String usename;
    private String password;
    private String prenom;
    private String nom;
    private String email;
    private List<String> roles;
}
