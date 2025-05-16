package com.aithinkers.KeyCloakJwtAuth.restcontroller;

import java.util.Map;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.aithinkers.KeyCloakJwtAuth.entity.AuthRequest;
import com.aithinkers.KeyCloakJwtAuth.entity.AuthResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", authRequest.getUsername());
        form.add("password", authRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, entity, Map.class);
            Map body = response.getBody();

            AuthResponse authResponse = new AuthResponse();
            authResponse.setAuthToken((String) body.get("access_token"));
            authResponse.setRefreshToken((String) body.get("refresh_token"));
            authResponse.setExpiresIn(((Number) body.get("expires_in")).longValue());
            authResponse.setIdleTimeout(20 * 60L); // 20 mins in seconds

            return ResponseEntity.ok(authResponse);

        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
