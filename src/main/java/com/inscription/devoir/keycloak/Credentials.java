package com.inscription.devoir.keycloak;

import org.keycloak.representations.idm.CredentialRepresentation;

public class Credentials {

    public static CredentialRepresentation createPassword(String password){
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(credentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);

        return credentialRepresentation;
    }
}
