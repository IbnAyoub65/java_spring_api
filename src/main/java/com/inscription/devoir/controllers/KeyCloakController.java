package com.inscription.devoir.controllers;


import com.inscription.devoir.keycloak.KeycloakService;
import com.inscription.devoir.keycloak.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/keycloak/users")
public class KeyCloakController {

    private final KeycloakService service;

    public KeyCloakController(KeycloakService service) {
        this.service = service;
    }

    @PostMapping
    public UserRepresentation addUser(@RequestBody UserDto userDTO){
        service.addUser(userDTO);
        return service.getUser(userDTO.getUsename());
    }

    @GetMapping("/list")
    public List<UserRepresentation> getUsers(){
        List<UserRepresentation> users = service.getUsers();
        return users;
    }

    @GetMapping("/details")
    public ResponseEntity<String> getAdmin(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("name");
        String userEmail = (String) token.getTokenAttributes().get("email");
        var a = ((JwtAuthenticationToken) principal).getAuthorities().stream().map(
                grantedAuthority -> {
                    log.info("role : {}", grantedAuthority.getAuthority());
                    return grantedAuthority.getAuthority();

                }

        ).toList();



        return ResponseEntity.ok("Hello \nUser Name : " + userName + "\nUser Email : " + userEmail+"\nRole : "+String.join(", ", a));
    }
}
