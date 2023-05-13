package com.inscription.devoir.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class KeycloakService {

    @Value("${app.keycloak.realm.name}")
    public String realmName;
    @Value("${app.keycloak.client-id}")
    public String clientId;

    private final KeycloakConfig keycloakConfig;


    public KeycloakService(KeycloakConfig keycloakConfig) {
        this.keycloakConfig = keycloakConfig;
    }

    public void  addUser(UserDto userDto){
        CredentialRepresentation credential = Credentials
                .createPassword(userDto.getPassword());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDto.getUsename());
        user.setFirstName(userDto.getPrenom());
        user.setLastName(userDto.getNom());
        user.setEmail(userDto.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        user.setEmailVerified(true);

        Keycloak keycloak = keycloakConfig.getInstance();
       UsersResource usersResource = keycloak.realm(realmName).users();
       usersResource.create(user);
       user = keycloak.realm(realmName).users().search(user.getUsername()).get(0);
       UserResource userResource = keycloak.realm(realmName).users().get(user.getId());

        ClientRepresentation app1Client = keycloak.realm(realmName).clients()
                .findByClientId("demo_inscription").get(0);

        for (String role: userDto.getRoles()) {

            RoleRepresentation userClientRole = keycloak.realm(realmName).clients()
                    .get(app1Client.getId())
                    .roles().get(role).toRepresentation();
            userResource.roles()
                    .clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

        }
    }

    public UserRepresentation getUser(String userName){
        UsersResource usersResource = keycloakConfig.getInstance().realm(realmName).users();
        List<UserRepresentation> users = usersResource.search(userName,true);
        return users.size()>0?users.get(0):null;
    }

    public List<UserRepresentation> getUsers(){
        Keycloak kc = keycloakConfig.getInstance();
        RealmResource realmResource = kc.realm(realmName);
        UsersResource usersResource = realmResource.users();
        System.out.println("Count: " + usersResource.count());

        return usersResource.list();
    }
}
