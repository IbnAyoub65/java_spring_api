package com.inscription.devoir.keycloak;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KeycloakConfig {

    @Value("${app.keycloak.client-id}")
    private String client_id;

    @Value("${app.keycloak.client-secret}")
    private String client_secret;

    @Value("${app.keycloak.server.url}")
    private String server_url;

    @Value("${app.keycloak.realm.name}")
    private String realm;

    @Value("${app.keycloak.admin.username}")
    private String username;

    @Value("${app.keycloak.admin.password}")
    private String password;

    private static Keycloak keycloak = null;

    public Keycloak getInstance(){
        if(keycloak != null){
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(server_url)
                    .clientId("admin-cli")
                    .username(username)
                    .password(password)
                    .realm("master")
                    .resteasyClient(new ResteasyClientBuilder()
                    .connectionPoolSize(10).build())
                    .build();
        }

        return keycloak;
    }
}
