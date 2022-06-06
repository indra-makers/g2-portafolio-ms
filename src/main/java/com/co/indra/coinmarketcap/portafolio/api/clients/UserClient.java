package com.co.indra.coinmarketcap.portafolio.api.clients;

import com.co.indra.coinmarketcap.portafolio.api.model.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UserClient {
    private final RestTemplate restTemplate;

    @Value("${api.userClient.url}")
    private String apiUrl;

    public UserClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public UserResponse getUserFromUsersmsByUsername(String username) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(apiUrl).path(username);
        ResponseEntity<UserResponse> responseUser = restTemplate.getForEntity(uri.toUriString(), UserResponse.class);
        UserResponse body = responseUser.getBody();
        return new UserResponse(body.getUsername(), body.getMail(), body.getDisplayName(), body.getIdCategoryUser());
    }
}
