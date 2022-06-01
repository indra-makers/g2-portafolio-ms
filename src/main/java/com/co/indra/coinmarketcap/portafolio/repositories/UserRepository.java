package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.responses.UserResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRepository {
    private final RestTemplate restTemplate;

    public UserRepository(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public UserResponse getUserFromUsersmsByUsername(String username) {
        String url = "https://g2-users-ms.herokuapp.com/api/users-ms/users/" + username;
        return this.restTemplate.getForObject(url, UserResponse.class);

    }
}
